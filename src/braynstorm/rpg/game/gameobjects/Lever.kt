package braynstorm.rpg.game.gameobjects

import braynstorm.rpg.game.entity.EntityLiving
import braynstorm.rpg.game.fields.GameObjectField
import braynstorm.rpg.graphics.textures.StateTexture
import braynstorm.rpg.graphics.textures.TextureManager

/**
 * A Lever that a [EntityLiving] can interact witch
 * Created by Braynstorm on 25.2.2017 г..
 */
class Lever(val onInteract: (GameObjectField, EntityLiving) -> Unit, isUp: Boolean = true) : GameObject() {
	
	override val texture: StateTexture = TextureManager.getStateTexture("go_lever")
	
	var isUp: Boolean = isUp
		set(value) {
			texture.state = if (value) {
				0
			} else {
				1
			}
			
			field = value
		}
	
	override fun onInteract(field: GameObjectField, entity: EntityLiving) {
		onInteract.invoke(field, entity)
	}
	
	override fun onStepOver(field: GameObjectField, entity: EntityLiving) {}
	
	
	override fun onStepOut(field: GameObjectField, entity: EntityLiving) {}
}
