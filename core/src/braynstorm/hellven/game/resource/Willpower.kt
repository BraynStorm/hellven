package braynstorm.hellven.game.resource

import braynstorm.hellven.game.AbstractResourcePool
import com.badlogic.gdx.graphics.Color

/**
 * - Inital state: half-way full. (affected positively by the Will stat)
 * - Accumulation:
 *    - Stay out-of-combat. (up to [capacity] / 2) (affected positively by the Will stat)
 *    - Ability use (up to full)
 * - Depletion:
 *    - Stay out-of-combat. (down to [capacity] / 2) (affected positively by the Will stat)
 *    - Ability use
 */
class Willpower @JvmOverloads constructor(capacity: Float, pool: Float = capacity / 2F) : AbstractResourcePool(capacity, pool, Color.valueOf("#CA59FF")) {
	override fun tickResource() {
		TODO("Implement Willpower::tickResource")
	}
}


