package braynstorm.rpg.graphics

import braynstorm.dirty.Dirty
import braynstorm.rpg.graphics.textures.Texture
import org.joml.Matrix4f
import org.joml.Vector2i
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

open class Rectangle(override val position: Vector2i,
                     override val width: Int = 16,
                     override val height: Int = 16,
                     override val z: Float = 0f,
                     val texture: Texture,
                     override var color: Vector3f = Vector3f(0f, 0f, 0f)) : Drawable, Dirty {
	
	protected var dirty = true
	
	override fun isDirty(): Boolean {
		return dirty
	}
	
	override fun setDirty() {
		dirty = true
	}
	
	override fun cleanUp() {
		matrix.identity()
		
		matrix.translate(position.x.toFloat(), position.y.toFloat(), z)
		matrix.scale(
				width.toFloat(),
				height.toFloat(),
				1f
		)
		
		
		matrix.get(matrixBuffer)
	}
	
	override fun draw() {
		checkAndCleanUp()
		texture.bind()
		Window.drawShader.bind()
		Window.useOrthoProjection()
		Window.camera.bind()
		Window.drawShader.setUniformMatrix4("model", matrixBuffer)
		GL30.glBindVertexArray(vao)
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0) // 6 indices, 2 triangles
		GL30.glBindVertexArray(0)
	}
	
	override fun pickDraw() {
		checkAndCleanUp()
		texture.bind()
		
		Window.pickingShader.bind()
		Window.useOrthoProjection()
		Window.camera.bind()
		Window.pickingShader.setUniformMatrix4("model", matrixBuffer)
		Window.pickingShader.setUniformVector3("color", color)
		GL30.glBindVertexArray(vao)
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0) // 6 indices, 2 triangles
		GL30.glBindVertexArray(0)
	}
	
	companion object {
		val vbo = GLUtils.createAndFillBuffer(GL15.GL_ARRAY_BUFFER,
				0f, 0f, 0f, 0f, 0f,
				1f, 0f, 0f, 1f, 0f,
				1f, 1f, 0f, 1f, 1f,
				0f, 1f, 0f, 0f, 1f
		)
		
		val ibo = GLUtils.createAndFillBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,
				0.toByte(), 1, 2, 0, 2, 3
		)
		
		val vao = GL30.glGenVertexArrays()
		
		val matrix = Matrix4f()
		val matrixBuffer = BufferUtils.createFloatBuffer(16)!!
		
		init {
			GL30.glBindVertexArray(vao)
			GL20.glEnableVertexAttribArray(0)
			GL20.glEnableVertexAttribArray(1)
			
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 20, 0)
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 20, 12)
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)
			
			GL30.glBindVertexArray(0)
			GLUtils.unbindBuffer(GL15.GL_ARRAY_BUFFER)
			GLUtils.unbindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER)
			GL20.glDisableVertexAttribArray(0)
			GL20.glDisableVertexAttribArray(1)
		}
		
		
	}
}
