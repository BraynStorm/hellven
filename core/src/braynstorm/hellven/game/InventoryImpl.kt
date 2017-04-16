package braynstorm.hellven.game

import braynstorm.hellven.game.Item
import braynstorm.hellven.game.ItemStack

//TODO Implement Basic inventory.
class InventoryImpl : Inventory {
	override fun get(slot: Int): ItemStack {
		TODO("get is not implemented");
		
	}
	
	override val capacity: Int
		get() = TODO("not implemented")
	override val currentItems: Int
		get() = TODO("not implemented")
	override val isFull: Boolean
		get() = TODO("not implemented")
	override val isEmpty: Boolean
		get() = TODO("not implemented")
	
	override fun canSlotContainItemStack(slot: Int, itemStack: ItemStack): Boolean {
		TODO("canSlotContainItemStack is not implemented")
	}
	
	override fun canContainItemStack(itemStack: ItemStack): Boolean {
		TODO("canContainItemStack is not implemented")
	}
	
	override fun iterator(): Iterator<ItemStack> {
		TODO("iterator is not implemented")
	}
	
	override fun insertItemStackInSlot(itemStack: ItemStack, slot: Int): Boolean {
		TODO("insertItemStackInSlot is not implemented")
	}
	
	override fun insertItemStack(itemStack: ItemStack): Boolean {
		TODO("insertItemStack is not implemented")
	}
	
	override fun containsItem(item: Item): Boolean {
		TODO("containsItem is not implemented")
	}
	
	override fun itemCount(item: Item): Int {
		TODO("itemCount is not implemented")
	}
}
