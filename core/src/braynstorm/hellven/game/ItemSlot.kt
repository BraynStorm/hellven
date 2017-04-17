package braynstorm.hellven.game

import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Describes any slot for [ItemStack] instances.
 *
 * Used by [Inventory], [Entity] (for equipment slots).
 */
interface ItemSlot<T : ItemStack> {
	
	/**
	 * The [T] that is held in the slot. Null if the slot is empty.
	 */
	val itemStack: T?
	
	/**
	 * Tries to insert a [T] in the slot.
	 *
	 * Performs the validity checks, and if the [T] passes them (provided the slot is empty) the [T] gets inserted in the slot.
	 */
	infix fun insertItem(stack: T): Boolean
	
	/**
	 * Removes and returns the items that was in the slot.
	 *
	 * @return The instance of [T] that was in the slot, null if the slot was empty
	 */
	fun removeItem(): T?
	
	/**
	 * Checks whether or not there is an [ItemStack] in the slot
	 */
	fun isNotEmpty(): Boolean = itemStack != null
	
	/**
	 * @return ![hasItem]
	 */
	fun isEmpty(): Boolean = itemStack == null
	
	/**
	 * This method is intended to be overridden by specialized [ItemSlot]s
	 * @returns true only if [stack] is valid for this slot.
	 */
	fun isStackValidForThisSlot(stack: T): Boolean
	
	fun draw(batch: Batch, x: Float, y: Float) {
		val stack = itemStack
		if (stack != null) {
			stack.item.icon.setPosition(x, y)
			stack.item.icon.draw(batch)
		}
	}
}
