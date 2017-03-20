package braynstorm.rpg.gui

import org.lwjgl.glfw.GLFW

/**
 * TODO Add class description
 * Created by Braynstorm on 20.3.2017 г..
 */
object Timer {
	var value: Float = 0.0f
		private set
	
	private var oldValue = 0f
	private var newValue = 0f
	
	init {
		GLFW.glfwSetTime(0.0)
		tick()
		tick()
		// just so there are no huge spikes
	}
	
	fun tick() {
		oldValue = newValue
		newValue = GLFW.glfwGetTime().toFloat()
		value = newValue - oldValue
	}
	
}
