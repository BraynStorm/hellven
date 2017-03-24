package braynstorm.rpg.game.fields

import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.entity.Player
import braynstorm.rpg.game.gameobjects.Lever
import braynstorm.rpg.graphics.textures.Texture

/**
 * TODO Add class description
 * Created by Braynstorm on 23.3.2017 г..
 */
class LeverField(globalPosition: GlobalPosition, lever: Lever) : GameObjectField(globalPosition, lever) {
	override fun canStepOver(player: Player): Boolean {
		return true
	}
	
	override fun canStepOut(player: Player, requestedGlobalPosition: GlobalPosition): Boolean {
		return true
	}
	
	override fun onStepOver(player: Player) {
		
	}
	
	override fun onStepOut(player: Player, requestedGlobalPosition: GlobalPosition) {
		
	}
	
	override fun getImage(): Texture {
		return gameObject.texture
	}
	
}
