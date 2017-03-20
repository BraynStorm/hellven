package braynstorm.rpg.gui.textures

import braynstorm.logger.GlobalKogger
import braynstorm.rpg.files.FileUtils
import braynstorm.rpg.gui.textures.SingleTexture
import org.json.JSONObject
import java.io.IOException
import java.nio.file.Path
import java.util.HashMap

/**
 * Storage class for all textures.
 * Get copies!
 * Created by Braynstorm on 9.3.2016 г..
 */
object TextureManager {
	
	private val allTextures = HashMap<String, TextureHandle>()
	private val stateTextures = HashMap<String, StateTexture.Description>()
	private val textureFileHandles = HashMap<String, Path>()
	
	
	fun addTexture(name: String, texture: TextureHandle) {
		allTextures.put(name, texture)
	}
	
	fun addFileHandle(name: String, fileUtils: Path) {
		textureFileHandles.put(name, fileUtils)
	}
	
	fun addStateTexture(jsonTexObj: JSONObject) {
		if (jsonTexObj.has("name") && jsonTexObj.has("textures"))
			stateTextures.put(
					jsonTexObj.getString("name"),
					StateTexture.Description(jsonTexObj.getJSONArray("textures"))
			)
	}
	
	fun getSingleTexture(name: String): SingleTexture {
		return SingleTexture(allTextures[name] ?: let {
			loadTexture(name)
			allTextures[name]!!
		})
	}
	
	fun getStateTexture(name: String): StateTexture {
		if (!stateTextures.containsKey(name)) {
			GlobalKogger.logWarning("No texture named '$name' is present. Falling back to transparent texture")
			return StateTexture(SingleTexture.Companion.TRANSPARENT.textureHandle)
		}
		
		val texture = StateTexture()
		val desc = stateTextures[name]!!
		
		desc.names.forEach { texName ->
			if (allTextures.containsKey(texName))
				texture.addTexture(allTextures[texName])
			else {
				loadTexture(texName)
				texture.addTexture(allTextures[texName])
			}
		}
		
		return texture
	}
	
	fun containsTexture(name: String): Boolean {
		return allTextures.containsKey(name) // TODO || textureFileHandles.containsKey(name);
	}
	
	fun canLoadTexture(name: String): Boolean {
		return textureFileHandles.containsKey(name)
	}
	
	/**
	 * Loads a texture into GPU Memory and RAM reading
	 * it from the FileUtils in the textureMap.
	 
	 * @param name The texture's name (usually the filename without the extension).
	 * *
	 * @throws IOException           If the texture cannot be read or accessed
	 * *
	 * @throws TextureParseException If the texture is of an unknown format or not a texture at all.
	 */
	@Throws(IOException::class, TextureParseException::class)
	fun loadTexture(name: String) {
		
		if (allTextures.containsKey(name)) {
			return
		}
		
		val path = textureFileHandles[name] ?: let {
			GlobalKogger.logWarning("No texture named '$name' is present in the texture map.")
			return
		}
		
		val textureData = FileUtils.readTextureData(path)
		
		addTexture(name, textureData.handle)
	}
}
