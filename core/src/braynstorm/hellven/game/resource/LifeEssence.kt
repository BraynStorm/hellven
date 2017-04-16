package braynstorm.hellven.game.resource

import braynstorm.hellven.game.AbstractResourcePool
import com.badlogic.gdx.graphics.Color

/**
 * - Initial state: Empty.
 * - Accumulation:
 *    - Damage done.
 * - Depletion:
 *    - Ability Use
 */
class LifeEssence @JvmOverloads constructor(capacity: Float, current: Float = 0F) : AbstractResourcePool(capacity, current, Color.valueOf("#FF6959")) {
	override fun tickResource() {
		TODO("Implement LifeEssence")
	}
}
