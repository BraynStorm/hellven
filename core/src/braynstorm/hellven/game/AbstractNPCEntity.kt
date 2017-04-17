package braynstorm.hellven.game

import braynstorm.hellven.Localization
import braynstorm.hellven.game.entity.EntityClass
import braynstorm.hellven.game.scripting.Scripts

/**
 * An abstract implementation of [NPCEntity]
 */
abstract class AbstractNPCEntity(
		override val id: String,
		override val ai: String,
		entityType: EntityType,
		entityClass: EntityClass,
		resourceMap: ResourceMap) : AbstractMovingEntity(entityType, entityClass, resourceMap), NPCEntity {
	
	
	override val name: String
		get() = Localization.formatNPC(id)
	
	/**
	 * Feeds the tick to the AI script for processing.
	 */
	override fun tickNPCAI() {
		Scripts[ai].tick(this)
	}
	
	
}
