package braynstorm.hellven.game

import braynstorm.hellven.game.Utils.FloatMath.epsilonEqual
import braynstorm.hellven.game.observation.ObserverSet
import com.badlogic.gdx.graphics.Color

/**
 * A resource pool using Float as it's internal type.
 */
abstract class AbstractResourcePool @JvmOverloads constructor(capacity: Float,
                                                              current: Float = capacity,
                                                              final override val color: Color) : ResourcePool {
	
	private val observers = ObserverSet<ResourceObserver>()
	
	private var strCurrent = "Current"
	private var strCapacity = "Capacity"
	private var strPercentage = "%"
	
	override var percentage: Float = current / capacity
	
	override var capacity by observers.observe(capacity, ResourceObserver::capacityChange)
	override var current by observers.observe(current, ResourceObserver::currentChange)
	
	override lateinit var entity: Entity
	
	init {
		observers += object : ResourceObserver {
			
			override fun capacityChange(old: Float, new: Float) {
				if (new < 1f)
					strCapacity = ""
				else
					strCapacity = Math.round(new).toString()
				super.capacityChange(old, new)
			}
			
			override fun currentChange(old: Float, new: Float) {
				if (new < 1f)
					strCurrent = ""
				else
					strCurrent = Math.round(new).toString()
				super.currentChange(old, new)
			}
			
			override fun anyChange() {
				percentage = this@AbstractResourcePool.current / this@AbstractResourcePool.capacity
				
				if (percentage < 0.01f) {
					strPercentage = ""
				} else {
					strPercentage = "${Math.round(percentage * 100f)}%"
				}
			}
			
		}
	}
	
	override final fun plusAssign(observer: ResourceObserver) {
		observers += observer
	}
	
	override final fun minusAssign(observer: ResourceObserver) {
		observers -= observer
	}
	
	override fun get(format: ResourcePool.Format) = when (format) {
		ResourcePool.Format.PERCENT  -> strPercentage
		ResourcePool.Format.CAPACITY -> strCapacity
		ResourcePool.Format.CURRENT  -> strCurrent
	}
	
	override val empty: Boolean get() = epsilonEqual(current, 0)
	override val full: Boolean get() = epsilonEqual(current, capacity)
	
	override fun canDrain(amount: Float): Boolean = current >= amount
	
	override fun canFill(amount: Float): Boolean = capacity - current >= amount
	
	override fun drain(amount: Float, doDrain: Boolean): Float {
		if (amount == 0F) {
			return 0F
		}
		
		if (canDrain(amount)) {
			if (doDrain) {
				current -= amount
			}
			return amount
		} else {
			val drained = current
			if (doDrain) {
				current = 0F
			}
			return drained
		}
	}
	
	override fun fill(amount: Float, doFill: Boolean): Float {
		if (amount == 0F) {
			return 0F
		}
		
		if (canFill(amount)) {
			if (doFill) {
				current += amount
			}
			return amount
		} else {
			val filled = capacity - current
			if (doFill) {
				current = capacity
			}
			
			return filled
		}
	}
}


