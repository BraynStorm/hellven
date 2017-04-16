package braynstorm.hellven.game

import braynstorm.hellven.game.Item
import braynstorm.hellven.game.ItemStack

interface Inventory : Iterable<ItemStack> {
	operator fun get(slot: Int): ItemStack
	
	val capacity: Int
	val currentItems: Int
	
	val isFull: Boolean
	val isEmpty: Boolean
	
	
	fun canSlotContainItemStack(slot: Int, itemStack: ItemStack): Boolean
	fun canContainItemStack(itemStack: ItemStack): Boolean
	
	
	fun insertItemStackInSlot(itemStack: ItemStack, slot: Int): Boolean
	fun insertItemStack(itemStack: ItemStack): Boolean
	fun containsItem(item: Item): Boolean
	fun itemCount(item: Item): Int
}
