package braynstorm.hellven.game.ability

import braynstorm.hellven.game.api.Entity
import com.badlogic.gdx.math.Vector2

interface AreaTargetAbility : Ability {
	fun use(user: Entity?, target: Vector2)
	
	/**
	 * @return **True** is both the user is a valid user for this ability and the target is a valid target for this ability
	 * and the user can use this ability on this entity.
	 *
	 * **False** otherwise.
	 */
	fun isTargetValid(user: Entity?, target: Vector2): Boolean
}
