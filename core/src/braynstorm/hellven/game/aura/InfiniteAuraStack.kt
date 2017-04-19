package braynstorm.hellven.game.aura

import braynstorm.hellven.game.api.Entity


class InfiniteAuraStack(aura: Aura, persistentThroughDeath: Boolean, receiver: Entity) : AuraStack(aura, -1f, persistentThroughDeath) {
	override val infinite = true
}


/*
TODO object AuraTickEvent : SimpleEvent<EntityLiving>()
object AuraFadeEvent : SimpleEvent<AuraStack>()
*/
