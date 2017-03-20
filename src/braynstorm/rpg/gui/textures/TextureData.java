package braynstorm.rpg.gui.textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.nio.ByteBuffer;

/**
 * Created by Braynstorm on 10.3.2016 г..
 */
public class TextureData {
	private ByteBuffer         pixels;
	private TextureDescription description;
	
	public TextureData(ByteBuffer pixels, TextureDescription description) {
		this.pixels = pixels;
		this.description = description;
	}
	
	public TextureHandle getHandle() {
		int handle = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, handle);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,
				description.colorType.getInternalFormat(), description.width, description.height, 0,
				description.colorType.format, GL11.GL_UNSIGNED_BYTE, pixels);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		return new TextureHandle(handle, description, this);
	}
}
