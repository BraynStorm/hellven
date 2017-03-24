package braynstorm.rpg.graphics.shaders

import braynstorm.rpg.graphics.shaders.ShaderType
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Braynstorm on 16.5.2016 г..
 */
object ShaderManager {
	
	private val shaders = ShaderType.values().map { Pair(it, HashMap<String, Shader>()) }.toMap()
	
	operator fun get(key: String, type: ShaderType): Shader? {
		return shaders[type]!![key]
	}
	
	operator fun get(name: String): Array<Shader> {
		return ShaderType.values()
				.map { shaderType -> shaders[shaderType] }
				.filter { map -> map!!.containsKey(name) }
				.mapTo(ArrayList<Shader>(6)) { map -> map!![name]!! }.toTypedArray()
	}
	
	operator fun set(name: String, shader: Shader) {
		shaders[shader.type]!!.put(name, shader)
	}
	
	fun empty() {
		shaders.values.forEach { map -> map.clear() }
	}
}



