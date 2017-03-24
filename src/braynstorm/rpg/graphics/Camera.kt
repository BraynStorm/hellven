package braynstorm.rpg.graphics

import braynstorm.dirty.Dirty
import braynstorm.rpg.graphics.shaders.ShaderProgram
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW

/**
 * A camera. Controls the view of the player.
 *
 * @param speed In pixels/sec
 * Created by Braynstorm on 8.3.2016 г..
 */
class Camera(val position: Vector3f = Vector3f(0f, 0f, 0f), var speed: Float = 100f) : Dirty {
	
	private val matrix = Matrix4f()
	private val matrixBuffer = BufferUtils.createFloatBuffer(16)
	
	private var dirty = true
	
	fun bind() {
		checkAndCleanUp()
		ShaderProgram.current!!.setUniformMatrix4("view", matrixBuffer)
	}
	
	fun update() {
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
			position.y = move(position.y, 1f)
			setDirty()
		}
		
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A)) {
			position.x = move(position.x, -1f)
			setDirty()
		}
		
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
			position.y = move(position.y, -1f)
			setDirty()
		}
		
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D)) {
			position.x = move(position.x, 1f)
			setDirty()
		}
	}
	
	fun bindAndUpdate() {
		update()
		bind()
	}
	
	private inline fun move(currentVal: Float, direction: Float) = direction * speed * (Timer.value) + currentVal
	
	override fun isDirty() = dirty
	
	override fun setDirty() {
		dirty = true
	}
	
	override fun cleanUp() {
		matrix.setTranslation(position)
				.invert() // TODO possible Bug, try affine again tho
		
		matrix.get(matrixBuffer)
		
	}
	
	
}
