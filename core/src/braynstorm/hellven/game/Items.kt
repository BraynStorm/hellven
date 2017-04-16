package braynstorm.hellven.game

import braynstorm.hellven.Localization
import com.badlogic.gdx.graphics.g2d.Sprite

/**
 * TODO Add class description
 * Created by Braynstorm on 3.4.2017 г..
 */

interface Item {
	/**
	 * Unique identification of the item.
	 *
	 * Useful for saving/loading.
	 */
	val id: Int
	val usage: ItemUsage
	val quality: ItemQuality
	
	/**
	 * Localized string.
	 */
	val name: String
	val icon: Sprite
	
	fun getItemStack(amount: Int = 1): ItemStack
	
	fun compareByNames(item: Item): Int
	fun compareByIDs(item: Item): Int
}

interface ItemEquippable : Item {
	/**
	 * In which slot does this item get equipped.
	 */
	val slot: EquipmentSlotType
	
	/**
	 * The attributes that this item affects.
	 */
	val attributes: Attributes
}

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

abstract class AbstractItem(final override val id: Int,
                            final override val usage: ItemUsage,
                            final override val quality: ItemQuality,
                            override val icon: Sprite) : Item {
	override val name: String get() = Localization.formatItem("i.$id.name")
	
	override fun compareByIDs(item: Item): Int = id.compareTo(item.id)
	override fun compareByNames(item: Item): Int = name.compareTo(item.name)
	
	override fun getItemStack(amount: Int): ItemStack {
		return ItemStack(this, amount)
	}
	
	
}

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

class GenericItem(id: Int, usage: ItemUsage, quality: ItemQuality, icon: Sprite) : AbstractItem(id, usage, quality, icon) {
	override fun toString(): String {
		return "GenericItem[id=$id, usage=$usage, quality=$quality]"
	}
}

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
