package braynstorm.rpg.gui.textures;

import org.lwjgl.opengl.GL11;

public enum ColorType {
	RGB(GL11.GL_RGB, 3),
	RGBA(GL11.GL_RGBA, 4),
	GRAYSCALE(GL11.GL_LUMINANCE, 1),
	GRAYSCALE_ALPHA(GL11.GL_LUMINANCE_ALPHA, 2);
	
	int format;
	int size;
	
	ColorType(int format, int size) {
		this.format = format;
		this.size = size;
	}
	
	public int getInternalFormat() {
		return format;
	}
	
	public int getSize() {
		return size;
	}
}
