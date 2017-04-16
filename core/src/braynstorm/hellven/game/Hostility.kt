package braynstorm.hellven.game

import braynstorm.hellven.Localization

enum class Hostility {
	HOSTILE,
	NEUTRAL,
	FRIENDLY;
	
	val localized: CharSequence
		get() = Localization formatUI "hostility_${this.name}"
}
