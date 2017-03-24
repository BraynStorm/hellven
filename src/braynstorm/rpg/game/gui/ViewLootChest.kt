package braynstorm.rpg.game.gui

import braynstorm.rpg.game.gameobjects.LootChest
import braynstorm.rpg.graphics.Drawable
import org.joml.Vector2i
import org.joml.Vector3f

/**
 * TODO Add class description
 * Created by Braynstorm on 22.3.2017 г..
 */
class ViewLootChest(val chest: LootChest, override val position: Vector2i) : Drawable {
	override val width: Int = 0
	override val height: Int = 0
	override val z: Float = -0.5f
	
	init {
		
	}
	
	override fun draw() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	override var color: Vector3f = Vector3f(0f, 0f, 0f)
}
