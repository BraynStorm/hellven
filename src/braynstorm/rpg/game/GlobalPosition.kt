package braynstorm.rpg.game

import braynstorm.rpg.game.world.World

/**
 * Describes a position for ingame purpuses (for a visual position, @see [org.joml.Vector3f]
 * Created by Braynstorm on 25.2.2017 г..
 */
data class GlobalPosition(val map: World, val x: Int, val y: Int)
