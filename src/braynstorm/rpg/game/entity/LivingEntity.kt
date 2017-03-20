package braynstorm.rpg.game.entity

import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.mechanics.Aura
import braynstorm.rpg.game.mechanics.Health
import braynstorm.rpg.game.mechanics.ResourcePool

/**
 * A living thing
 * Created by Braynstorm on 13.3.2017 г..
 */
abstract class LivingEntity(
		var health: Health,
		var resource: ResourcePool<*>,
		var isDead: Boolean,
		val type: EntityType,
		val entityClass: EntityClass = EntityClass.UNKNOWN,
		var globalPosition: GlobalPosition
) {
	
	val auras: MutableSet<Aura> = HashSet()
	
	
}

/**
 * The types of Entity there can be.
 * Created by Braynstorm on 14.3.2017 г..
 */
enum class EntityType {
	NORMAL_MONSTER,
	CHAMPION_MONSTER,
	BOSS_MONSTER,
	PLAYER,
	
	
}

/**
 * The class of the Entity.
 * Useful for tooltips, but also, the player object uses this.
 * Created by Braynstorm on 14.3.2017 г..
 */
enum class EntityClass {
	UNKNOWN,
	BOSS,
	
	WARRIOR,
	MAGE,
	WARLOCK,
	ROGUE,
	PRIEST,
	SHAMAN,
}
