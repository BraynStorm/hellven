package braynstorm.hellven.game

import com.badlogic.gdx.graphics.g2d.Sprite

/*
interface ItemMutator {
	val sortingNumber: Int
}

interface ItemNameMutator : ItemMutator {
	val prefix: String get () = ""
	val suffix: String get () = ""
}

interface ItemStatMutator : ItemMutator {
	val stats: Attributes
}
*/

/*

class MutatedItem(id: Int, type: ItemType, quality: ItemQuality, mutators: List<ItemMutator> = emptyList()) : AbstractItem(id) {
	var mutators = mutators
		private set
	
	init {
		this.mutators = mutators.sortedBy { it.sortingNumber }
	}
	
	override val name: String
		get() {
			val prefix = StringBuilder()
			val suffix = StringBuilder()
			
			@Suppress("UNCHECKED_CAST") // The cast is always going to succeed
			val nameMutators = (mutators.filter { it is ItemNameMutator } as? Collection<ItemNameMutator>)!!
			
			nameMutators.forEach {
				if (it.prefix.isNotEmpty()) {
					prefix.append(it.prefix)
					prefix.append(' ')
				}
				
				if (it.suffix.isNotEmpty()) {
					suffix.append(' ')
					suffix.append(it.suffix)
				}
			}
			
			return Localization.formatItem("i.$id.name", prefix.toString(), suffix.toString())
		}
	
	override fun compareByNames(item: Item): Int {
		return name.compareTo(item.name, true)
	}
	
	override fun compareByIDs(item: Item): Int {
		return id.compareTo(item.id)
	}
	
	override fun getItemStack(amount: Int): ItemStack {
		return ItemStack(this, amount)
	}
	
}
*/

class GenericEquipment(id: Int, quality: ItemQuality, icon: Sprite,
                       override val slot: EquipmentSlotType,
                       override val attributes: Attributes) : AbstractItem(id, ItemUsage.EQUIPMENT, quality, icon), ItemEquippable {
	/**
	 * Ignores the [amount] because equippable items cant have more than 1 in their stacks
	 */
	override fun getItemStack(amount: Int): EquippableItemStack {
		return super.getItemStack(1).toEquippable()
	}
}
