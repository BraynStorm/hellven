package braynstorm.hellven.game.ability

import braynstorm.hellven.game.Entity

interface FixedCostAbility : Ability {
	fun userHasEnoughResource(user: Entity): Boolean
}
