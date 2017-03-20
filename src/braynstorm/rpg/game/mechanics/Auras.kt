package braynstorm.rpg.game.mechanics

import braynstorm.events.SimpleEvent
import braynstorm.rpg.game.entity.LivingEntity

/**
 * Aura is a buff/debuff/status effect applied to a [braynstorm.rpg.entity.LivingEntity]
 * This class contains only a declaration for an aura.
 * For an aura applied to a LivingEntity, @see [AuraStack]
 *
 * **Aura Ticks**
 * 1 Aura Tick == 0.1s = 10ms
 *
 * Created by Braynstorm on 14.3.2017 г..
 */
abstract class Aura(val id: Int = Aura.Companion.EZID, val name: String, val maxAppliections: Int = 1) {
	
	abstract fun tick(receiver: LivingEntity)
	
	companion object {
		var EZID = 0
			get() {
				return Aura.EZID++
			}
	}
}

/**
 * Representation of the effect of an aura over a duration on a given [LivingEntity].
 *
 *
 *
 * @param aura The aura effect of the current "stack"
 * @param startingDuration The duration of the aura, in AuraTicks (@see [Aura]).
 */
open class AuraStack(
		val aura: Aura,
		val receiver: LivingEntity,
		val startingDuration: Int,
		val persistsThroughDeath: Boolean = false) {
	
	var duration = startingDuration
		private set
	
	var paused = false
	
	open val infinite = false
	
	init {
		// onAuraTickEvent
		AuraTickEvent on {
			if (it === receiver) {
				tick()
			}
			
			if (!infinite && duration == 0) {
				AuraFadeEvent fire this
			}
		}
	}
	
	private fun tick() {
		if (!paused) {
			aura.tick(receiver)
			
			if (!infinite) {
				duration--
			}
		}
	}
	
	
	fun pause() {
		paused = true
	}
	
	fun resume() {
		paused = false
	}
	
	fun cancelAura() {
		duration = 0
	}
	
	
}

class InfiniteAuraStack(aura: Aura, persistentThroughDeath: Boolean, receiver: LivingEntity) : AuraStack(aura, receiver, -1, persistentThroughDeath) {
	override val infinite = true
}

object AuraTickEvent : SimpleEvent<LivingEntity>()
object AuraFadeEvent : SimpleEvent<AuraStack>()
