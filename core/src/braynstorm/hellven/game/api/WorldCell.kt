package braynstorm.hellven.game.api

import braynstorm.hellven.game.attributes.Direction
import com.badlogic.gdx.graphics.g2d.Batch

/**
 * A cell form the game world. [World]
 */
interface WorldCell : HasLocation {
	val world: GameWorld
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
