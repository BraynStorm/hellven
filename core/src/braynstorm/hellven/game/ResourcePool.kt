package braynstorm.hellven.game

import braynstorm.hellven.game.observation.Observable
import com.badlogic.gdx.graphics.Color

/**
 * Resource pool that uses floats for storage
 *
 */
interface ResourcePool : Observable<ResourceObserver>, TickReceiverResource {
	
	var capacity: Float
	val current: Float
	
	/**
	 * How full is the pool?
	 * Range: Between 0 and 1 (float).
	 */
	val percentage: Float
	/**
	 * Is the pool empty
	 * [empty] must always be equal ![full]
	 */
	val empty: Boolean
	/**
	 * True if the pool is full.
	 * [full] must always be equal ![empty]
	 */
	val full: Boolean
	
	/**
	 * This resource belongs to this [Entity].
	 */
	var entity:Entity
	/**
	 * True if the pool is *not* empty. False if it is
	 */
	val isNotEmpty: Boolean get() = !empty
	/**
	 * True if the pool is empty. False if it isn't.
	 */
	val isNotFull: Boolean get() = !full
	/**
	 * The tint color of this resource.
	 */
	val color: Color
	
	/**
	 * @return True if there's enough in the pool to drain [amount] units
	 */
	fun canDrain(amount: Float): Boolean
	
	/**
	 * Drains resources from the pool.
	 * @param [doDrain] If false, then it just simulates the fill, without actually modifying the pool.
	 * @return How much was drained.
	 */
	fun drain(amount: Float, doDrain: Boolean): Float
	
	
	/**
	 * @return Is there enough space to fit [amount] units?
	 */
	fun canFill(amount: Float): Boolean
	
	/**
	 * Fills the pool.
	 *
	 * @param [doFill] If false, then it just simulates the fill, without actually modifying the pool.
	 * @return How much was filled.
	 */
	fun fill(amount: Float, doFill: Boolean): Float
	
	/**
	 * Returns a string to be used for UI purposes.
	 */
	operator fun get(format: Format): String
	
	enum class Format {
		/**
		 * Example:
		 * - pool = 50
		 * - capacity = 200
		 * - Format: 25%
		 */
		PERCENT,
		
		/**
		 * Example:
		 * - pool = 55
		 * - capacity = 1020
		 * - Format: 5% - 55/1020
		 */
		CAPACITY,
		/**
		 * Example:
		 * - pool = 55
		 * - capacity = 1020
		 * - Format: 55/1020
		 */
		CURRENT
	}
	
}
