package braynstorm.rpg.game.fields

import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.gameobjects.GameObject

/**
 * Any field that contains a GameObject.
 * (eg. Levers, Doors, Traps)
 * Created by Braynstorm on 25.2.2017 г..
 */
abstract class GameObjectField(
		globalPosition: GlobalPosition,
		val gameObject: GameObject) : Field(FieldType.GAMEOBJECT, globalPosition) {
	
}
