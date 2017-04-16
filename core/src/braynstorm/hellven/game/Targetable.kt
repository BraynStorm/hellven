package braynstorm.hellven.game

/**
 * Describes something that can appear in the TargetFrame by the player.
 */
interface Targetable {
	val name: String
	
	
	val resources: ResourceMap
	
	fun hasResource(resource: Class<ResourcePool>): Boolean
}
