package braynstorm.hellven.game.dataparsing

import braynstorm.hellven.Hellven
import braynstorm.hellven.Localization
import braynstorm.hellven.game.Entity
import braynstorm.hellven.game.GameObject
import braynstorm.hellven.game.ParseException
import braynstorm.hellven.game.gameobjects.AbstractGameObject
import braynstorm.hellven.game.gameobjects.Teleporter
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

abstract class GameObjectDescription(val id: String) {
	abstract fun createGameObject(): GameObject
}

class LootChestDescription() : GameObjectDescription("loot_chest") {
	override fun createGameObject(): GameObject {
		return LootChest()
	}
	
}

class LootChest() : AbstractGameObject("loot_chest",  Hellven.gameSkin.getSprite("ability_button_background")) {
	override val name: String
		get() = Localization.formatUI("go_teleporter")
	
	
	override fun onInteract(entity: Entity) {
		TODO("onInteract is not implemented")
	}
	
}

class TeleporterDescription(val world: String) : GameObjectDescription("teleporter") {
	override fun createGameObject(): GameObject {
		return Teleporter(world)
	}
	
}
