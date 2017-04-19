package braynstorm.hellven.game.api

import braynstorm.hellven.game.ResourceMap

/**
 * Describes something that can appear in the TargetFrame by the player.
 */
interface Targetable {
	val name: String
	
	
	val resources: ResourceMap
	
	fun hasResource(resource: Class<ResourcePool>): Boolean
}
