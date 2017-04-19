package braynstorm.hellven.game.dataparsing

import braynstorm.hellven.game.ParseException
import com.badlogic.gdx.utils.JsonValue

/**
 * TODO Add class description
 * Created by Braynstorm on 18.4.2017 г..
 */
class RawGameObjectDescription(
		val id: String,
		val settings: JsonValue
) {
	companion object {
		fun valueOf(json: JsonValue): RawGameObjectDescription {
			//@formatter:off
			val id          = json.get("id")?.asString()    ?: throw ParseException("GameObject definition has no ID field")
			val settings    = json.get("settings")          ?: throw ParseException("GameObject definition has no SETTINGS field")
			
			return RawGameObjectDescription(id, settings)
			//@formatter:on
			
		}
	}
	
	fun parse(): GameObjectDescription {
		when (id) {
			"teleporter" ->
				return TeleporterDescription(settings.getString("world") ?: throw ParseException("Teleporter definition has no SETTINGS:WORLD field"))
			"loot_chest" ->
				return LootChestDescription() // TODO
			else         -> {
				throw ParseException("Unknown game object '$id'")
			}
		}
		
	}
}

