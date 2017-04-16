package braynstorm.hellven.game

enum class Attribute(val default: Float = 0f) {
	STRENGTH(0f),
	INTELLECT(0f),
	WISDOM(0f),
	AGILITY(0f),
	
	STAMINA(0f),
	
	HEALTH(0f),
	RAGE(0f),
	MANA(0f),
	
	//	WILLPOWER,
	//	LIFEESSENCE,
	
	HEALTH_PER_SEC(0f),
	MANA_PER_SEC(0f),
	
	ARMOR(0f),
	
	WEAPON_DAMAGE_MIN(0f),
	WEAPON_DAMAGE_MAX(0f),
	
	/**
	 * Subtracted from moveTime
	 */
	MOVEMENT_SPEED(0f),
	
	/**
	 * Multiplier of castTime
	 */
	CAST_SPEED(1f),
	
	
	/**
	 * Subtracted from attackSpeed
	 */
	ATTACK_SPEED(0f),
	
	/**
	 * Bonus Magical damage
	 */
	MAGICAL_DAMAGE(0f),
	/**
	 * Bonus Physical damage.
	 */
	PHYSICAL_DAMAGE(0f)
	
}
