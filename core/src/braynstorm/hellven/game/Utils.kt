package braynstorm.hellven.game

import braynstorm.hellven.game.entity.EntityClass
import braynstorm.hellven.game.resource.Health
import braynstorm.hellven.game.resource.Mana
import braynstorm.hellven.game.resource.Rage
import com.badlogic.gdx.math.MathUtils
import java.util.EnumMap

typealias ResourceMap = Map<Class<*>, ResourcePool>

sealed class Utils {
	inline fun <reified K : Enum<K>, V> enumMap(map: Map<String, V>): Map<K, V> {
		return EnumMap<K, V>(K::class.java).apply {
			map.forEach { k, v ->
				enumValueOf<K>(k) to v
			}
		}
	}
	
	object FloatMath {
		val EPSILON = 0.01f
		
		fun epsilonEqual(num1: Float, num2: Int): Boolean {
			return num1.toInt() == num2
		}
		
		fun epsilonEqual(num1: Float, num2: Float): Boolean {
			return Math.abs(num1 - num2) < MathUtils.FLOAT_ROUNDING_ERROR
		}
		
		inline fun bounded(lowerBound: Float, value: Float, upperBound: Float): Float {
			return if (value < lowerBound) {
				lowerBound
			} else if (value > upperBound) {
				upperBound
			} else {
				value
			}
		}
		
		inline fun lowerBounded(lowerBound: Float, value: Float): Float {
			return if (value < lowerBound) {
				lowerBound
			} else {
				value
			}
		}
		
		inline fun upperBounded(value: Float, upperBound: Float): Float {
			return if (value > upperBound) {
				upperBound
			} else {
				value
			}
		}
	}
	
	object NewPlayer {
		/**
		 *  Returns a resource map for this class of player.
		 *  These values are for Level 1.
		 *  @return MutableMap<KClass, ResourcePool>
		 */
		@Suppress("UNCHECKED_CAST")
		fun getResources(playerClass: EntityClass) = (when (playerClass) {
			EntityClass.WARRIOR -> mutableMapOf(
					Health::class.java to Health(1f),
					Rage::class.java to Rage(1f)
			)
			EntityClass.MAGE    -> mutableMapOf(
					Health::class.java to Health(1f),
					Mana::class.java to Mana(1f)
			)
		/*EntityClass.WARLOCK -> mutableMapOf(
				Health::class.java to (Health(85f)),
				Mana::class.java to Mana(120f),
				LifeEssence::class.java to LifeEssence(100f)
		)
		EntityClass.ROGUE   -> mutableMapOf(
				Health::class.java to Health(90f)
		)
		EntityClass.PRIEST  -> mutableMapOf(
				Health::class.java to Health(80f),
				Willpower::class.java to Willpower(30f)
		)
		EntityClass.SHAMAN  -> mutableMapOf(
				Health::class.java to Health(100f),
				Mana::class.java to Mana(70f)
		)*/
			EntityClass.UNKNOWN -> throw IllegalArgumentException("Player class is UNKNOWN.")
			else                -> TODO("$playerClass not accounted for in Utils.NewPlayer::getResources(EntityClass). ")
		}) as ResourceMap
		
		fun getStats(playerClass: EntityClass): Attributes {
			return when (playerClass) {
				EntityClass.WARRIOR -> Attributes.fromEnumMap(listOf(
						Attribute.HEALTH to 100f,
						Attribute.RAGE to 100f,
						Attribute.STRENGTH to 1f
				))
				EntityClass.MAGE    -> Attributes.fromEnumMap(listOf(
						Attribute.HEALTH to 80f,
						Attribute.MANA to 120f,
						Attribute.INTELLECT to 1f,
						Attribute.MANA_PER_SEC to 2f
				))
			/*EntityClass.WARLOCK -> EntityStats(
					intellect = 10f,
					lifeEssence = 50f,
					weaponDamageMin = 1,
					weaponDamageMax = 1,
					healthPerSec = defaultHPS,
					manaPerSec = 15f
			)
			EntityClass.ROGUE   -> EntityStats(
					agility = 12f,
					intellect = 3f,
					weaponDamageMin = 1,
					weaponDamageMax = 1,
					healthPerSec = defaultHPS
			)
			EntityClass.PRIEST  -> EntityStats(
					intellect = 9f,
					willpower = 20f,
					weaponDamageMin = 1,
					weaponDamageMax = 1,
					healthPerSec = defaultHPS
			)
			EntityClass.SHAMAN  -> EntityStats(
					strength = 10f,
					intellect = 3f,
					agility = 5f,
					weaponDamageMin = 1,
					weaponDamageMax = 1,
					healthPerSec = defaultHPS
			)*/
				EntityClass.UNKNOWN -> throw IllegalArgumentException("Player class is UNKNOWN.")
				else                -> TODO("$playerClass not accounted for in Utils.NewPlayer::getResources(EntityClass). ")
			}
		}
	}
}
