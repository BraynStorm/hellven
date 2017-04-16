package braynstorm.hellven.game.cells

import com.badlogic.gdx.scenes.scene2d.utils.Drawable


class PlainWorldCell(background: Drawable) : AbstractSingleEntityContainer(background) {
	
	override fun toString(): String {
		return "PlainWorldCell[x=$x, y=$y, entity=${this.entity}]"
	}
	
}
