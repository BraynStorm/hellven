package braynstorm.hellven.game

enum class Attribute(val default: Float = 0f) {
	STRENGTH(0f),
	INTELLECT(0f),
	WISDOM(0f),
	AGILITY(0f),
	
	STAMINA(0f),
	
	HEALTH(1f),
	RAGE(0f),
	MANA(0f),
	
	//	WILLPOWER,
	//	LIFEESSENCE,
	
	HEALTH_PER_SEC(1f),
	MANA_PER_SEC(0f),
	
	ARMOR(0f),
	
	WEAPON_DAMAGE_MIN(1f),
	WEAPON_DAMAGE_MAX(1f),
	
	/**
	 * Subtracted from moveTime
	 */
	MOVEMENT_SPEED(30f),
	
	/**
	 * Multiplier of castTime
	 */
	CAST_SPEED(1f),
	
	
	/**
	 * Subtracted from attackSpeed
	 */
	ATTACK_SPEED(1f),
	
	/**
	 * Bonus Magical damage
	 */
	MAGICAL_DAMAGE(0f),
	/**
	 * Bonus Physical damage.
	 */
	PHYSICAL_DAMAGE(0f)
	
}
