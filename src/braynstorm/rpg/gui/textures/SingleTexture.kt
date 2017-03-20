package braynstorm.rpg.gui.textures

import org.lwjgl.BufferUtils

/**
 * Created by Braynstorm on 3.3.2016 г..
 */
class SingleTexture : Texture {
	val textureHandle: TextureHandle
	
	constructor(textureHandle: TextureHandle) {
		this.textureHandle = textureHandle
	}
	
	constructor(other: SingleTexture) {
		this.textureHandle = other.textureHandle
	}
	
	override fun getWidth(): Int {
		return textureHandle.width
	}
	
	override fun getHeight(): Int {
		return textureHandle.height
	}
	
	override fun bind(slot: Int) {
		textureHandle.bind(slot)
	}
	
	companion object {
		/**
		 * Returns a 1x1 [SingleTexture] with it's pixel set to [Vector4f] (0F, 0F, 0F, 0F)
		 * Useful when you don't want to have a textureHandle on a element, only a background color.
		 * @return A 1x1 transparent textureHandle.
		 */
		val TRANSPARENT: SingleTexture
		
		init {
			val buffer = BufferUtils.createByteBuffer(4)
			buffer.putInt(0) // 0x00000000
			buffer.flip()
			TRANSPARENT = SingleTexture(TextureData(buffer, TextureDescription(1, 1, ColorType.RGBA)).handle)
		}
		
		
		val defaultTexture: SingleTexture
			get() = TextureManager.getSingleTexture("default")
	}
}
