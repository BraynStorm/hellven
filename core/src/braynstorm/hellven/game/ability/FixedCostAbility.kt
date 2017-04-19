package braynstorm.hellven.game.ability

import braynstorm.hellven.game.api.Entity

interface FixedCostAbility : Ability {
	fun userHasEnoughResource(user: Entity): Boolean
}
