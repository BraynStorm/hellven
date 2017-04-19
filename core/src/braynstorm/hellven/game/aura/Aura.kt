package braynstorm.hellven.game.aura

import braynstorm.hellven.game.attributes.Attribute
import braynstorm.hellven.game.attributes.AttributeChange
import braynstorm.hellven.game.api.Entity
import com.badlogic.gdx.graphics.g2d.Sprite

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
abstract class Aura(val id: Int) {
	abstract val icon:Sprite
	
	abstract fun tick(receiver: Entity)
	
	abstract fun getChange(attribute: Attribute): AttributeChange
}

