package braynstorm.hellven.game.dataparsing

import braynstorm.hellven.game.*
import braynstorm.hellven.game.entity.EntityClass
import braynstorm.hellven.game.resource.Health
import braynstorm.hellven.game.resource.Mana
import braynstorm.hellven.game.resource.Rage
import com.badlogic.gdx.graphics.g2d.Sprite

class NPCDescription(
		var id: String,
		val ai: String,
		val texture: Sprite,
		val npcType: EntityType,
		val npcClass: EntityClass,
		val hostility: Map<String, Hostility>,
		val attributes: Attributes,
		val abilities: Map<String, Int>
//		TODO addItems. val loot: Array<Item>?
) {
	
	
	fun getResourceMap(): ResourceMap {
		return when (npcClass) {
			EntityClass.UNKNOWN -> hashMapOf<Class<*>, ResourcePool>(Health::class.java to Health(0f), Mana::class.java to Mana(0f), Rage::class.java to Rage(0f))
			EntityClass.WARRIOR -> hashMapOf<Class<*>, ResourcePool>(Health::class.java to Health(0f), Rage::class.java to Rage(0f))
			EntityClass.MAGE    -> hashMapOf<Class<*>, ResourcePool>(Health::class.java to Health(0f), Mana::class.java to Mana(0f))
		}
	}
}
