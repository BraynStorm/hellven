package braynstorm.rpg.gui

import braynstorm.dirty.Dirty
import braynstorm.rpg.gui.shaders.ShaderProgram
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW

/**
 * A camera. Controls the view of the player.
 * Created by Braynstorm on 8.3.2016 г..
 */
class Camera(val position: Vector3f = Vector3f(0f, 0f, -1f),
             val forward: Vector3f = Vector3f(0f, 0f, 1f),
             val up: Vector3f = Vector3f(0f, 1f, 0f)) : Dirty {
	
	private val matrix = Matrix4f()
	private val matrixBuffer = BufferUtils.createFloatBuffer(16)
	
	private var dirty = true
	
	private var lastTime = GLFW.glfwGetTime().toLong()
	
	
	fun bindAndUpdate() {
		var currentTime = lastTime
		lastTime = GLFW.glfwGetTimerValue()
		currentTime = lastTime - currentTime
		
		
		
		println(GLFW.glfwGetTimerFrequency())
		
		ShaderProgram.current!!.setUniformMatrix4("view", matrixBuffer)
	}
	
	fun unbind() {
		lastTime = -1
	}
	
	override fun isDirty() = dirty
	
	override fun setDirty() {
		dirty = true
	}
	
	override fun cleanUp() {
		matrix.identity()
				.translate(position)
				.invertAffine() // TODO possible Bug
				.get(matrixBuffer)
		
	}
	
	
}
