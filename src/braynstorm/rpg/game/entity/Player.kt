package braynstorm.rpg.game.entity

import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.mechanics.EmptyResource
import braynstorm.rpg.game.mechanics.Health
import braynstorm.rpg.game.mechanics.ResourcePool

/**
 * A player... duh..
 * Created by Braynstorm on 25.2.2017 г..
 */
class Player(
		var name: String = "Jon Doe",
		health: Health,
		resource: ResourcePool<*>?,
		isDead: Boolean = false,
		playerClass: EntityClass,
		gps: GlobalPosition)
	: LivingEntity(
		health,
		resource ?: EmptyResource(),
		isDead,
		EntityType.PLAYER,
		playerClass,
		gps
) {
	
	
}
