package braynstorm.hellven.game.scripting

import braynstorm.hellven.game.api.NPCEntity

interface AIScript {
	/**
	 * This method will be called when the Timer for AI ticks for an entity.
	 *
	 * It will be called for each entity separately.
	 */
	fun tick(entity: NPCEntity)
	
}
