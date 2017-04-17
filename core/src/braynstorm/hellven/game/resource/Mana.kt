package braynstorm.hellven.game.resource

import braynstorm.hellven.game.AbstractResourcePool
import braynstorm.hellven.game.Attribute
import com.badlogic.gdx.graphics.Color

/**
 *
 *
 * Like Mana in World of Warcraft
 */
class Mana @JvmOverloads constructor(capacity: Float, current: Float = capacity / 2f) : AbstractResourcePool(capacity, current, Color.valueOf("#00A2FF")) {
	override fun tickResource() {
		if (entity.isInCastStress())
			fill(entity[Attribute.MANA_PER_SEC] / 5f, true)
		else
			fill(entity[Attribute.MANA_PER_SEC], true)
	}
	
}
