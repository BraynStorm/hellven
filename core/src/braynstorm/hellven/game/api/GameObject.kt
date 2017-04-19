package braynstorm.hellven.game.api

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2

/**
 * Anything that can act / contain items
 */
interface GameObject : HasLocation {
	val id: String
	val name: String
	
	val container: GameObjectContainer?
	override val cellLocation: Vector2 get() = container?.cellLocation ?: Vector2.Zero
	override val pixelLocation: Vector2 get() = container?.pixelLocation ?: Vector2.Zero
	
	fun onContainerChanged(newContainer: GameObjectContainer?)
	
	val texture: Sprite
	fun onInteract(entity: Entity)
}
