package braynstorm.rpg

import braynstorm.logger.GlobalKogger
import braynstorm.rpg.game.world.World

/**
 * Contains all [World]s
 * Created by Braynstorm on 23.3.2017 г..
 */

object Worlds {
	
	val maps = HashMap<String, World>()
	
	init {
//		maps[] = GameMap(0)
		
		
		GlobalKogger.logDebug("${maps.hashCode()}")
	}
}
