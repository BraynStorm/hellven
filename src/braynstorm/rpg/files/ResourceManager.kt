package braynstorm.rpg.files

import braynstorm.logger.GlobalKogger
import braynstorm.rpg.graphics.shaders.Shader
import braynstorm.rpg.graphics.shaders.ShaderManager
import braynstorm.rpg.graphics.shaders.ShaderType
import braynstorm.rpg.graphics.textures.TextureData
import braynstorm.rpg.graphics.textures.TextureManager
import org.json.JSONException
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object ResourceManager {
	private val RESOURCE_PATH = Paths.get("res")
	private val MODEL_PATH = getPath("models")
	private val TEXTURE_PATH = getPath("textures")
	private val SHADERS_PATH = getPath("shaders")
	private val SOUNDS_PATH = getPath("sounds")
	private val LISTS_PATH = getPath("lists")
	
	fun getTexturePath(localPath: String): Path {
		return TEXTURE_PATH.resolve(localPath)
	}
	
	fun getPath(localPath: Path): Path {
		return RESOURCE_PATH.resolve(localPath)
	}
	
	fun getPath(localPath: String): Path {
		return RESOURCE_PATH.resolve(localPath)
	}
	
	fun getRawTexture(localPath: Path): TextureData? {
		TODO("getRawTexture(localPath = [$localPath])")
	}
	
	
	/**
	 * Utility method to load all the shaders in a given folder.
	 */
	fun loadAllShaders() {
		ShaderManager.empty()
		
		Files.newDirectoryStream(SHADERS_PATH).forEach { path ->
			val fileName = path.fileName.toString().toLowerCase()
			
			try {
				// Vertex
				var index: Int = fileName.lastIndexOf(".vs.glsl")
				if (index != -1) {
					ShaderManager[fileName.substring(0, index)] = Shader(FileUtils.readAllLines(path), ShaderType.VERTEX)
				}
				
				// Fragment
				index = fileName.lastIndexOf(".fs.glsl")
				
				if (index != -1) {
					ShaderManager[fileName.substring(0, index)] = Shader(FileUtils.readAllLines(path), ShaderType.FRAGMENT)
				}
				
				// Geometry
				index = fileName.lastIndexOf(".gs.glsl")
				
				if (index != -1) {
					ShaderManager[fileName.substring(0, index)] = Shader(FileUtils.readAllLines(path), ShaderType.GEOMETRY)
				}
				
				
				// Tessellation_Eval
				index = fileName.lastIndexOf(".tes.glsl")
				
				if (index != -1) {
					ShaderManager[fileName.substring(0, index)] = Shader(FileUtils.readAllLines(path), ShaderType.TESSELLATION_EVALUATION)
				}
				
				// Tessellation_Control
				index = fileName.lastIndexOf(".tcs.glsl")
				
				if (index != -1) {
					ShaderManager[fileName.substring(0, index)] = Shader(FileUtils.readAllLines(path), ShaderType.TESSELLATION_CONTROL)
					return
				}
				
			} catch (e: IOException) {
				GlobalKogger.logDebug(e)
			}
		}
	}
	
	
	/**
	 * Reads and loads all pathObj lists in the directory designated by [ResourceManager.LISTS_PATH].
	 *
	 * @throws IOException
	 */
	@Throws(IOException::class)
	fun loadAllLists() {
		Files.list(LISTS_PATH).parallel().forEach { path ->
			try {
				val json = FileUtils.readJSONArray(path)
				for (i in 0..json.length() - 1) {
					TextureManager.addStateTexture(json.getJSONObject(i))
				}
			} catch (e: IOException) {
				GlobalKogger.logWarning("Couldn't read StateTexture list '" + path.toAbsolutePath() + "'. Ignoring.")
				GlobalKogger.logWarning(e)
			} catch (e: JSONException) {
				GlobalKogger.logWarning("Couldn't parse StateTexture list '" + path.toAbsolutePath() + "'. Ignoring.")
				GlobalKogger.logWarning(e)
			}
		}
	}
	
	fun getSound(name: String): InputStream {
		return Files.newInputStream(SOUNDS_PATH.resolve(name))
	}
	
}
