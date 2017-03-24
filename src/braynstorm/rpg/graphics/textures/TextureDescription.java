package braynstorm.rpg.graphics.textures;

/**
 * Created by Braynstorm on 29.2.2016 г..
 */
public class TextureDescription {
	public int       width;
	public int       height;
	public ColorType colorType;
	
	public TextureDescription(int width, int height, ColorType colorType) {
		this.width = width;
		this.height = height;
		this.colorType = colorType;
	}
}
