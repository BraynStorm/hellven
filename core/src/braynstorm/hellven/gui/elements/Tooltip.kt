package braynstorm.hellven.gui.elements

import braynstorm.hellven.Hellven
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.utils.Align

/**
 * TODO Add class description
 * Created by Braynstorm on 18.4.2017 г..
 */
class Tooltip() : Widget() {
	
	val background = Hellven.skin.getDrawable("bar_background")
	val font = Hellven.skin.getFont("Nevis18")
	
	var text: String = ""
	var shown = false
	
	init {
		width = 200f
		height = 200f
		setOrigin(Align.bottomLeft)
		TODO("Implement Tooltip further")
	}
	
	fun showWithText(string: String) {
		text = string
		shown = true
	}
	
	override fun draw(batch: Batch?, parentAlpha: Float) {
		if (shown) {
			x = Gdx.input.x.toFloat()
			y = Gdx.graphics.height - Gdx.input.y.toFloat()
			super.draw(batch, parentAlpha)
			background.draw(batch, x, y, width, height)
			font.draw(batch, text, x, y - 5f + height, width, Align.left, true)
			
			
		}
	}
	
	fun hide() {
		shown = false
	}
	
}
