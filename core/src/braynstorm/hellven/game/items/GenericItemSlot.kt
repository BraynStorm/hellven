package braynstorm.hellven.game.items

import braynstorm.hellven.game.AbstractItemSlot

/**
 * Ordinary [ItemSlot]. Can contain any [ItemStack]
 */
class GenericItemSlot(itemStack: ItemStack? = null) : AbstractItemSlot<ItemStack>(itemStack) {
	override fun toString(): String {
		return "GenericItemSlot[itemStack=$itemStack]"
	}
}
