package braynstorm.hellven.gui.elements

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.InventoryContainer
import braynstorm.hellven.game.PlayerEntity
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ktx.style.get

/**
 * TODO Add class description
 * Created by Braynstorm on 16.4.2017 г..
 */
class FrameInventory(val player: PlayerEntity) : Table() {
	
	init {
		background = Hellven.skin["bar_background"]
		
		(player as InventoryContainer)
	}
	
	
}
