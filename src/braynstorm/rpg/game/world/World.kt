package braynstorm.rpg.game.world

import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.fields.Field
import braynstorm.rpg.game.fields.LeverField
import braynstorm.rpg.game.gameobjects.Lever

/**
 * Contains the fields for the place where the player is currently.
 *
 * Generated with parameters and random everytime.
 * Created by Braynstorm on 25.2.2017 г..
 */
class World(val difficulty: Int) {
	val width = 80
	val height = 60
	
	val fields: Array<Field> = Array(width * height, {
		val x = it % width
		val y = it / width
		
		LeverField(GlobalPosition(this, x, y), Lever({ field, entity ->
			println(field.globalPosition)
			println(entity.type)
		}))
//		EmptyField(GlobalPosition(this, it % (width), it / width))
	})
	
	operator fun get(x: Int, y: Int): Field? {
		return fields[x + y * width]
	}
	
	
	operator fun set(x: Int, y: Int, field: Field?) {
		if (field == null) {
			fields[x + y * width] = LeverField(GlobalPosition(this, x, y), Lever({ field, entity ->
				println(field.globalPosition)
				println(entity.type)
			}))
		} else {
			fields[x + y * width] = field
			assert(field.globalPosition.map == this)
			assert(field.globalPosition.x == x)
			assert(field.globalPosition.y == y)
		}
	}
	
	
}
