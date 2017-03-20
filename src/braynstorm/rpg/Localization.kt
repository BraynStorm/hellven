package braynstorm.rpg

import java.io.FileInputStream
import java.util.HashMap
import java.util.Properties

/**
 *
 * Created by Braynstorm on 25.2.2017 г..
 */
object Localization {
	
	private val locales: MutableMap<String, Properties> = HashMap()
	
	lateinit var currentLocale: Properties
		private set
		get
	
	init {
		loadLocale("en_EN")
	}
	
	fun loadLocale(name: String): Properties {
		val loc = Properties()
		
		loc.load(FileInputStream("lang/$name.json"))
		locales.put("bg_BG", loc)
		
		return loc
	}
	
	fun switchToLocale(name: String) {
		currentLocale = locales[name] ?: loadLocale(name)
	}
	
	fun getLocalized(string: String): String {
		return currentLocale[string].toString()
	}
	
}
