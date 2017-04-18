package braynstorm.hellven.game.cells

import braynstorm.hellven.game.Entity
import braynstorm.hellven.game.GameObject
import braynstorm.hellven.game.GameObjectContainer
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.utils.Drawable

/**
 * TODO Add class description
 * Created by Braynstorm on 18.4.2017 г..
 */
class GameObjectWorldCell(background: Drawable) : SolidWorldCell(background), GameObjectContainer {
	override fun onInteract(entity: Entity) {
		gameObject?.onInteract(entity)
	}
	
	override var gameObject: GameObject? = null
	
	override fun hold(obj: GameObject) {
		val oldGo = gameObject
		
		if (obj == oldGo)
			return
		
		if (oldGo != null) {
			release(oldGo)
		}
		
		gameObject = obj
		gameObject?.onContainerChanged(this)
	}
	
	override fun release(obj: GameObject) {
		if (obj != gameObject) {
			return
		}
		
		gameObject = null
		gameObject?.onContainerChanged(null)
	}
	
	override fun silentRelease(obj: GameObject) {
		if (obj != gameObject) {
			return
		}
		
		gameObject = null
	}
	
	override fun draw(batch: Batch?, parentAlpha: Float) {
		super.draw(batch, parentAlpha)
		val go = gameObject ?: return
		
		go.texture.setPosition(x, y)
		go.texture.draw(batch)
	}
	
}
