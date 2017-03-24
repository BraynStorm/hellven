package braynstorm.rpg.graphics

import braynstorm.rpg.game.fields.Field
import org.joml.Vector3f
import org.joml.Vector3i

/**
 *
 */
open class Scene(vararg objects: Drawable) {
	val sceneObjects = mutableListOf(*objects) // make it into a sorted collection
	
	operator fun plusAssign(drawable: Drawable) {
		add(drawable)
	}
	
	operator fun minusAssign(drawable: Drawable) {
		remove(drawable)
	}
	
	fun add(drawable: Drawable) {
		sceneObjects.add(drawable)
	}
	
	fun remove(drawable: Drawable) {
		sceneObjects.remove(drawable)
	}
	
	
	fun render() {
		sceneObjects.forEach(Drawable::draw)
	}
	
	fun pickRender() {
		currentColor = Vector3i(1, 0, 0)
		sceneObjects.forEach {
			it.color = getPickColor()
			it.pickDraw()
		}
	}
	
	fun findObject(color: Vector3f): Drawable? {
		return sceneObjects.find { it.color == color }
	}
	
	private var currentColor = Vector3i(1, 0, 0)
	private val oneOver255 = 1f / 255f
	
	private fun getPickColor(): Vector3f {
		currentColor.x++
		
		if (currentColor.x > 255) {
			currentColor.y++
			currentColor.x = 0
		}
		
		
		if (currentColor.y > 255) {
			currentColor.z++
			currentColor.y = 0
		}
		
		if (currentColor.z > 255) {
			throw RuntimeException("Too many objects in the scene maximum is 256^3 = ${Math.pow(256.0, 3.0).toInt()}")
		}
		
		return Vector3f(
				currentColor.x.toFloat() * oneOver255,
				currentColor.y.toFloat() * oneOver255,
				currentColor.z.toFloat() * oneOver255
		)
	}
	
	fun add(drawable: Array<Field>) {
		for (field in drawable) {
			add(field.rectangle)
		}
	}
	
	
}
