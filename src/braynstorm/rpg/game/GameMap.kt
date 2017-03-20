package braynstorm.rpg.game

import braynstorm.rpg.fields.EmptyField
import braynstorm.rpg.fields.Field
import braynstorm.rpg.game.entity.Player
import java.util.HashMap

/**
 * Contains the fields for the place where the player is currently.
 *
 * Generated with parameters and random everytime.
 * Created by Braynstorm on 25.2.2017 г..
 */
class GameMap(val difficulty: Int) {
	val width = 40
	val height = 30
	
	// all the players (for now, there will be only one)
	private val players: MutableMap<Int, Player> = HashMap()
	
	private val field: Array<Field> = Array(width * height, {
		EmptyField(GlobalPosition(this, it % (width), it / width))
	})
	
}
