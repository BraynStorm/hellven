package braynstorm.hellven.game.ability

import braynstorm.hellven.game.api.Entity

interface EntityTargetAbility : Ability {
	fun use(user: Entity?, target: Entity, vararg arguments: Any)
	
	/**
	 * @return **True** is both the user is a valid user for this ability and the target is a valid target for this ability
	 * and the user can use this ability on this entity.
	 *
	 * **False** otherwise.
	 */
	fun isTargetValid(user: Entity?, target: Entity): Boolean
}
