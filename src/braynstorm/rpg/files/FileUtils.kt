package braynstorm.rpg.files

import braynstorm.rpg.graphics.textures.*
import org.json.JSONArray
import org.json.JSONObject
import org.lwjgl.BufferUtils
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

/**
 * Created by Braynstorm on 4.3.2016 г..
 */
object FileUtils {
	
	/**
	 * Returns the extension of the pathObj(if any).
	 * Example: C:\\john.doe.txt => txt
	 */
	fun getExtension(path: Path): String {
		val fullName = path.fileName.toString()
		val dotIndex = fullName.lastIndexOf('.')
		
		if (dotIndex == -1)
			return ""
		
		return fullName.substring(dotIndex)
	}
	
	@Throws(IOException::class)
	fun readAllLines(path: Path): String {
		val lines = Files.readAllLines(path, Charsets.UTF_8)
		val sb = StringBuilder()
		
		lines.forEach { line -> sb.append(line).append('\n') }
		
		return sb.toString()
	}
	
	@Throws(IOException::class)
	fun readJSONObject(path: Path): JSONObject {
		val lines = readAllLines(path)
		if (lines.length <= 2)
			return JSONObject()
		return JSONObject(lines)
	}
	
	@Throws(IOException::class)
	fun readJSONArray(path: Path): JSONArray {
		return JSONArray(readAllLines(path))
	}
	
	@Throws(IOException::class, TextureParseException::class)
	fun readTextureData(path: Path): TextureData {
		val image = ImageIO.read(Files.newInputStream(path)) ?: throw TextureParseException(path.toAbsolutePath().toString())
		
		val description = TextureDescription(image.width, image.height, ColorType.RGBA)
		val backwardsBuffer = BufferUtils.createByteBuffer(description.width * description.height * 4)
		
		var color: Int
		for (i in description.height - 1 downTo 0) {
			for (j in 0..description.width - 1) {
				color = image.getRGB(j, i)
				backwardsBuffer.put((color shr 16 and 0xFF).toByte())
				backwardsBuffer.put((color shr 8 and 0xFF).toByte())
				backwardsBuffer.put((color and 0xFF).toByte())
				backwardsBuffer.put((color shr 24 and 0xFF).toByte())
			}
		}
		
		backwardsBuffer.flip()
		
		return TextureData(backwardsBuffer, description)
	}
	
	@Throws(IOException::class)
	fun readAndLoadStateTextureList(path: Path) {
		val json = readJSONArray(path)
		var i = json.length()
		while (i-- > 0) {
			TextureManager.addStateTexture(json.getJSONObject(i))
		}
	}
	
	@Throws(IOException::class)
	fun writeBytes(path: Path, bytes: ByteArray) {
		Files.write(path, bytes)
	}
	
	@Deprecated("")
	@Throws(IOException::class)
	fun writeBytes(path: Path, bytes: Collection<Byte>) {
		// Sadly, it cannot be reduced to bytes.toArray(new byte[length])
		// because (byte instanceof Object) == false therefore (byte instanceof Byte) == false ...
		val os = Files.newOutputStream(path)
		
		for (b in bytes)
			os.write(b.toInt())
		
		os.close()
	}
	
	@Throws(IOException::class)
	fun writeString(path: Path, string: String) {
		Files.write(path, string.toByteArray(Charsets.UTF_8))
	}
	
	@Throws(IOException::class)
	fun writeJSON(path: Path, json: JSONArray) {
		val w = Files.newBufferedWriter(path, Charsets.UTF_8)
		json.write(w, 4, 1)
		w.close()
	}
	
	@Throws(IOException::class)
	fun writeJSON(path: Path, json: JSONObject) {
		val w = Files.newBufferedWriter(path, Charsets.UTF_8)
		json.write(w, 4, 1)
		w.close()
	}
}
