package braynstorm.hellven.game.entity

/**
 * The class of the Entity.
 *
 * Useful for tooltips, but also, the player object uses this.
 *
 * Created by Braynstorm on 14.3.2017 г..
 */
enum class EntityClass(val resourceCount: Int) {
	UNKNOWN(0),
	
	WARRIOR(1),
	MAGE(1),
	
}
