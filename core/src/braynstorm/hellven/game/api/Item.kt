package braynstorm.hellven.game.api

import braynstorm.hellven.game.items.ItemQuality
import braynstorm.hellven.game.items.ItemStack
import braynstorm.hellven.game.items.ItemUsage
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
