package braynstorm.rpg.files

import braynstorm.logger.GlobalKogger
import braynstorm.rpg.graphics.ConfigException
import org.json.JSONObject
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.HashMap

object Config : AbstractConfig(Paths.get("res/config.json")) {
	override fun setDefaults() {
		/*
		 * Strings
		 */
		
		setStringValue("monitor", "")
		setStringValue("window_mode", "windowed")
		setStringValue("window_title", "Hellven [0.0.∞]")
		
		/*
		 * Integers
		 */
		setIntValue("window_width", 800)
		setIntValue("window_height", 600)
		setIntValue("window_refreshRate", 60)
		
		/*
		 * Floats
		 */
		
		setFloatValue("camera_fov", 90f)
		
		/*
		 * Longs
		 */
		
		
		/*
		 * Doubles
		 */
	}
	
}

abstract class AbstractConfig(val configFile: Path) {
	
	val strings: MutableMap<String, String> = HashMap()
	val ints: MutableMap<String, Int> = HashMap()
	val floats: MutableMap<String, Float> = HashMap()
	val longs: MutableMap<String, Long> = HashMap()
	val doubles: MutableMap<String, Double> = HashMap()
	
	init {
		init()
	}
	
	private fun init() {
		setDefaults()
		try {
			GlobalKogger.logInfo("Loading config...")
			
			val obj = FileUtils.readJSONObject(configFile)
			
			val strings = obj.getJSONObject("strings")
			val integers = obj.getJSONObject("integers")
			val floats = obj.getJSONObject("floats")
			val longs = obj.getJSONObject("longs")
			val doubles = obj.getJSONObject("doubles")
			
			// Strings - Load
			strings.keySet().parallelStream().forEach {
				this.strings.put(it, strings.getString(it))
			}
			
			// Integers - Load
			integers.keySet().parallelStream().forEach {
				ints.put(it, integers.getInt(it))
			}
			
			// Floats - Load
			floats.keySet().parallelStream().forEach {
				this.floats.put(it, floats.getDouble(it).toFloat())
			}
			
			// Longs - Load
			longs.keySet().parallelStream().forEach {
				this.longs.put(it, longs.getLong(it))
			}
			
			// Doubles - Load
			longs.keySet().parallelStream().forEach {
				this.doubles.put(it, doubles.getDouble(it))
			}
			
			GlobalKogger.logInfo("Config loaded successfully!")
		} catch (e: Exception) {
			// No config. W/e
			GlobalKogger.logInfo(ConfigException("Generating default config.", e))
			saveConfig()
		}
		
	}
	
	/**
	 * Fixes any missing values from the config by introducing the default ones.
	 */
	abstract fun setDefaults()
	
	fun saveConfig() {
		val data = JSONObject()
		data.put("strings", strings)
		data.put("integers", ints)
		data.put("floats", floats)
		data.put("longs", longs)
		data.put("doubles", doubles)
		
		try {
			FileUtils.writeJSON(configFile, data)
		} catch (e: IOException) {
			GlobalKogger.logWarning(Exception("Config file failed to save", e))
		}
	}
	
	/*
	 * Getters and setters
	 */
	fun getStringValue(name: String): String {
		return strings[name] ?: throwRuntimeExc("STRING", name)
	}
	
	fun getStringValue(name: String, def: String): String {
		return strings[name] ?: def
	}
	
	fun setStringValue(name: String, value: String) {
		strings.put(name, value)
	}
	
	fun getIntValue(name: String): Int {
		return ints[name] ?: throwRuntimeExc("INT", name)
	}
	
	fun setIntValue(name: String, value: Int) {
		ints.put(name, value)
	}
	
	fun getFloatValue(name: String): Float {
		return floats[name] ?: throwRuntimeExc("FLOAT", name)
	}
	
	fun setFloatValue(name: String, value: Float) {
		floats.put(name, value)
	}
	
	fun getLongValue(name: String): Long {
		return longs[name] ?: throwRuntimeExc("LONG", name)
	}
	
	fun setLongValue(name: String, value: Long) {
		longs.put(name, value)
	}
	
	fun getDoubleValue(name: String): Double {
		return doubles[name] ?: throwRuntimeExc("DOUBLE", name)
	}
	
	fun setDoubleValue(name: String, value: Double) {
		doubles.put(name, value)
	}
	
	private fun throwRuntimeExc(type: String, name: String): Nothing {
		throw RuntimeException("Config doesn't contain a mapping for '$name' ($type).")
	}
	
}
