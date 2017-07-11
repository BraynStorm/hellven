package braynstorm.hellven.gui.elements

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.api.Entity
import braynstorm.hellven.game.resource.Health
import braynstorm.hellven.game.resource.Mana
import braynstorm.hellven.game.resource.Rage
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.Align

/**
 * TODO Add class description
 * Created by Braynstorm on 30.3.2017 г..
 */
class FrameEntity(entity: Entity?) : Table(Hellven.skin) {
	constructor() : this(null)
	
	val label = Label("", skin)
	val healthBar = ResourceBar(Health(1f), skin)
	val resourceBar = ResourceBar(Mana(1f), skin)
	val font = skin.getFont("Nevis18")!!
	var entity: Entity? = null
		set(value) {
			field = value
			
			if (value != null) {
				label.setText(value.name)
				healthBar.resource = value.resources[Health::class.java]!!
				sequenceOf(
						Rage::class.java,
						Mana::class.java
//						LifeEssence::class.java,
//						Willpower::class.java
				).forEach {
					if (value.resources.containsKey(it)) {
						resourceBar.resource = value.resources[it]
					}
				}
				healthBar.invalidate()
				resourceBar.invalidate()
			}
		}
	
	init {
		val style = skin.get(Style::class.java)
		background = style.background
//		debugAll()
		
		padLeft(style.leftPadding)
		padRight(style.rightPadding)
		
		label.setAlignment(Align.center)
		add(label).grow().bottom().height(20f)
		row().spaceBottom(0f)
		add(healthBar).grow().height(20f)
		row().spaceBottom(0f)
		add(resourceBar).grow().height(20f)
		
		this.entity = entity
		pack()
	}
	
	override fun getPrefWidth(): Float {
		return 200f
	}
	
	override fun getPrefHeight(): Float {
		return 65f
	}
	
	override fun draw(batch: Batch?, parentAlpha: Float) {
		if (entity != null) {
			super.draw(batch, parentAlpha)
			
			val auras = entity?.auras ?: return
			
			auras.forEachIndexed { index, auraStack ->
				val icon = auraStack.aura.icon
				icon.setPosition(x + index * icon.width, y - icon.height)
				font.color = Color.BLACK
				icon.draw(batch)
				font.draw(batch, auraStack.secondsLeft.toString(), x + index * icon.width, y - icon.height + 10f, icon.width, Align.center, false)
			}
		}
	}
	
	class Style {
		var leftPadding = 5f
		var rightPadding = 5f
		
		var labelStyle: Label.LabelStyle? = null
		var healthBarStyle: ResourceBar.Style? = null
		var resourceBarStyle: ResourceBar.Style? = null
		var background: Drawable? = null
	}
}
