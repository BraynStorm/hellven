package braynstorm.rpg.graphics

import org.lwjgl.glfw.GLFW

/**
 * TODO Add class description
 * Created by Braynstorm on 16.1.2017 г..
 */
object Mouse {
	var buttons = BooleanArray(10)
	
	var x: Int = 0
	var y: Int = 0
	
	val STATE_BUTTON_0: Boolean get() = buttons[0]
	val STATE_BUTTON_1: Boolean get() = buttons[1]
	val STATE_BUTTON_2: Boolean get() = buttons[2]
	val STATE_BUTTON_3: Boolean get() = buttons[3]
	val STATE_BUTTON_4: Boolean get() = buttons[4]
	val STATE_BUTTON_5: Boolean get() = buttons[5]
	val STATE_BUTTON_6: Boolean get() = buttons[6]
	val STATE_BUTTON_7: Boolean get() = buttons[7]
	val STATE_BUTTON_8: Boolean get() = buttons[8]
	val STATE_BUTTON_9: Boolean get() = buttons[9]
	
	val STATEL_LMB: Boolean get() = buttons[GLFW.GLFW_MOUSE_BUTTON_LEFT]
	val STATEL_MMB: Boolean get() = buttons[GLFW.GLFW_MOUSE_BUTTON_RIGHT]
	val STATEL_RMB: Boolean get() = buttons[GLFW.GLFW_MOUSE_BUTTON_MIDDLE]
	
	inline fun setButtonState(button: Int, state: Boolean) {
		buttons[button] = state
	}
	
	infix inline fun press(button: Int) {
		setButtonState(button, true)
	}
	
	infix inline fun release(button: Int) {
		setButtonState(button, false)
	}
	
	fun resetMouse() {
		x = Window.width / 2
		y = Window.height / 2
	}
	
}
