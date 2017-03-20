package braynstorm.rpg.gui

import braynstorm.dirty.Dirty
import braynstorm.rpg.gui.shaders.ShaderProgram
import braynstorm.rpg.gui.textures.Texture
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

/**
 * TODO Add class description
 * Created by Braynstorm on 20.3.2017 г..
 */

class Rectangle(val position: Vector3f, val width: Int = 16, val height: Int = 16, val texture: Texture) : Dirty {
	private var dirty = true
	
	
	init {
		// transform the position
	}
	
	override fun isDirty(): Boolean {
		return dirty
	}
	
	override fun setDirty() {
		dirty = true
	}
	
	override fun cleanUp() {
		matrix.identity()
		
		matrix.scale(
				width.toFloat() / Window.width,
				height.toFloat() / Window.height,
				1f
		)
		
		matrix.translate(position.x, position.y, position.y)
		matrix.identity()
		
		matrix.get(matrixBuffer)
	}
	
	fun draw(shaderProgram: ShaderProgram) {
		checkAndCleanUp()
//		texture.bind()
		shaderProgram.bind()
		shaderProgram.setUniformMatrix4("model", matrixBuffer)
//		shaderProgram.setUniformMatrix4("projection", matrixBuffer)
//		shaderProgram.setUniformMatrix4("view", matrixBuffer)
		GL30.glBindVertexArray(vao)
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0) // 6 indices, 2 triangles
		GL30.glBindVertexArray(0)
	}
	
	
	companion object {
		val vbo = GLUtils.createAndFillBuffer(GL15.GL_ARRAY_BUFFER,
				0f, 0f, 0f, /*0f, 0f,*/
				2f, 0f, 0f, /*1f, 0f,*/
				2f, 2f, 0f, /*1f, 1f,*/
				0f, 2f, 0f/*, 0f, 1f*/
		)
		
		val ibo = GLUtils.createAndFillBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,
				0, 1, 2, 0, 2, 3
		)
		
		val vao = GL30.glGenVertexArrays()
		
		val matrix = Matrix4f()
		val matrixBuffer = BufferUtils.createFloatBuffer(16)!!
		
		init {
			GL30.glBindVertexArray(vao)
			GL20.glEnableVertexAttribArray(0)
			GL20.glEnableVertexAttribArray(1)
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0)
//			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 20, 12)
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)
			
			GL30.glBindVertexArray(0)
//			GLUtils.unbindBuffer(GL15.GL_ARRAY_BUFFER)
//			GLUtils.unbindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER)
			
		}
		
		
	}
}
