package braynstorm.hellven.game.ability

import braynstorm.hellven.game.attributes.Attribute
import braynstorm.hellven.game.api.Entity

data class Damage(val source: Entity, val type: Type, val amount: Float) {
	enum class Type {
		PHYSICAL,
		FROST,
		FIRE,
		ARCANE,
		SHADOW,
		HOLY,
		NATURE,
		RAW
	}
	
	
	fun calculateAgainst(entity: Entity): Float {
		if (type != Type.PHYSICAL) {
			return amount
		}
		
		/**
		 * Formula:
		 *
		 * mitigatedAmount= amount * Mitigation
		 *
		 * weighted armor = armor * (0.10f - 0.002f * level)
		 *
		 * final damage = mitigatedAmount -
		 */
		//TODO this is not final, at all...
		return amount - entity[Attribute.ARMOR] * (0.10f /* - 0.002f * level */)
	}
	
	operator fun times(number: Float): Damage {
		return Damage(source, type, amount * number)
	}
	
	operator fun div(number: Float): Damage {
		return Damage(source, type, amount / number)
	}
	
	
}
