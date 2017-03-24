package braynstorm.rpg.graphics

import org.lwjgl.glfw.GLFW

/**
 * TODO Add class description
 * Created by Braynstorm on 16.1.2017 г..
 */
object Keyboard {
	
	val keys = BooleanArray(GLFW.GLFW_KEY_LAST + 1)
	
	
	fun isKeyDown(key: Int) = keys[key]
	
	fun setKeyState(key: Int, state: Boolean) {
		if (key != -1)
			keys[key] = state
	}
	
}
