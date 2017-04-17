package braynstorm.hellven.game

import com.badlogic.gdx.graphics.g2d.Sprite

class GenericItem(id: Int, usage: ItemUsage, quality: ItemQuality, icon: Sprite) : AbstractItem(id, usage, quality, icon) {
	override fun toString(): String {
		return "GenericItem[id=$id, usage=$usage, quality=$quality]"
	}
}
