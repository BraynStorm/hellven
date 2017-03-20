package braynstorm.rpg

import braynstorm.logger.GlobalKogger
import braynstorm.rpg.files.Config
import braynstorm.rpg.files.ResourceManager
import braynstorm.rpg.gui.*
import braynstorm.rpg.gui.shaders.ShaderManager
import braynstorm.rpg.gui.shaders.ShaderProgram
import braynstorm.rpg.gui.shaders.ShaderType
import braynstorm.rpg.gui.textures.TextureManager
import javazoom.jl.player.Player
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URL
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.spi.AudioFileReader
import kotlin.concurrent.thread

/**
 *
 * Created by Braynstorm on 25.2.2017 г..
 */
object Launcher {
	
	@JvmStatic fun main(args: Array<String>) {
		
		
		var shouldStayOpen = true
		
		WindowCloseEvent on {
			shouldStayOpen = false
		}
		
		KeyUpEvent on {
			when (it.button) {
				GLFW.GLFW_KEY_F1 ->
					Window.setWindowed(800, 600)
				GLFW.GLFW_KEY_F2 ->
					Window.setBorderless()
				GLFW.GLFW_KEY_F3 ->
					Window.setFullscreen()
			}
			
		}
		
		Window
		ResourceManager.loadAllShaders()
		TextureManager.addFileHandle("empty_field", ResourceManager.getTexturePath("empty.png"))
		
		val rect = Rectangle(Vector3f(0f, 0f, 0f), texture = TextureManager.getSingleTexture("empty_field"))
		
		val program = ShaderProgram()
		program.addShaders(true, ShaderManager["shader2d", ShaderType.FRAGMENT]!!, ShaderManager["shader2d", ShaderType.VERTEX]!!)
		program.addAttribute("meshVertex", 0)
		program.addAttribute("meshTexcoord", 1)
		
		program.addUniform("model")
		program.addUniform("view")
		program.addUniform("projection")
		program.bind()
		
		val pickingProgram = ShaderProgram()
		pickingProgram.addShaders(true, ShaderManager["shader2d", ShaderType.VERTEX]!!, ShaderManager["picking", ShaderType.FRAGMENT]!!)
		
		
		val player = Player(ResourceManager.getSound("theme.mp3"))
		thread(start = true, isDaemon = true) {
			println("playing")
			player.play()
		}
		
		val cam = Camera()
		
		
		while (shouldStayOpen) {
			Window.frameStart()
			Timer.tick()
			program.bind()
			Window.useOrthoProjection()
			cam.bindAndUpdate()
			rect.draw()
			Window.frameEnd()
		}
		
		Window.destroy()
		Config.saveConfig()
		GlobalKogger.destroy()
	}
}


class MP3Encoding : AudioFormat.Encoding("mp3")
class MP3FileReader : AudioFileReader() {
	override fun getAudioInputStream(stream: InputStream): AudioInputStream {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	override fun getAudioInputStream(url: URL): AudioInputStream {
		val stream = url.openStream()
		stream.use { stream ->
			return getAudioInputStream(stream)
		}
	}
	
	override fun getAudioInputStream(file: File): AudioInputStream {
		val stream = FileInputStream(file)
		stream.use { stream ->
			return getAudioInputStream(stream)
		}
	}
	
	override fun getAudioFileFormat(stream: InputStream?): AudioFileFormat {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	override fun getAudioFileFormat(url: URL): AudioFileFormat {
		val stream = url.openStream()
		stream.use { stream ->
			return getAudioFileFormat(stream)
		}
	}
	
	override fun getAudioFileFormat(file: File): AudioFileFormat {
		val stream = FileInputStream(file)
		stream.use { stream ->
			return getAudioFileFormat(stream)
		}
	}
	
}
