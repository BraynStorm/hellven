package braynstorm.hellven.game.resource

import braynstorm.hellven.game.AbstractResourcePool
import braynstorm.hellven.game.attributes.Attribute
import com.badlogic.gdx.graphics.Color

/**
 * - Initial state: Full.
 * - Accumulation:
 *     - In Combat (VERY slowly and only if not casting (affected positively by MP5 and Intellect)).
 *     - Resting (Moderately fast, affected positively by MP5 and Intellect)
 */
class Health @JvmOverloads constructor(capacity: Float, current: Float = capacity) : AbstractResourcePool(capacity, current, Color.valueOf("#00C31C")) {
	override fun tickResource() {
		if (!entity.inCombat)
			fill(entity[Attribute.HEALTH_PER_SEC], true)
	}
}
