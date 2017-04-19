package braynstorm.hellven.gui.elements

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.api.ResourcePool
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.Align

/**
 * TODO Add class description
 * Created by Braynstorm on 29.3.2017 г..
 */
class ResourceBar(resource: ResourcePool?,
                  skin: Skin = Hellven.skin) : Widget() {
	
	val background: Drawable
	val filling: Sprite
	val font: BitmapFont
	val fontColor: Color
	
	private val style: Style = skin.get(Style::class.java)
	
	var resource: ResourcePool? = resource
		set(value) {
			field = value
			invalidate()
		}
	
	init {
		background = style.background!!
		filling = Sprite(style.filling!!)
		font = style.font!!
		fontColor = style.fontColor
		pack()
	}
	
	override fun draw(batch: Batch, parentAlpha: Float) {
		val resource = resource ?: return
		
		super.draw(batch, parentAlpha)
		
		background.draw(batch, x, y, width, height)
		filling.color = resource.color
		filling.setBounds(x + 2f, y + 2f, (width - 4f) * resource.percentage, height - 4f)
		filling.draw(batch)
		
		font.draw(batch, resource[ResourcePool.Format.PERCENT] + "  " + resource[ResourcePool.Format.CURRENT], x, y + height - font.lineHeight + 13f, width, Align.center, false)
	}
	
	override fun getMinWidth(): Float {
		return 30f
	}
	
	override fun getMinHeight(): Float {
		return 0F
	}
	
	override fun getMaxWidth(): Float {
		return 0f
	}
	
	override fun getMaxHeight(): Float {
		return 0f
	}
	
	override fun getPrefWidth(): Float {
		return 200f
	}
	
	override fun getPrefHeight(): Float {
		return if (resource == null) 0f else 30f
	}
	
	class Style {
		var filling: TextureRegion? = null
		var background: Drawable? = null
		var font: BitmapFont? = null
		var fontColor: Color = Color.WHITE
	}
}
