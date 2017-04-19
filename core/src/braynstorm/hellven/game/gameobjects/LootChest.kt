package braynstorm.hellven.game.gameobjects

import braynstorm.hellven.Hellven
import braynstorm.hellven.Localization
import braynstorm.hellven.game.api.Entity
import braynstorm.hellven.game.gameobjects.AbstractGameObject

class LootChest() : AbstractGameObject("loot_chest",  Hellven.gameSkin.getSprite("go_lootchest")) {
	override val name: String
		get() = Localization.formatUI("go_teleporter")
	
	
	override fun onInteract(entity: Entity) {
		println("onInteract is not implemented")
	}
	
}
