package braynstorm.hellven.gui.elements

import braynstorm.hellven.Hellven
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.Drawable

/**
 * TODO Add class description
 * Created by Braynstorm on 2.4.2017 г..
 */
class AbilityBar(skin: Skin = Hellven.gameSkin, styleName: String = "default") : Table() {
	
	private val style = skin.get(styleName, Style::class.java)
	val buttonBackground = style.buttonBackground!!
	
	init {
//		background = style.barBackground!!
		add(Table().apply {
			padLeft(3f)
			defaults().fill()
			add(ImageButton(buttonBackground, null as Drawable?))
			add(ImageButton(buttonBackground, null as Drawable?))
			add(ImageButton(buttonBackground, null as Drawable?))
			add(ImageButton(buttonBackground, null as Drawable?))
			add(ImageButton(buttonBackground, null as Drawable?))
			add(ImageButton(buttonBackground, null as Drawable?))
			add(ImageButton(buttonBackground, null as Drawable?))
			add(ImageButton(buttonBackground, null as Drawable?))
			add(ImageButton(buttonBackground, null as Drawable?))
			padRight(3f)
		}).expandX().bottom()
	}
	
	override fun getPrefHeight(): Float {
		return 48f + 6f
	}
	
	override fun getPrefWidth(): Float {
		return 48f * 9f + 6f
	}
	
	class Style {
		//		var barBackground: Drawable? = null
		var buttonBackground: Drawable? = null
	}
}
