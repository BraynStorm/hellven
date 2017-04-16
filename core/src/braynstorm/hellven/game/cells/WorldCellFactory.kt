package braynstorm.hellven.game.cells

import braynstorm.hellven.Hellven
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.Drawable

/**
 * Creates world cells on demand.
 */
object WorldCellFactory {
	private lateinit var cellBackgroundPlain: Drawable
	private lateinit var cellBackgroundSolid: Drawable
	
	init {
		setSkin(Hellven.gameSkin)
	}
	
	fun setSkin(skin: Skin) {
		cellBackgroundPlain = skin.getDrawable("world_cell_plain")
		cellBackgroundSolid = skin.getDrawable("world_cell_solid")
	}
	
	
	fun createPlainCell(): PlainWorldCell {
		return PlainWorldCell(cellBackgroundPlain)
	}
	
	fun createSolidCell(): SolidWorldCell {
		return SolidWorldCell(cellBackgroundSolid)
	}
	
}

