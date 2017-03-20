package braynstorm.rpg.gui.textures;

/**
 * Created by Braynstorm on 3.3.2016 г..
 */
public abstract class Texture {
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	public void bind() {
		bind(0);
	}
	
	public abstract void bind(int slot);
}
