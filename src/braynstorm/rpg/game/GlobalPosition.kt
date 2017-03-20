package braynstorm.rpg.game

/**
 * Describes a position for ingame purpuses (for a visual position, @see [org.joml.Vector3f]
 * Created by Braynstorm on 25.2.2017 г..
 */
data class GlobalPosition(val map: GameMap, val x: Int, val y: Int)
