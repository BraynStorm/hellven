package braynstorm.rpg.game.gameobjects

import braynstorm.rpg.fields.GameObjectField
import braynstorm.rpg.game.entity.LivingEntity
import braynstorm.rpg.gui.textures.StateTexture
import braynstorm.rpg.gui.textures.TextureManager

/**
 * A Lever that a [LivingEntity] can interact witch
 * Created by Braynstorm on 25.2.2017 г..
 */
class Lever(isUp: Boolean = true, val onInteract: (GameObjectField, LivingEntity) -> Unit) : GameObject() {
	
	override val texture: StateTexture = TextureManager.getStateTexture("go_lever")
	
	var isUp: Boolean = isUp
		set(value) {
			texture.state = if (value) {
				1
			} else {
				0
			}
			
			field = value
		}
	
	override fun onInteract(field: GameObjectField, entity: LivingEntity) {
		onInteract.invoke(field, entity)
	}
	
	override fun onStepOver(field: GameObjectField, entity: LivingEntity) {}
	
	
	override fun onStepOut(field: GameObjectField, entity: LivingEntity) {}
}
