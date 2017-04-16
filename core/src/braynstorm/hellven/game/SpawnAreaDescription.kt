package braynstorm.hellven.game

import com.badlogic.gdx.utils.JsonValue

/**
 * TODO Add class description
 * Created by Braynstorm on 14.4.2017 г..
 */

class SpawnArea(
		val level: Int,
		val entityID: String,
		val count: Int
) {
	companion object {
		fun valueOf(jsonValue: JsonValue): SpawnArea{
		
		}
	}
}
