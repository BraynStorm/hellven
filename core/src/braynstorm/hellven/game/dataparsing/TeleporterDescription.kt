package braynstorm.hellven.game.dataparsing

import braynstorm.hellven.game.api.GameObject
import braynstorm.hellven.game.gameobjects.Teleporter

class TeleporterDescription(val world: String) : GameObjectDescription("teleporter") {
	override fun createGameObject(): GameObject {
		return Teleporter(world)
	}
	
}
