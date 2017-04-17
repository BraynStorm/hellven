package braynstorm.hellven.game.cells

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.Direction
import braynstorm.hellven.game.GameWorld
import braynstorm.hellven.game.WorldCell
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import java.util.EnumMap

/**
 * TODO Add class description
 * Created by Braynstorm on 1.4.2017 г..
 */
abstract class AbstractWorldCell(var background: Drawable) : Widget(), WorldCell {
	override lateinit var world: GameWorld
	override lateinit var pixelLocation: Vector2
	override lateinit var cellLocation: Vector2
	
	val neighbours: MutableMap<Direction, WorldCell?> = EnumMap(Direction::class.java)
	
	override fun get(direction: Direction): WorldCell? {
		return neighbours[direction]
	}
	
	override fun set(direction: Direction, value: WorldCell?) {
		neighbours[direction] = value
	}
	
	override fun getPrefWidth(): Float = Hellven.cellSizeF
	override fun getPrefHeight(): Float = Hellven.cellSizeF
	override fun getMinWidth(): Float = Hellven.cellSizeF
	override fun getMinHeight(): Float = Hellven.cellSizeF
	override fun getMaxWidth(): Float = Hellven.cellSizeF
	override fun getMaxHeight(): Float = Hellven.cellSizeF
	
	override fun toString(): String {
		return "AbstractWorldCell[x=$x, y=$y]"
	}
	
	override fun draw(batch: Batch?, parentAlpha: Float) {
		super.draw(batch, parentAlpha)
		
		background.draw(batch, x, y, width, height)
		
	}
}
