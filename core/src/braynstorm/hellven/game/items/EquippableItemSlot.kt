package braynstorm.hellven.game.items

import braynstorm.hellven.game.AbstractItemSlot


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
