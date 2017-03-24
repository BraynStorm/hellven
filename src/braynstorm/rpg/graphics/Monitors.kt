package braynstorm.rpg.graphics

import org.lwjgl.glfw.GLFW
import java.util.ArrayList

/**
 * Contains all monitors
 * Created by Braynstorm on 26.1.2017 г..
 */
object Monitors {
	private val list = ArrayList<Monitor>(1)
	
	lateinit var primaryMonitor: Monitor
		private set
	
	init {
		refresh()
	}
	
	fun refresh() {
		val raw = GLFW.glfwGetMonitors() ?: throw RuntimeException("No monitors found on this system.")
		
		list.clear()
		
		val prim = GLFW.glfwGetPrimaryMonitor()
		
		while (raw.hasRemaining()) {
			val id = raw.get()
			
			list.add(Monitor(id))
			
			if (id == prim)
				primaryMonitor = list.last()
			
		}
	}
	
	operator fun get(index: Int): Monitor {
		return list[index]
	}
	
	fun forEach(function: (Monitor) -> Unit) {
		list.forEach(function)
	}
	
	/**
	 * Tries to find a Monitor with the supplied name.
	 * If it fails, returns the [primaryMonitor]
	 */
	infix fun find(stringValue: String): Monitor {
		return list.find { it.name == stringValue } ?: primaryMonitor
	}
	
	
}

