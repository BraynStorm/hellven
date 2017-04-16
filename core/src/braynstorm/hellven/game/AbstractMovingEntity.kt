package braynstorm.hellven.game

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.entity.EntityClass
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar

abstract class AbstractMovingEntity(entityType: EntityType,
                                    entityClass: EntityClass,
                                    resources: ResourceMap) : AbstractEntity(entityType, entityClass, resources), TickReceiverMovement {
	override var moving: Boolean = false
	override var movementDirection: Direction = Direction.UP
	
	/**
	 * Movement ticks counter
	 */
	private var moveTime = 0
		set(value) {
			field = value
			movementBar.value = value.toFloat()
		}
	
	override fun tickMove() {
		val speed = this[Attribute.MOVEMENT_SPEED].toInt()
		if (moveTime < speed) {
			moveTime++
		}

//		println("$this : MovementTime:" + moveTime)
		
		if (moveTime == speed && moving) {
			if (world!!.entityTryMove(movementDirection, this))
				moveTime = 0
		}
		
		
	}
	
	override fun validateAttributes(): Boolean {
		if (super.validateAttributes()) {
			movementBar.setRange(0f, this[Attribute.MOVEMENT_SPEED])
			return true
		}
		return false
	}
	
	
	val movementBar = ProgressBar(0f, 1f, 1f, false, Hellven.skin)
	
	override fun draw(batch: Batch) {
		if (movementBar.value != movementBar.maxValue) {
			movementBar.setPosition(location.x, location.y)
			movementBar.setSize(Hellven.cellSizeF, 23f)
			movementBar.draw(batch, 1f)
		}
	}
}
