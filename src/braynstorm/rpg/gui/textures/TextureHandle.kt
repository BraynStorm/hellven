package braynstorm.rpg.gui.textures

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13

/**
 * Represents the handle handle and the handle description.
 */
class TextureHandle(private val handle: Int,
                    private val textureDescription: TextureDescription,
                    private val dataReference: TextureData) {
	
	var isBound = false
		private set
	
	@JvmOverloads fun bind(slot: Int = 0) {
		if (slot <= 32 && slot >= 0) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, handle)
			isBound = true
		}
	}
	
	@Throws(Throwable::class)
	protected fun finalize() {
		GL11.glDeleteTextures(handle)
	}
	
	enum class Wrap constructor(var value: Int) {
		CLAMP(GL11.GL_CLAMP),
		REPEAT(GL11.GL_REPEAT)
	}
	
	enum class MipMapFilter constructor(var value: Int) {
		NEAREST(GL11.GL_NEAREST),
		LINEAR(GL11.GL_LINEAR)
	}
	
	fun setWrapS(setting: Wrap): TextureHandle {
		bind()
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, setting.value)
		unbind()
		return this
	}
	
	fun setWrapT(setting: Wrap): TextureHandle {
		bind()
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, setting.value)
		unbind()
		return this
	}
	
	fun setMipMapFilterMin(filter: MipMapFilter): TextureHandle {
		bind()
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter.value)
		unbind()
		return this
	}
	
	fun setMipMapFilterMag(filter: MipMapFilter): TextureHandle {
		bind()
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter.value)
		unbind()
		return this
	}
	
	val width: Int
		get() = textureDescription.width
	
	val height: Int
		get() = textureDescription.height
	
	companion object {
		fun unbind() {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
		}
	}
}
