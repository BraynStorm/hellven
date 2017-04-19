package braynstorm.hellven.game

import braynstorm.hellven.game.api.PartialInputProcessor
import braynstorm.hellven.game.attributes.Direction
import braynstorm.hellven.game.entity.PlayerEntity
import braynstorm.hellven.game.scripting.Scripts
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

/**
 * TODO Add class description
 * Created by Braynstorm on 4.4.2017 г..
 */
class PlayerController : PartialInputProcessor {
	lateinit var player: PlayerEntity
	lateinit var world: World
	
	
	override fun keyDown(keycode: Int): Boolean {
		if (world.destroyed)
			return false
		
		when (keycode) {
			Input.Keys.W     -> {
				player.moving = true
				player.movementDirection = Direction.UP
			}
			Input.Keys.S     -> {
				player.moving = true
				player.movementDirection = Direction.DOWN
			}
			Input.Keys.A     -> {
				player.moving = true
				player.movementDirection = Direction.LEFT
			}
			Input.Keys.D     -> {
				player.moving = true
				player.movementDirection = Direction.RIGHT
			}
			Input.Keys.R     -> {
				// Reload all scripts
				Scripts.reloadAll()
			}
			
			Input.Keys.Q     -> {
				player.printItems()
			}
			
			Input.Keys.NUM_1 -> {

//				println(fireball.name)
//				println(fireball.description)
				player.abilityFireball.use()
			}
			Input.Keys.NUM_2 -> {

//				println(arcaneBlast.name)
//				println(arcaneBlast.description)
				player.abilityArcaneBlast.use()
			}
			Input.Keys.NUM_3 -> {
			}
			Input.Keys.NUM_4 -> {
				// damage
				val target = player.target
				if (target != null) {
					
				}
				println("Target: $target")
			}
			else             -> {
				return false
			}
		}
		return true
	}
	
	override fun keyUp(keycode: Int): Boolean {
		if (world.destroyed)
			return false
		if (!(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) ||
				Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D))) {
			player.moving = false
			println('s')
		}
		return false
	}
}
