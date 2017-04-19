package braynstorm.hellven.game

import braynstorm.hellven.Localization
import braynstorm.hellven.game.api.Item
import braynstorm.hellven.game.items.ItemQuality
import braynstorm.hellven.game.items.ItemStack
import braynstorm.hellven.game.items.ItemUsage
import com.badlogic.gdx.graphics.g2d.Sprite

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
