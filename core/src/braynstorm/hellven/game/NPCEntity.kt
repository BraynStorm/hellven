package braynstorm.hellven.game

interface NPCEntity : Entity, TickReceiverNPCAI {
	
	/**
	 * The id of this entity.
	 *
	 * Unique for the entity not for the instance.
	 *
	 * For example, if we have an NPC with the id=npc_sullivan:
	 * There can be 10 Sullivans spawned in the world, and can be different levels, but they will have the same base attributes.
	 */
	val id: String
	
	/**
	 * The ID of the AI script for this entity.
	 *
	 * Note: it is not a straight AIScript because this way we can reload the script without
	 * having to update very single entity's [ai].
	 *
	 * Although, how often will we reload it? Asking the real questions here...
	 */
	val ai: String
}
