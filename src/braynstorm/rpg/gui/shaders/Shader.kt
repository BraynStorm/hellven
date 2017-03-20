package braynstorm.rpg.gui.shaders

import braynstorm.logger.GlobalKogger
import org.lwjgl.opengl.GL20

/**
 * Created by Braynstorm on 28.2.2016 г..
 */
class Shader(sourceCode: String, type: ShaderType) {
	internal val shader: Int = GL20.glCreateShader(type.glShader)
	internal val type: ShaderType = ShaderType.getFromGL(type.glShader)
	
	init {
		GL20.glShaderSource(shader, sourceCode)
		GL20.glCompileShader(shader)
		
		GlobalKogger.logInfo("Shader Compilation Log " + GL20.glGetShaderInfoLog(shader))
	}
	
	fun finalize() {
		GL20.glDeleteShader(shader)
	}
}
