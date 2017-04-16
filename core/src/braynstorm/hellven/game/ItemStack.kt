package braynstorm.hellven.game

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
