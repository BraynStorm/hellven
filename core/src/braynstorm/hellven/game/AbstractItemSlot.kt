package braynstorm.hellven.game

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
