package braynstorm.hellven.game.api

import com.badlogic.gdx.InputProcessor

interface PartialInputProcessor : InputProcessor {
	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return false
	}
	
	override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
		return false
	}
	
	override fun keyTyped(character: Char): Boolean {
		return false
	}
	
	override fun scrolled(amount: Int): Boolean {
		return false
	}
	
	override fun keyUp(keycode: Int): Boolean {
		return false
	}
	
	override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
		return false
	}
	
	override fun keyDown(keycode: Int): Boolean {
		return true
	}
	
	override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return false
	}
}
