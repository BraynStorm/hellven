package braynstorm.hellven.game.aura

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.attributes.Attribute
import braynstorm.hellven.game.attributes.AttributeChange
import braynstorm.hellven.game.api.Entity
import braynstorm.hellven.game.ability.Damage
import com.badlogic.gdx.graphics.g2d.Sprite

/**
 * TODO Add class description
 * Created by Braynstorm on 13.4.2017 г..
 */
sealed class Auras {
	companion object {
		val ticksPerSec = 10
	}
	
	class FireballDotAura(val causedBy: Entity, val dps: Damage) : Aura(1) {
		override val icon: Sprite = Hellven.gameSkin.getSprite("ability_fireball")
		
		override fun getChange(attribute: Attribute): AttributeChange {
			return AttributeChange.NO_CHANGE
		}
		
		private var tickCount = 0
		
		override fun tick(receiver: Entity) {
			tickCount++
			
			if (tickCount != 0 && tickCount % ticksPerSec == 0) {
				receiver.receiveDamage(dps)
				causedBy.inCombat = true
				println("Ticking $dps $this")
			}
		}
		
		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (other?.javaClass != javaClass) return false
			
			other as FireballDotAura
			
			if (causedBy != other.causedBy) return false
			
			return true
		}
		
		override fun hashCode(): Int {
			return causedBy.hashCode()
		}
	}
}
