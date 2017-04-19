package braynstorm.hellven.gui.elements

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.items.ItemStack
import braynstorm.hellven.game.entity.PlayerEntity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align

/**
 * TODO Add class description
 * Created by Braynstorm on 16.4.2017 г..
 */
class FrameInventory(var player: PlayerEntity? = null) : Window("Inventory", Hellven.skin) {
	
	init {
		padTop(30f)
		
		width = 150f
		height = 400f
		
		setPosition(Gdx.graphics.width.toFloat(), 0f, Align.bottomRight)
	}
	
	val items: MutableList<ItemStack> = arrayListOf()
	
	override fun draw(batch: Batch?, parentAlpha: Float) {
		super.draw(batch, parentAlpha)
		
		items.forEach {
			it.item.icon.setBounds(x, y, width, height)
			it.item.icon.draw(batch)
		}
	}
	
}
