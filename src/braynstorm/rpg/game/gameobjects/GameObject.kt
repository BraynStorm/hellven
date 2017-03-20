package braynstorm.rpg.game.gameobjects

import braynstorm.rpg.fields.GameObjectField
import braynstorm.rpg.game.entity.LivingEntity
import braynstorm.rpg.gui.textures.Texture

/**
 * Represents a GameObject (eg. Lever, Trap, Door).
 * The method onInteract([GameObjectField], [LivingEntity]
 * Created by Braynstorm on 25.2.2017 г..
 */
abstract class GameObject {
	abstract val texture: Texture
	
	abstract fun onInteract(field: GameObjectField, entity: LivingEntity)
	abstract fun onStepOver(field: GameObjectField, entity: LivingEntity)
	abstract fun onStepOut(field: GameObjectField, entity: LivingEntity)
	
}
