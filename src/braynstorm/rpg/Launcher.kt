package braynstorm.rpg

import braynstorm.logger.GlobalKogger
import braynstorm.rpg.files.Config
import braynstorm.rpg.files.ResourceManager
import braynstorm.rpg.game.GlobalPosition
import braynstorm.rpg.game.entity.EntityClass
import braynstorm.rpg.game.entity.Player
import braynstorm.rpg.game.gui.EntityRectangle
import braynstorm.rpg.game.mechanics.Health
import braynstorm.rpg.game.world.World
import braynstorm.rpg.graphics.Window
import braynstorm.rpg.graphics.WindowCloseEvent
import braynstorm.rpg.graphics.textures.TextureManager
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
		
		Window
		TextureManager.addFileHandle("empty_field", ResourceManager.getTexturePath("empty_field.png"))
		TextureManager.addFileHandle("go_lever_up", ResourceManager.getTexturePath("go_lever_up.png"))
		TextureManager.addFileHandle("go_lever_down", ResourceManager.getTexturePath("go_lever_down.png"))
		TextureManager.addFileHandle("player", ResourceManager.getTexturePath("player.png"))
		TextureManager.addFileHandle("monster", ResourceManager.getTexturePath("monster.png"))
		
		val mp3Player = javazoom.jl.player.Player(ResourceManager.getSound("theme.mp3"))
		thread(start = true, isDaemon = true) {
			println("playing")
			mp3Player.play()
		}
		
		/*val font = object : Drawable {
			override val width: Int = 200
			override val height: Int = 50
			override val position: Vector2i = Vector2i(100, 100)
			override var color: Vector3f = Vector3f(0f, 0f, 0f)
			
			private val vbo: Int
			private val ibo: Int = 0
			private val indexCount: Int
			private val matrixBuffer = Matrix4f().scale(10f).get(BufferUtils.createFloatBuffer(16))!!

//			val rect = Rectangle(position, texture = TextureManager.getSingleTexture("empty_field"))
			
			init {
				val buffer = BufferUtils.createByteBuffer(1000)
//				indexCount = STBEasyFont.stb_easy_font_print(0f, 0f, "TEXT", null, buffer)
				indexCount = 3
				buffer.putFloat(0f).putFloat(0f).putFloat(0f).putInt(-1)
				buffer.putFloat(10f).putFloat(0f).putFloat(0f).putInt(-1)
				buffer.putFloat(10f).putFloat(10f).putFloat(0f).putInt(-1)
				buffer.putFloat(0f).putFloat(10f).putFloat(0f).putInt(-1)
				buffer.flip()
				vbo = GLUtils.createAndFillBuffer(GL15.GL_ARRAY_BUFFER, buffer.asFloatBuffer())
				GlobalKogger.logInfo(GLUtils.dumpBuffer(buffer))
			}
			
			override fun draw() {
				Window.fontShader.bind()
				Window.useOrthoProjection()
				Window.camera.bind()
				ShaderProgram.current!!.setUniformMatrix4("model", matrixBuffer)
//				rect.draw()
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
				GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 16, 0)
				GL20.glVertexAttribPointer(1, 4, GL11.GL_UNSIGNED_BYTE, false, 16, 12)
				GL11.glDrawArrays(GL11.GL_QUADS, 0, indexCount)
			}
		}
		*/
		
		val map = World(1)
		val player = Player("Braynstorm", Health(100, 100), null, false, EntityClass.WARRIOR, GlobalPosition(map, 10, 10))
		
		Window.currentScene.add(map.fields)
		Window.currentScene.add(EntityRectangle(player))
		
		
		
		while (shouldStayOpen) {
			Window.frameStart()
			Window.drawScene()
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
