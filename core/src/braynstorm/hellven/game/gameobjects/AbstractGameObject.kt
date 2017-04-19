package braynstorm.hellven.game.gameobjects

import braynstorm.hellven.game.api.GameObject
import braynstorm.hellven.game.api.GameObjectContainer
import com.badlogic.gdx.graphics.g2d.Sprite

abstract class AbstractGameObject(final override val id: String, override val texture: Sprite) : GameObject {
	override var container: GameObjectContainer? = null
	override fun onContainerChanged(newContainer: GameObjectContainer?) {
		container?.silentRelease(this)
		container = newContainer
	}
}
