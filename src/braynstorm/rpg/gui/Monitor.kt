package braynstorm.rpg.gui

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWVidMode

/**
 * A monitor is a representation of a screen in memory.
 *
 * For all intents and purposes, we ignore [GLFWVidMode.redBits], [GLFWVidMode.greenBits] and [GLFWVidMode.blueBits].
 * as in 99.9% of the cases they will be equal to 8.
 */
class Monitor(val id: Long) {
	val name = GLFW.glfwGetMonitorName(id)!!
	
	val videoModes: Array<GLFWVidMode> = GLFW.glfwGetVideoModes(id).let { videoModes ->
		Array(videoModes.limit(), {
			videoModes[it]
		})
	}
	
	val maximumResolution = videoModes[videoModes.lastIndex]
	
	fun getClosestVideomodeSupported(width: Int, height: Int, refreshRate: Int): GLFWVidMode {
		videoModes.reversedArray().forEach {
			if (width >= it.width() && Window.height >= it.height() && refreshRate >= it.refreshRate())
				return it
		}
		
		throw RuntimeException("Couldn't find a fitting videomode for monitor $this. Was looking for: ${width}x$height, ${refreshRate}Hz")
	}
	
	override fun toString(): String {
		val sb = StringBuilder()
		
		sb.append("{Monitor=$name, resolutions=[")
		
		videoModes.forEach {
			sb.append("[${it.width()}x${it.height()}, ${it.refreshRate()}Hz],")
		}
		sb.deleteCharAt(sb.length - 1)
		sb.append("]}")
		
		return sb.toString()
	}
}
