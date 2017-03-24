package braynstorm.rpg.game.gameobjects

import braynstorm.rpg.game.entity.EntityLiving
import braynstorm.rpg.game.fields.GameObjectField
import braynstorm.rpg.game.items.Itemstack
import braynstorm.rpg.graphics.textures.StateTexture
import braynstorm.rpg.graphics.textures.TextureManager

/**
 * Gameobject - Loot chest
 * Created by Braynstorm on 25.2.2017 г..
 */
class LootChest(items: Set<Itemstack>) : GameObject() {
	override fun onInteract(field: GameObjectField, entity: EntityLiving) {
		throw UnsupportedOperationException("LootChest.OnInteract not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	override fun onStepOver(field: GameObjectField, entity: EntityLiving) {}
	override fun onStepOut(field: GameObjectField, entity: EntityLiving) {}
	
	
	var closed = true
		set(value) {
			texture.state = if (value) {
				1
			} else {
				0
			}
			
			field = value
		}
	
	
	override val texture: StateTexture = TextureManager.getStateTexture("go_loot_chest")
	
	
}
