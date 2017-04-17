package braynstorm.hellven.game

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

/**
 * A cell form the game world. [World]
 */
interface WorldCell {
	val world: GameWorld
	var cellLocation: Vector2
	var pixelLocation: Vector2
	/**
	 * Allows for easy traversal between cells.
	 */
	operator fun get(direction: Direction): WorldCell?
	
	/**
	 * Allows for easy traversal between cells.
	 */
	operator fun set(direction: Direction, value: WorldCell?)
	
	fun draw(batch: Batch?, parentAlpha: Float)
}
