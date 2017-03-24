package braynstorm.rpg.game.gui

import braynstorm.rpg.game.entity.EntityLiving
import braynstorm.rpg.game.entity.EntityType
import braynstorm.rpg.graphics.KeyUpEvent
import braynstorm.rpg.graphics.Rectangle
import braynstorm.rpg.graphics.textures.TextureManager
import org.joml.Vector2i
import org.lwjgl.glfw.GLFW

/**
 * TODO Add class description
 * Created by Braynstorm on 23.3.2017 г..
 */
class EntityRectangle(val entity: EntityLiving)
	: Rectangle(
		Vector2i(entity.globalPosition.x * 16, entity.globalPosition.y * 16),
		z = 0.1f,
		texture = if (entity.type == EntityType.PLAYER) {
			TextureManager.getSingleTexture("player")
		} else {
			TextureManager.getSingleTexture("monster")
		}) {
	
	init {
		// TODO organize this HELL
		
		KeyUpEvent on {
			when (it.keycode) {
				GLFW.GLFW_KEY_LEFT  -> {
					position.x -= width
					setDirty()
				}
				
				GLFW.GLFW_KEY_RIGHT -> {
					position.x += width
					setDirty()
				}
				
				GLFW.GLFW_KEY_UP    -> {
					position.y += height
					setDirty()
				}
				
				GLFW.GLFW_KEY_DOWN  -> {
					position.y -= height
					setDirty()
				}
			}
		}
	}
	
	fun updateRect() {
		
	}
}
