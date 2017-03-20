package braynstorm.rpg.game.gameobjects

import braynstorm.rpg.fields.GameObjectField
import braynstorm.rpg.game.entity.LivingEntity
import braynstorm.rpg.game.items.Itemstack
import braynstorm.rpg.gui.textures.StateTexture
import braynstorm.rpg.gui.textures.TextureManager

/**
 * Gameobject - Loot chest
 * Created by Braynstorm on 25.2.2017 г..
 */
class LootChest(items: Set<Itemstack>) : GameObject() {
	override fun onInteract(field: GameObjectField, entity: LivingEntity) {
		throw UnsupportedOperationException("LootChest.OnInteract not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	override fun onStepOver(field: GameObjectField, entity: LivingEntity) {}
	override fun onStepOut(field: GameObjectField, entity: LivingEntity) {}
	
	
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
