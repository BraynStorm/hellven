package braynstorm.rpg.game.entity

import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.fields.GameObjectField
import braynstorm.rpg.game.mechanics.EmptyResource
import braynstorm.rpg.game.mechanics.Health
import braynstorm.rpg.game.mechanics.IntResourcePool
import braynstorm.rpg.graphics.KeyDownEvent
import org.lwjgl.glfw.GLFW

/**
 * A player... duh..
 * Created by Braynstorm on 25.2.2017 г..
 */
class Player(
		var name: String = "Jon Doe",
		health: Health,
		resource: IntResourcePool?,
		isDead: Boolean = false,
		playerClass: EntityClass,
		gps: GlobalPosition)
	: EntityLiving(
		health,
		resource ?: EmptyResource(),
		isDead,
		EntityType.PLAYER,
		playerClass,
		gps
) {
	init {
		
		
		KeyDownEvent on {
			if (it.keycode == GLFW.GLFW_KEY_E) {
				val field = gps.map[gps.x, gps.y]
				if (field is GameObjectField) {
					field.gameObject.onInteract(field, this)
				}
			}
		}
	}
	
	
}

