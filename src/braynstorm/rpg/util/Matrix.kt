package braynstorm.rpg.util

/**
 * A 2D Array.
 * Created by Braynstorm on 14.3.2017 г..
 */
class Matrix<T>(val width: Int, val height: Int, init: (Int, Int) -> T) : Collection<T>, Iterable<T> {
	
	val data: MutableList<T> = ArrayList(width * height)
	
	init {
		data.addAll(0, (0 until width * height).map { i ->
			init(i % width, i / width)
		})
	}
	
	operator fun get(x: Int, y: Int): T {
		return data[x + y * width]
	}
	
	operator fun set(x: Int, y: Int, value: T) {
		data[x + y * width] = value
	}
	
	override val size: Int = width * height
	
	override fun contains(element: T): Boolean {
		return data.contains(element)
	}
	
	override fun containsAll(elements: Collection<T>): Boolean {
		return data.containsAll(elements)
	}
	
	override fun isEmpty(): Boolean = false
	
	override fun iterator(): Iterator<T> {
		return data.iterator()
	}
	
}
