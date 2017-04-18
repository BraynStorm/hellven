package braynstorm.hellven.game

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.entity.EntityClass

/**
 * TODO Add class description
 * Created by Braynstorm on 29.3.2017 г..
 */
object Realm {
	var world: World? = null
	
	object PlayerInfo {
		var playerClass: EntityClass? = null
		var playerName: String? = null
	}
	
	fun switchWorld(worldID: String) {
		world?.destroy()
		val newWorld = Hellven.getWorld(worldID)
		world = newWorld
		Hellven.gameScreen?.setWorld(world!!)
	}
}
