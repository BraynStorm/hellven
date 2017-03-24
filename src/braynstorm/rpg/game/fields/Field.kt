package braynstorm.rpg.game.fields

import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.entity.Player
import braynstorm.rpg.graphics.Rectangle
import braynstorm.rpg.graphics.textures.Texture
import org.joml.Vector2i

/**
 * One spot where an object is contained (gameobject, player, monster, etc.)
 * Created by Braynstorm on 25.2.2017 г..
 */
abstract class Field(var type: FieldType, val globalPosition: GlobalPosition) {
	
	val rectangle: Rectangle
		get() = Rectangle(Vector2i(globalPosition.x * 16, globalPosition.y * 16), texture = getImage())
	
	abstract fun canStepOver(player: Player): Boolean
	
	abstract fun canStepOut(player: Player, requestedGlobalPosition: GlobalPosition): Boolean
	
	/**
	 * What happens when something steps on the field
	 */
	abstract fun onStepOver(player: Player)
	
	
	abstract fun onStepOut(player: Player, requestedGlobalPosition: GlobalPosition)
	
	abstract fun getImage(): Texture
}
