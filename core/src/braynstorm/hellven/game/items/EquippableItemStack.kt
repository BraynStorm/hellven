package braynstorm.hellven.game.items

import braynstorm.hellven.game.api.ItemEquippable

class EquippableItemStack internal constructor(itemStack: ItemStack) : ItemStack(itemStack.item, 1) {
	override val item: ItemEquippable = itemStack.item as ItemEquippable
	
	val slot = item.slot
	val attributes = item.attributes
	
	override fun toEquippable(): EquippableItemStack {
		return this
	}
	
	override fun toString(): String {
		return "EquippableItemStack[item=$item]"
	}
}
