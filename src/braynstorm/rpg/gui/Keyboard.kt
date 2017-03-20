package braynstorm.rpg.gui

/**
 * TODO Add class description
 * Created by Braynstorm on 16.1.2017 г..
 */
object Keyboard {
	
	val keys = BooleanArray(500)
	
	
	fun isKeyDown(key: Int) = keys[key]
	
	fun setKeyState(key: Int, state: Boolean) {
		keys[key] = state
	}
	
}
