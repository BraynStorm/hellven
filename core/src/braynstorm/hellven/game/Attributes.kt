package braynstorm.hellven.game

import java.util.EnumMap

/**
 * TODO Add class description
 * Created by Braynstorm on 14.4.2017 г..
 */

class Attributes(enumMap: EnumMap<Attribute, Float> = run {
	val map = EnumMap<Attribute, Float>(Attribute::class.java)
	Attribute.values().forEach {
		map.put(it, it.default)
	}
	map
}) {
	
	/**
	 * Contains the actual attributes.
	 */
	private val map: MutableMap<Attribute, Float> = enumMap
	
	operator fun get(attribute: Attribute): Float = map[attribute]!!
	operator fun set(attribute: Attribute, value: Float) {
		map[attribute] = value
	}
	
	companion object {
		fun valueOf(map: Map<String, Float>): Attributes {
			return Attributes().also {
				map.forEach { k, v -> it[Attribute.valueOf(k.toUpperCase())] = v }
			}
		}
		
		fun fromEnumMap(iterable: Iterable<Pair<Attribute, Float>>): Attributes {
			return Attributes().apply { map.putAll(iterable) }
		}
	}
	
	fun copy(): Attributes {
		return Attributes(EnumMap(map))
	}
	
}
