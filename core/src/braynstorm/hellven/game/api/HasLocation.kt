package braynstorm.hellven.game.api

import com.badlogic.gdx.math.Vector2

interface HasLocation {
	val cellLocation: Vector2
	val pixelLocation: Vector2
}
