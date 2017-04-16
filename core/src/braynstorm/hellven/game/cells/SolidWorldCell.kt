package braynstorm.hellven.game.cells

import braynstorm.hellven.game.World
import com.badlogic.gdx.scenes.scene2d.utils.Drawable

class SolidWorldCell(background: Drawable) : AbstractWorldCell(background) {
	override fun toString(): String {
		return "SolidWorldCell[x=$x, y=$y]"
	}
	
}
