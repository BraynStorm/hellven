package braynstorm.hellven

import java.util.Locale

/**
 * TODO Add class description
 * Created by Braynstorm on 3.4.2017 г..
 */
object Localization {
	private var locales: MutableMap<Locale, LocalizedBundle> = hashMapOf()
	
	private lateinit var currentLoc: LocalizedBundle
	
	fun loadLocale(properties: LocalizedBundle) {
		locales.put(properties.items.locale, properties)
		currentLoc = locales[properties.items.locale]!!
		
	}
	
	fun setCurrentLocale(locale: Locale) {
		if (locales.containsKey(locale)) {
			currentLoc = locales[locale]!!
		}
	}
	
	infix fun formatUI(key: String) = currentLoc.ui.format(key)!!
	infix fun formatNPC(key: String) = currentLoc.npcs.format(key)!!
	infix fun formatItem(key: String) = currentLoc.items.format(key)!!
	infix fun formatAbiliy(key: String) = currentLoc.abilities.format(key)!!
	
	fun formatUI(key: String, vararg args: Any) = currentLoc.ui.format(key, *args)!!
	fun formatNPC(key: String, vararg args: Number) = currentLoc.npcs.format(key, *args)!!
	fun formatItem(key: String, vararg args: Any) = currentLoc.items.format(key, *args)!!
	fun formatAbility(key: String, vararg args: Number): String = currentLoc.abilities.format(key, *args)!!
	
	
}

