package braynstorm.hellven.game.gameobjects

import braynstorm.hellven.Hellven
import braynstorm.hellven.Localization
import braynstorm.hellven.game.Realm
import braynstorm.hellven.game.api.Entity

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
