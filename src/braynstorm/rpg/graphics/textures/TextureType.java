package braynstorm.rpg.graphics.textures;

public enum TextureType {
	PNG("png"),
	JPEG("jpeg"),
	TIFF("tiff"),
	VEC4_ARRAY("vec4"),
	VEC3_ARRAY("vec3");
	
	String extension;
	
	TextureType(String extension) {
		this.extension = extension;
	}
	
	public String getExtension() {
		return extension;
	}
}
