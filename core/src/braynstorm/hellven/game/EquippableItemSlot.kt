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


abstract class AbstractItemSlot<T : ItemStack>(itemStack: T? = null) : ItemSlot<T> {
	override var itemStack: T? = itemStack
		protected set
	
	override fun isStackValidForThisSlot(stack: T): Boolean = true
	
	override fun insertItem(stack: T): Boolean {
		if (isEmpty() && isStackValidForThisSlot(stack)) {
			this.itemStack = stack
			return true
		}
		
		return false
	}
	
	override fun removeItem(): T? {
		if (isNotEmpty()) {
			val stack = itemStack
			itemStack = null
			return stack
		}
		
		return null
	}
}

/**
 * Ordinary [ItemSlot]. Can contain any [ItemStack]
 */
class GenericItemSlot(itemStack: ItemStack? = null) : AbstractItemSlot<ItemStack>(itemStack) {
	override fun toString(): String {
		return "GenericItemSlot[itemStack=$itemStack]"
	}
}

/**
 * Describes a slot for equippable items.
 * Used by entities to manage their equipment.
 *
 * Parameters:
 * - [itemStack] the initial stack in this slot, null mean no stack.
 * - [slot] the type of this slot. It is used for validity check of [EquippableItemStack]s when equipping them.
 * Created by Braynstorm on 16.4.2017 г..
 */
open class EquippableItemSlot(val slot: EquipmentSlotType, itemStack: EquippableItemStack? = null) : AbstractItemSlot<EquippableItemStack>(itemStack) {
	
	override fun toString(): String {
		return "EquippableItemSlot[itemStack=$itemStack]"
	}
	
	override fun isStackValidForThisSlot(stack: EquippableItemStack): Boolean {
		return slot == stack.slot
	}
	
}
