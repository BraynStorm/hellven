package braynstorm.rpg.game.mechanics

/**
 * Describes a resource pool for living beings to use for their tricks.
 * Things like Health, Mana, Lifeforce, Willpower, etc...
 * Created by Braynstorm on 13.3.2017 г..
 */
interface ResourcePool<T : Comparable<T>> {
	/**
	 * @return True if there's enough in the pool to drain [amount] units
	 */
	fun canDrain(amount: T): Boolean
	
	/**
	 * @param [doFill] If false, then it just simulates the fill, without actually modifying the pool.
	 * @return How much more should have been drained but wasn't due to insufficient amount of resource.
	 */
	fun drain(amount: T, doDrain: Boolean): T
	
	
	/**
	 * @return Is there enough space to fit [amount] units?
	 */
	fun canFill(amount: T): Boolean
	
	/**
	 * Fills the pool "to the brim".
	 *
	 * @param [doFill] If false, then it just simulates the fill, without actually modifying the pool.
	 * @return How much is left from [amount] after the filling.
	 */
	fun fill(amount: T, doFill: Boolean): T
	
	/**
	 * Is the pool empty
	 * [empty] should always be equal ![full]
	 */
	val empty: Boolean
	
	/**
	 * Is the pool full
	 * [full] should always be equal ![empty]
	 */
	val full: Boolean
	
	val isNotEmpty: Boolean get() = !empty
	val isNotFull: Boolean get() = !full
}

/**
 * @
 */
abstract class AbstractResourcePool<T : Comparable<T>> : ResourcePool<T> {
	/**
	 * How much resource is this pool capable of holding.
	 */
	abstract var capacity: T
		protected set
	
	/**
	 * The current amount of resource in this pool
	 */
	abstract var pool: T
		protected set
	
	/**
	 * How full is the pool?
	 * 0f <-> 1f
	 */
	abstract val percentage: Float
}

// TODO its fun, work on it
open class IntResourcePool(capacity: Int, pool: Int = 0) : AbstractResourcePool<Int>() {
	
	override val percentage: Float
		get() = if (full) {
			1f
		} else if (empty) {
			0f
		} else {
			pool.toFloat() / capacity
		}
	
	override var capacity = capacity
		set(value) {
			
			if (pool > value) {
				pool = value
			}
			
			field = value
		}
	
	override var pool = pool
		set(value) {
			if (value > capacity) {
				field = capacity
			} else {
				field = value
			}
		}
	
	override val empty: Boolean
		get() = (pool == 0)
	
	override val full: Boolean
		get() = (pool == capacity)
	
	
	override fun canDrain(amount: Int): Boolean {
		return pool >= amount
	}
	
	override fun drain(amount: Int, doDrain: Boolean): Int {
		TODO("not implemented")
	}
	
	override fun canFill(amount: Int): Boolean {
		return pool + amount <= capacity
	}
	
	override fun fill(amount: Int, doFill: Boolean): Int {
		TODO("not implemented")
	}
}


/**
 * Represents "no resource"
 */
class EmptyResource() : IntResourcePool(1, 0) {
	
}

class Health(capacity: Int, pool: Int = capacity) : IntResourcePool(capacity, pool) {
	
}


class Mana(capacity: Int, pool: Int = 0) : IntResourcePool(capacity, pool) {
	
	
}

class LivingEssence(capacity: Int, pool: Int = 0) : IntResourcePool(capacity, pool) {
	
}

class Willpower(capacity: Int)
