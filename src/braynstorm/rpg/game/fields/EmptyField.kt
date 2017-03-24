package braynstorm.rpg.game.fields

import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.entity.Player
import braynstorm.rpg.graphics.textures.Texture
import braynstorm.rpg.graphics.textures.TextureManager

/**
 *
 * Created by Braynstorm on 26.2.2017 г..
 */
class EmptyField(globalPosition: GlobalPosition) : Field(FieldType.EMPTY, globalPosition) {
	override fun canStepOver(player: Player): Boolean {
		return true
	}
	
	override fun canStepOut(player: Player, requestedGlobalPosition: GlobalPosition): Boolean {
		return true
	}
	
	override fun onStepOver(player: Player) {
		// do nothing
	}
	
	override fun onStepOut(player: Player, requestedGlobalPosition: GlobalPosition) {
		// do nothing
	}
	
	override fun getImage(): Texture {
		return TextureManager.getSingleTexture("empty_field")
	}
}
