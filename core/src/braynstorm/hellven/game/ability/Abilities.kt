package braynstorm.hellven.game.ability

import braynstorm.hellven.Hellven
import braynstorm.hellven.Localization
import braynstorm.hellven.game.*
import braynstorm.hellven.game.aura.AuraStack
import braynstorm.hellven.game.aura.Auras
import braynstorm.hellven.game.entity.EntityClass
import braynstorm.hellven.game.resource.Mana
import braynstorm.hellven.game.resource.Rage
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils

interface Ability {
	fun use(): Boolean
	val cooldownLeft: Long
	val icon: Sprite
	
	val name: String
	val description: String
}


abstract class AbilityFactory {
	abstract fun create(user: Entity, rank: Int): Ability
}

sealed class Abilities {
	companion object {
		internal val factories = hashMapOf(
				"fireball" to Fireball.factory,
				"mortal strike" to MortalStrike.factory,
				"dragon's breath" to DragonsBreath.factory,
				"rend" to Rend.factory,
				"arcane blast" to ArcaneBlast.factory
		)
		
		fun forName(name: String): AbilityFactory {
			return factories[name] ?: throw ParseException("No ability named: $name")
		}
	}
	
	class AutoAttack(val user: Entity) : Ability {
		private var cooldown = 0L
		private var lastUsed = 0L
		private var damage = 0f
		private val range = 2f
		
		override val icon = Hellven.gameSkin.getSprite("ability_autoattack")!!
		lateinit var ticker: Ticker
		
		companion object {
			fun getFactory(): AbilityFactory {
				return object : AbilityFactory() {
					override fun create(user: Entity, rank: Int): Ability {
						return AutoAttack(user)
					}
				}
			}
		}
		
		fun calculate() {
			ticker = (user.world as World).tickerNPCAI
			val lowDmg = user[Attribute.WEAPON_DAMAGE_MIN]
			val highDmg = user[Attribute.WEAPON_DAMAGE_MAX]
			val dmg = MathUtils.random(lowDmg, highDmg)
			cooldown = user[Attribute.ATTACK_SPEED].toLong()
			this.damage = dmg
		}
		
		override val cooldownLeft: Long
			get() = ticker.passedTime - (lastUsed + cooldown)
		
		override fun use(): Boolean {
			
			if (user.dead)
				return false
			
			val target = user.target ?: return false
			
			if (user.entityClass != EntityClass.WARRIOR)
				return false
			
			if (target.dead || user.getHostilityTowards(target) == Hostility.FRIENDLY)
				return false
			
			calculate()
			
			val now = ticker.passedTime
			
			if (lastUsed + cooldown > now) {
				return false
			}
			
			if (user.getHostilityTowards(target) == Hostility.FRIENDLY) {
				return false
			}
			
			if (user.cellLocation.dst2(target.cellLocation) > range) {
				return false
			}
			
			target.receiveDamage(Damage(user, Damage.Type.PHYSICAL, damage))
			lastUsed = now
			return true
		}
		
		override val name: String
			get() = Localization.formatAbility("auto_attack")
		override val description: String
			get() = Localization.formatAbility("auto_attack.description", user[Attribute.WEAPON_DAMAGE_MIN], user[Attribute.WEAPON_DAMAGE_MAX])
	}
	
	class Fireball(val user: Entity, val rank: Int) : Ability {
		override val icon: Sprite = Hellven.gameSkin.getSprite("ability_fireball")
		private val baseUpperImpactDamageBound = 15f
		private val baseLowerImpactDamageBound = 5f
		private val baseBurnAuraTotalDamage = 2f
		private val baseBurnAuraDuration = 5f
		
		private val baseManaCost = 10f
		
		var manaCost = 0f
		
		var upperImpactDamageBound = 0f
		var lowerImpactDamageBound = 0f
		var burnAuraDuration = 0f
		var burnAuraTotalDamage = 0f
		
		override val cooldownLeft: Long = 0L
		
		fun calculate() {
			val intellect = user[Attribute.INTELLECT]
			val rawDamageIncrease = intellect
			
			upperImpactDamageBound = (baseUpperImpactDamageBound + rank * 1.3f) + rawDamageIncrease
			lowerImpactDamageBound = (baseLowerImpactDamageBound + rank * 2f) + rawDamageIncrease
			burnAuraDuration = (baseBurnAuraDuration)
			burnAuraTotalDamage = (baseBurnAuraTotalDamage * rank * 0.5f) + intellect * 5f
			
			manaCost = baseManaCost + rank * 3f
		}
		
		
		override val name: String
			get() = Localization.formatAbility("fireball", rank)
		
		override val description: String get() {
			calculate()
			return Localization.formatAbility("fireball.description",
					lowerImpactDamageBound,
					upperImpactDamageBound,
					burnAuraTotalDamage,
					burnAuraDuration
			)
		}
		
		override fun use(): Boolean {
			if (user.dead)
				return false
			
			val target = user.target ?: return false
			val manaPool = user.resources[Mana::class.java] ?: return false
			
			if (target.dead || user.getHostilityTowards(target) == Hostility.FRIENDLY)
				return false
			
			calculate()
			
			if (user.getHostilityTowards(target) == Hostility.FRIENDLY) {
				return false
			}
			
			if (user.cellLocation.dst2(target.cellLocation) > 5) {
				return false
			}
			
			if (manaPool.canDrain(manaCost)) {
				manaPool.drain(manaCost, true)
			} else {
				return false
			}
			
			println(user)
			println(target)
			
			
			// TODO Really "hurl" the spell
			
			target.receiveDamage(Damage(user, Damage.Type.FIRE, MathUtils.random(lowerImpactDamageBound, upperImpactDamageBound + 1f)))
			
			if (!target.dead)
				target.applyAura(
						AuraStack(
								Auras.FireballDotAura(
										user,
										Damage(
												user,
												Damage.Type.FIRE,
												burnAuraTotalDamage / burnAuraDuration
										)
								),
								burnAuraDuration
						)
				)
			user.setInCastStress()
			return true
		}
		
		companion object {
			val factory =
					object : AbilityFactory() {
						override fun create(user: Entity, rank: Int): Ability {
							return Fireball(user, rank)
						}
					}
			
		}
	}
	
	class MortalStrike(val user: Entity, val rank: Int) : Ability {
		private var cooldown = 12L
		private var lastUsed = 0L
		lateinit var ticker: Ticker
		
		private val baseDamage = 10f
		private val baseRageCost = 30f
		private var lowerImpactDamageBound = 0f
		private var upperImpactDamageBound = 0f
		
		private fun calculate() {
			ticker = (user.world as World).tickerNPCAI
			val tmpDmg = baseDamage + user[Attribute.PHYSICAL_DAMAGE]
			lowerImpactDamageBound = user[Attribute.WEAPON_DAMAGE_MIN] + tmpDmg
			upperImpactDamageBound = user[Attribute.WEAPON_DAMAGE_MAX] + tmpDmg
		}
		
		override fun use(): Boolean {
			if (user.dead)
				return false
			
			val target = user.target ?: return false
			val ragePool = user.resources[Rage::class.java] ?: return false
			
			if (target.dead || user.getHostilityTowards(target) == Hostility.FRIENDLY)
				return false
			
			calculate()
			
			if (user.getHostilityTowards(target) == Hostility.FRIENDLY) {
				return false
			}
			
			if (user.cellLocation.dst2(target.cellLocation) > 5) {
				return false
			}
			
			if (ragePool.canDrain(baseRageCost)) {
				ragePool.drain(baseRageCost, true)
			} else {
				return false
			}
			
			target.receiveDamage(Damage(user, Damage.Type.PHYSICAL, MathUtils.random(lowerImpactDamageBound, upperImpactDamageBound)))
			
			user.setInCastStress()
			return true
		}
		
		override val cooldownLeft: Long
			get() = ticker.passedTime - (lastUsed + cooldown)
		
		override val icon: Sprite
			get() = TODO("not implemented")
		
		override val name: String
			get() = Localization.formatAbility("mortal_strike", rank)
		
		override val description: String
			get() {
				calculate()
				return Localization.formatAbility("mortal_strike.description", lowerImpactDamageBound, upperImpactDamageBound)
			}
		
		companion object {
			val factory = object : AbilityFactory() {
				override fun create(user: Entity, rank: Int): Ability {
					return MortalStrike(user, rank)
				}
			}
		}
	}
	
	class DragonsBreath(val user: Entity, val rank: Int) : Ability {
		override fun use(): Boolean {
			TODO("use is not implemented")
		}
		
		override val cooldownLeft: Long
			get() = TODO("not implemented")
		override val icon: Sprite
			get() = TODO("not implemented")
		// TODO
		
		companion object {
			val factory = object : AbilityFactory() {
				override fun create(user: Entity, rank: Int): Ability {
					return DragonsBreath(user, rank)
				}
			}
		}
		
		override val name: String
			get() = Localization.formatAbility("dragons_breath", rank)
		override val description: String
			get() = Localization.formatAbility("dragons_breath.description")
	}
	
	class Rend(val user: Entity, val rank: Int) : Ability {
		override fun use(): Boolean {
			return false
			TODO("use is not implemented")
		}
		
		override val cooldownLeft: Long
			get() = TODO("not implemented")
		override val icon: Sprite
			get() = TODO("not implemented")
		
		companion object {
			val factory = object : AbilityFactory() {
				override fun create(user: Entity, rank: Int): Ability {
					return Rend(user, rank)
				}
			}
		}
		
		override val name: String
			get() = Localization.formatAbility("rend", rank)
		override val description: String
			get() = Localization.formatAbility("rend.description")
	}
	
	class ArcaneBlast(val user: Entity, val rank: Int) : Ability {
		
		private val baseDamage = 30f
		private val baseManaCost = 30f
		private val castTime = 3f
		
		var damage = 0f
		var manaCost = 30f
		
		override val cooldownLeft: Long = 0L
		override val icon: Sprite
			get() = TODO("not implemented")
		
		fun calculate() {
			damage = user[Attribute.MAGICAL_DAMAGE] + baseDamage * rank
			manaCost = baseManaCost * rank - user[Attribute.WISDOM] * 3f
		}
		
		override fun use(): Boolean {
			if (user.dead)
				return false
			
			val target = user.target ?: return false
			val manaPool = user.resources[Mana::class.java] ?: return false
			
			if (target.dead || user.getHostilityTowards(target) == Hostility.FRIENDLY)
				return false
			
			calculate()
			
			if (user.getHostilityTowards(target) == Hostility.FRIENDLY) {
				return false
			}
			
			if (user.cellLocation.dst2(target.cellLocation) > 6) {
				return false
			}
			
			if (manaPool.canDrain(manaCost)) {
				manaPool.drain(manaCost, true)
			} else {
				return false
			}
			
			
			target.receiveDamage(Damage(user, Damage.Type.ARCANE, damage))
			user.setInCastStress()
			return true
		}
		
		override val name: String
			get() = Localization.formatAbility("arcane_blast", rank)
		override val description: String
			get() = Localization.formatAbility("arcane_blast.description", damage)
		
		companion object {
			val factory = object : AbilityFactory() {
				override fun create(user: Entity, rank: Int): Ability {
					return ArcaneBlast(user, rank)
				}
			}
		}
	}
}







