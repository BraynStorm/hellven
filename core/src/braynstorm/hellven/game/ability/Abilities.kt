package braynstorm.hellven.game.ability

import braynstorm.hellven.Localization
import braynstorm.hellven.game.Attribute
import braynstorm.hellven.game.Entity
import braynstorm.hellven.game.Hostility
import braynstorm.hellven.game.aura.AuraStack
import braynstorm.hellven.game.aura.Auras
import braynstorm.hellven.game.resource.Mana
import com.badlogic.gdx.math.MathUtils


sealed class Abilities {
	
	class Fireball(val user: Entity, val rank: Int) {
		private val baseUpperImpactDamageBound = 10f
		private val baseLowerImpactDamageBound = 5f
		private val baseBurnAuraTotalDamage = 2f
		private val baseBurnAuraDuration = 5f
		
		private val baseManaCost = 10f
		
		var manaCost = 0f
		
		var upperImpactDamageBound = 0f
		var lowerImpactDamageBound = 0f
		var burnAuraDuration = 0f
		var burnAuraTotalDamage = 0f
		
		fun calculate() {
			val intellect = user[Attribute.INTELLECT]
			val rawDamageIncrease = intellect
			
			upperImpactDamageBound = (baseUpperImpactDamageBound + rank * 1.3f) + rawDamageIncrease
			lowerImpactDamageBound = (baseLowerImpactDamageBound + rank * 2f) + rawDamageIncrease
			burnAuraDuration = (baseBurnAuraDuration)
			burnAuraTotalDamage = (baseBurnAuraTotalDamage * rank * 0.5f) + intellect * 5f
			
			manaCost = baseManaCost + rank * 10f
		}
		
		val name: String get() = Localization.formatAbility("fireball", rank)
		val description: String get() {
			calculate()
			return Localization.formatAbility("fireball.description",
					lowerImpactDamageBound,
					upperImpactDamageBound,
					burnAuraTotalDamage,
					burnAuraDuration
			)
		}
		
		/**
		 * Casts the spell
		 */
		fun use(): Boolean {
			if (user.dead)
				return false
			
			val target = user.target ?: return false
			val manaPool = user.resources[Mana::class.java] ?: return false
			
			if (target.dead || user.getHostilityTowards(target) == Hostility.FRIENDLY)
				return false
			
			calculate()
			
			if (manaPool.canDrain(manaCost)) {
				manaPool.drain(manaCost, true)
			} else {
				return false
			}
			
			println(target)
			if (user.getHostilityTowards(target) == Hostility.FRIENDLY) {
				return false
			}
			
			// TODO range check
			
			// TODO Really "hurl" the spell
			
			target.receiveDamage(Damage(Damage.Type.FIRE, MathUtils.random(lowerImpactDamageBound, upperImpactDamageBound + 1f)))
			
			if (!target.dead)
				target.applyAura(
						AuraStack(
								Auras.FireballDotAura(
										user,
										Damage(
												Damage.Type.FIRE,
												burnAuraTotalDamage / burnAuraDuration
										)
								),
								burnAuraDuration
						)
				)
			
			return true
		}
	}
	
	object Frostbolt {
		// TODO
	}
	
	object Slam {
		// TODO
	}
	
	object ShamanisticRage {
		// TODO
	}
}







