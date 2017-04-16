package braynstorm.hellven.game.observation

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A set of observers for a particular object, to easily implement the Observer Pattern without needing inheritance.
 *
 * This can be made a public property of the object. Call [observe] to create delegated properties for each property
 * that observers need to be notified about.
 *
 * @param O The type of the observers, typically an interface.
 *
 * @author http://codereview.stackexchange.com/questions/155580/observer-pattern-using-a-set-of-observers-and-delegated-properties
 */
class ObserverSet<O> {
	
	private val observers = (mutableSetOf<O>() as LinkedHashSet)
	private val properties = mutableListOf<ObservedProperty<*>>()
	
	/**
	 * Sets whether an observer is present in the set. If the observer was actually added to the set, its notification
	 * methods will be called with the current value of the property.
	 *
	 * @param observer The observer.
	 * @param present If true, the observer will be added to the set if not already there. If false, the observer will
	 *   be removed from the set if it's there.
	 */
	fun toggle(observer: O, present: Boolean) {
		if (present) {
			this += observer
		} else {
			this -= observer
		}
	}
	
	operator fun plusAssign(observer: O) {
		if (observers.add(observer)) {
			init(observer)
		}
	}
	
	operator fun minusAssign(observer: O) {
		observers -= observer
	}
	
	
	/**
	 * Creates a delegate property with a callback that notifies each observer.
	 *
	 * @param V The type of the property.
	 * @param initialValue The initial value of the property.
	 * @param onChanged Function on the [O] interface that will be called with the old and new value after the
	 *   property has been changed.
	 */
	fun <V> observe(initialValue: V, onChanged: O.(old: V, new: V) -> Unit): ReadWriteProperty<Any?, V> {
		val property = ObservedProperty(initialValue, onChanged)
		properties.add(property)
		return property
	}
	
	private fun init(observer: O) {
		properties.forEach { property ->
			property.initObserver(observer)
		}
	}
	
	private inner class ObservedProperty<V>(initialValue: V, private val onChanged: O.(old: V, new: V) -> Unit) :
			ReadWriteProperty<Any?, V> {
		
		private var value = initialValue
		
		override fun getValue(thisRef: Any?, property: KProperty<*>): V {
			return value
		}
		
		override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
			val oldValue = this.value
			this.value = value
			observers.forEach { observer ->
				observer.onChanged(oldValue, value)
			}
		}
		
		fun initObserver(observer: O) {
			observer.onChanged(value, value)
		}
	}
}
