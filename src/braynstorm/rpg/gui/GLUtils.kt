package braynstorm.rpg.gui

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15

import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * Created by Braynstorm on 28.2.2016 г..
 */
object GLUtils {
	/**
	 * Create a GL Buffer and fills it with the given data.
	 * @param target The target to witch the buffer will be attached. GL_ARRAY_BUFFER, GL_ELEMENT_ARRAY_BUFFER
	 * *
	 * @param data A ByteBuffer with the needed data.
	 * *
	 * @param usage GL_STATIC_DRAW.
	 * *
	 * @return The newly created buffer.
	 */
	@JvmOverloads fun createAndFillBuffer(target: Int, data: ByteBuffer, usage: Int = GL15.GL_STATIC_DRAW): Int {
		val buffer = GL15.glGenBuffers()
		GL15.glBindBuffer(target, buffer)
		GL15.glBufferData(target, data, usage)
		unbindBuffer(target)
		return buffer
	}
	
	/**
	 * Float version of [GLUtils.createAndFillBuffer]
	 * @param target GL_ARRAY_BUFFER, GL_ELEMENT_ARRAY_BUFFER
	 * *
	 * @param usage GL_STATIC_DRAW
	 */
	@JvmOverloads fun createAndFillBuffer(target: Int, data: IntBuffer, usage: Int = GL15.GL_STATIC_DRAW): Int {
		val buffer = GL15.glGenBuffers()
		GL15.glBindBuffer(target, buffer)
		GL15.glBufferData(target, data, usage)
		unbindBuffer(target)
		return buffer
	}
	
	/**
	 * Float version of [GLUtils.createAndFillBuffer]
	 * @param target GL_ARRAY_BUFFER, GL_ELEMENT_ARRAY_BUFFER
	 * *
	 * @param usage GL_STATIC_DRAW
	 */
	@JvmOverloads fun createAndFillBuffer(target: Int, data: FloatBuffer, usage: Int = GL15.GL_STATIC_DRAW): Int {
		val buffer = GL15.glGenBuffers()
		GL15.glBindBuffer(target, buffer)
		GL15.glBufferData(target, data, usage)
		unbindBuffer(target)
		return buffer
	}
	
	/**
	 * [GLUtils.createAndFillBuffer]
	 * with automatic buffer creation.
	 */
	fun createAndFillBuffer(target: Int, vararg data: Byte): Int {
		return createAndFillBuffer(target, byteBufferFromArray(*data))
	}
	
	/**
	 * [GLUtils.createAndFillBuffer]
	 * with automatic buffer creation.
	 */
	fun createAndFillBuffer(target: Int, vararg data: Int): Int {
		return createAndFillBuffer(target, intBufferFromArray(*data))
	}
	
	/**
	 * [GLUtils.createAndFillBuffer]
	 * with automatic buffer creation.
	 */
	fun createAndFillBuffer(target: Int, vararg data: Float): Int {
		return createAndFillBuffer(target, floatBufferFromArray(*data))
	}
	
	/**
	 * Creates and flips a new [FloatBuffer].
	 * @param data The data that the buffer will contain
	 * *
	 * @return The new [FloatBuffer].
	 */
	fun floatBufferFromArray(vararg data: Float): FloatBuffer {
		return BufferUtils.createFloatBuffer(data.size).put(data).flip() as FloatBuffer
	}
	
	/**
	 * Creates and flips a new [IntBuffer].
	 * @param data The data that the buffer will contain
	 * *
	 * @return The new [IntBuffer].
	 */
	fun intBufferFromArray(vararg data: Int): IntBuffer {
		return BufferUtils.createIntBuffer(data.size).put(data).flip() as IntBuffer
	}
	
	/**
	 * Creates and flips a new [ByteBuffer].
	 * @param data The data that the buffer will contain
	 * *
	 * @return The new [ByteBuffer].
	 */
	fun byteBufferFromArray(vararg data: Byte): ByteBuffer {
		return BufferUtils.createByteBuffer(data.size).put(data).flip() as ByteBuffer
	}
	
	fun unbindBuffer(target: Int) {
		GL15.glBindBuffer(target, 0)
	}
	
}
