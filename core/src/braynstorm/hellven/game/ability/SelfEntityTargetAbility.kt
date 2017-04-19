package braynstorm.hellven.game.ability

import braynstorm.hellven.game.api.Entity

interface SelfEntityTargetAbility : EntityTargetAbility {
	fun use(user: Entity)
	
	/**
	 * @return **True** is both the user is a valid user for this ability and the target is a valid target for this ability
	 * and the user can use this ability on this entity.
	 *
	 * **False** otherwise
	 */
	override fun isTargetValid(user: Entity?, target: Entity): Boolean {
		return target == user
	}
}
