package braynstorm.rpg.graphics

import braynstorm.rpg.graphics.shaders.ShaderProgram
import org.joml.Vector2i
import org.joml.Vector3f

/**
 * Describes a 2d polygon that can be drawn on the screen
 *
 * Created by Braynstorm on 21.3.2017 г..
 */
interface Drawable : Comparable<Drawable> {
	val width: Int
	val height: Int
	val z: Float
	
	val position: Vector2i
	
	fun draw()
	/**
	 * The color of the object in the "picking" phase.
	 * Must be unique for each object in the scene.
	 */
	var color: Vector3f
	
	fun pickDraw() {
		ShaderProgram.current!!.setUniformVector3("color", color)
		draw()
	}
	
	override fun compareTo(other: Drawable): Int {
		return z.compareTo(other.z)
	}
}
