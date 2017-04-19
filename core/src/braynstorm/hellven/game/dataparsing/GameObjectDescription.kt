package braynstorm.hellven.game.dataparsing

import braynstorm.hellven.game.api.GameObject

abstract class GameObjectDescription(val id: String) {
	abstract fun createGameObject(): GameObject
}
