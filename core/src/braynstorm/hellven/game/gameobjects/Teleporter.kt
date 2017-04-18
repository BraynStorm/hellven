package braynstorm.hellven.game.gameobjects

import braynstorm.hellven.Hellven
import braynstorm.hellven.Localization
import braynstorm.hellven.game.Entity
import braynstorm.hellven.game.GameObject
import braynstorm.hellven.game.GameObjectContainer
import braynstorm.hellven.game.Realm
import com.badlogic.gdx.graphics.g2d.Sprite

abstract class AbstractGameObject(final override val id: String, override val texture: Sprite) : GameObject {
	override var container: GameObjectContainer? = null
	override fun onContainerChanged(newContainer: GameObjectContainer?) {
		container?.silentRelease(this)
		container = newContainer
	}
}

class Teleporter(val newWorld: String) : AbstractGameObject("teleporter", Hellven.gameSkin.getSprite("go_teleporter")) {
	override fun onInteract(entity: Entity) {
		if (entity.inCombat) {
			return
		}
		if(entity.cellLocation.dst(cellLocation) <= 1)
		Realm.switchWorld(newWorld)
	}
	
	
	override val name: String
		get() = Localization.formatUI("go_teleporter", newWorld)
	
}
