package braynstorm.rpg.fields

import braynstorm.rpg.game.FieldType
import braynstorm.rpg.game.GlobalPosition

/**
 * Any field that contains a GameObject.
 * (eg. Levers, Doors, Traps)
 * Created by Braynstorm on 25.2.2017 г..
 */
abstract class GameObjectField(globalPosition: GlobalPosition) : Field(FieldType.OBJECT, globalPosition) {
	
}
