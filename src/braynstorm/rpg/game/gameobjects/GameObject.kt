package braynstorm.rpg.game.gameobjects

import braynstorm.rpg.game.entity.EntityLiving
import braynstorm.rpg.game.fields.GameObjectField
import braynstorm.rpg.graphics.textures.Texture

/**
 * Represents a GameObject (eg. Lever, Trap, Door).
 * The method onInteract([GameObjectField], [EntityLiving]
 * Created by Braynstorm on 25.2.2017 г..
 */
abstract class GameObject {
	abstract val texture: Texture
	
	abstract fun onInteract(field: GameObjectField, entity: EntityLiving)
	abstract fun onStepOver(field: GameObjectField, entity: EntityLiving)
	abstract fun onStepOut(field: GameObjectField, entity: EntityLiving)
	
}
