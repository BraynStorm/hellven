package braynstorm.hellven.game

import com.badlogic.gdx.utils.JsonValue

/**
 * Describes the parameters for a Spawn Area in a world.
 * Created by Braynstorm on 14.4.2017 г..
 */

class SpawnAreaDescription(
		val minlevel: Int,
		val maxlevel: Int,
		val entityID: String,
		val count: Int
) {
	companion object {
		fun valueOf(json: JsonValue): SpawnAreaDescription {
			// @formatter:off
			val minLevel = (json.get("minlevel")    ?: json.get("level"))?.asInt()      ?: throw ParseException("No level/minlevel specified for SpawnArea")
			val maxLevel = (json.get("maxlevel")    ?: json.get("level"))?.asInt()      ?: throw ParseException("No level/maxlevel specified for SpawnArea")
			val entityID = (json.get("entity")                          )?.asString()   ?: throw ParseException("No entity specified for SpawnArea")
			val count    = (json.get("count")                           )?.asInt()      ?: throw ParseException("No count specified for SpawnArea")
			// @formatter:on
			
			return SpawnAreaDescription(minLevel,maxLevel, entityID, count)
			
		}
	}
}
