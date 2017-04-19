package braynstorm.hellven.game.dataparsing

import braynstorm.hellven.game.api.GameObject
import braynstorm.hellven.game.gameobjects.LootChest

class LootChestDescription() : GameObjectDescription("go_loot_chest") {
	override fun createGameObject(): GameObject {
		return LootChest()
	}
	
}
