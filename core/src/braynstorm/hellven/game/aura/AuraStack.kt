package braynstorm.hellven.game.aura

import braynstorm.hellven.game.api.Entity
import braynstorm.hellven.game.Utils

/**
 * Representation of the effect of an aura over a duration on a given [EntityLiving].
 *
 *
 *
 * @param aura The aura effect of the current "stack"
 * @param startingDuration The duration of the aura, in AuraTicks (@see [Aura]).
 */
open class AuraStack(
		val aura: Aura,
		val seconds: Float,
		val persistsThroughDeath: Boolean = false) {
	
	var ticksLeft: Float = seconds * 10f
		private set
	
	var paused = false
	
	lateinit var receiver: Entity
	
	open val infinite = false
	
	fun tick(): Boolean {
		aura.tick(receiver)
		
		if (!infinite) {
			ticksLeft -= 1f
		}
		
		//println("Ticks left $ticksLeft")
		if (Utils.FloatMath.epsilonEqual(ticksLeft, 0f)) {
			return true
		}
		
		return false
	}
	
	fun cancelAura() {
		ticksLeft = 0f
	}
	
	override fun hashCode(): Int {
		return aura.hashCode()
	}
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other?.javaClass != javaClass) return false
		
		other as AuraStack
		
		if (aura != other.aura) return false
		
		return true
	}
	
	val secondsLeft: Int get() = (ticksLeft / 10f).toInt()
	
	
}
