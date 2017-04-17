package braynstorm.hellven.game

import braynstorm.hellven.Hellven
import com.badlogic.gdx.utils.IntMap
import com.badlogic.gdx.utils.JsonValue

object Items {
	val items: IntMap<Item> = IntMap(100)
	
	operator fun get(id: Int): Item {
		return items[id] ?: throw IndexOutOfBoundsException("Index=$id, Size=${items.size}")
	}
	
	
	fun has(id: Int): Boolean = items.containsKey(id)
	
	fun load(json: JsonValue) {
		// @formatter:off
		val id          = json.get("id")        ?.asInt()       ?: throwException("id", "Any positive integer, but it has to be unique amongst all items")
		
		if(has(id)) {
			throw ParseException("ID ($id) is not unique, shared with item: ${this[id]}")
		}
		
		val rawUsage    = json.get("usage")     ?.asString()    ?: throwException("usage", ItemUsage.values())
		val rawQuality  = json.get("quality")   ?.asString()    ?: throwException("quality", ItemQuality.values())
		val rawIcon     = json.get("icon")      ?.asString()    ?: "item_$id"
		
		val usage   = enumValueOf<ItemUsage>(rawUsage.toUpperCase())
		val quality = enumValueOf<ItemQuality>(rawQuality.toUpperCase())
		val icon    = Hellven.gameSkin.getSprite("item_0") // TODO add the real item-icon here: rawIcon
		// @formatter:on
		
		when (usage) {
			ItemUsage.EQUIPMENT -> {
				val rawSlot = json.get("slot")?.asString() ?: throwException("slot", EquipmentSlotType.values())
				val rawAttributes = json.get("attributes") ?: throwException("attributes", Attribute.values())
				val rawAttrMap = hashMapOf<String, Float>()
				val slot = enumValueOf<EquipmentSlotType>(rawSlot.toUpperCase())
				
				rawAttributes.forEach {
					rawAttrMap[it.name] = it.asFloat()
				}
				
				val attributes = Attributes.valueOf(rawAttrMap)
				
				items.put(id, GenericEquipment(id, quality, icon, slot, attributes))
			}
			else                -> {
				items.put(id, GenericItem(id, usage, quality, icon))
			}
		}
		
		
	}
	
	private inline fun throwException(fieldName: String, validValues: Array<*>): Nothing {
		throwException(fieldName, validValues.joinToString())
	}
	
	private inline fun throwException(fieldName: String, validValues: String): Nothing {
		throw ParseException("Item description has no ${fieldName.toUpperCase()} field. Valid values for this field: $validValues.")
	}
}
