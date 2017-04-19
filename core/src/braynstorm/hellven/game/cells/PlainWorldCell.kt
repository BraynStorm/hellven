package braynstorm.hellven.game.cells

import braynstorm.hellven.game.api.Entity
import braynstorm.hellven.game.api.WorldCellEntityContainer
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.utils.Drawable

open class PlainWorldCell(background: Drawable) : AbstractWorldCell(background), WorldCellEntityContainer {
	
	
	override fun toString(): String {
		return "PlainWorldCell[x=$x, y=$y, entity=${this.entity}]"
	}
	
	override var entity: Entity? = null
		protected set
	
	override fun hold(entity: Entity) {
		if (entity === this.entity)
			return
		
		this.entity = entity
		entity.containerChanged(this)
	}
	
	override fun release(entity: Entity) {
		if (entity !== this.entity)
			return
		
		this.entity = null
		entity.containerChanged(null)
	}
	
	override fun releaseSilent(entity: Entity) {
		if (entity === this.entity)
			this.entity = null
	}
	
	override fun draw(batch: Batch?, parentAlpha: Float) {
		batch!!
		if (entity != null)
			super.draw(batch, parentAlpha)
		
		entity?.draw(batch)
	}
	
	
}
