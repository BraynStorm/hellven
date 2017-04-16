package braynstorm.hellven.game.resource

import braynstorm.hellven.game.AbstractResourcePool
import com.badlogic.gdx.graphics.Color

/**
 * - Initial state: Empty.
 * - Accumulation:
 *    - Damage done.
 *    - Damage taken.
 *    - Stay in combat.
 * - Depletion:
 *    - Ability Use.
 *    - Stay out-of-combat
 * - Notes:
 *    - If you have full Rage, every generation will heal you (increase Health) (affected positively by HP5) (does not apply to rage-generating abilities).
 * Like Rage in World of Warcraft
 */
class Rage @JvmOverloads constructor(capacity: Float = 100F, current: Float = 0F) : AbstractResourcePool(capacity, current, Color.valueOf("#C71300")) {
	override fun tickResource() {
		if (entity.inCombat) {
			val filled = fill(1F, true)
			if (filled < 1) {
				// TODO add check for
				entity.health.fill(1 - filled, true)
			}
		} else {
			drain(1.5F, true)
		}
	}
}
