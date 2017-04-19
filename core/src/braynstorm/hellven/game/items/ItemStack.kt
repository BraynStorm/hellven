package braynstorm.hellven.game.items

import braynstorm.hellven.game.api.Item

/**
 * You are strongly discouraged from using the constructor of this class. Instead, use [Item.getItemStack].
 */
open class ItemStack internal constructor(open val item: Item, var amount: Int = 1) {
	internal constructor(id: Int, amount: Int = 1) : this(Items[id], amount)
	
	open fun toEquippable(): EquippableItemStack {
		if (item.usage != ItemUsage.EQUIPMENT) {
			throw IllegalArgumentException("$item is not equippable")
		}
		
		if (amount != 1) {
			throw IllegalArgumentException("$amount cannot be different than 1 when creating EquippableItemStacks")
		}
		
		return EquippableItemStack(this)
	}
	
	override fun toString(): String {
		return "ItemStack[item=$item]"
	}
}

