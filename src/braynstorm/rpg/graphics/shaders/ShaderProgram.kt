package braynstorm.rpg.graphics.shaders

import braynstorm.dirty.Dirty
import braynstorm.logger.GlobalKogger
import braynstorm.rpg.graphics.UniformNotFoundException
import braynstorm.rpg.graphics.shaders.ShaderType
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL20
import java.nio.FloatBuffer
import java.util.HashMap

/**
 * Created by Braynstorm on 28.2.2016 г..
 */
class ShaderProgram : Dirty {
	
	override fun cleanUp() {
		compileProgram()
		dirty = false
	}
	
	override fun isDirty() = dirty
	
	
	override fun setDirty() {
		dirty = true
	}
	
	private val currentShaders = HashMap<ShaderType, Shader>()
	private val uniforms = HashMap<String, Int>()
	private val attributes = HashMap<String, Int>()
	
	private var program: Int = 0
	
	private var dirty: Boolean = true
	
	fun compileProgram() {
		if (GL20.glIsProgram(program)) {
			GL20.glDeleteProgram(program)
		}
		
		program = GL20.glCreateProgram()
		
		currentShaders.forEach { _, shader -> GL20.glAttachShader(program, shader.shader) }
		attributes.forEach { name, index -> GL20.glBindAttribLocation(program, index, name) }
		
		GL20.glLinkProgram(program)
		GlobalKogger.logDebug("Program Link Log " + GL20.glGetProgramInfoLog(program))
		GL20.glValidateProgram(program)
		GlobalKogger.logDebug("Program Validation Log " + GL20.glGetProgramInfoLog(program))
		
		//GLUtil.checkGLError();
		
		
	}
	
	fun addShaders(replaceExisting: Boolean, vararg shaders: Shader): ShaderProgram {
		shaders
				.filterNot { !replaceExisting && currentShaders.containsValue(it) }
				.forEach { currentShaders.put(it.type, it) }
		
		setDirty()
		
		return this
	}
	
	fun removeShader(type: ShaderType): ShaderProgram {
		if (currentShaders.containsKey(type)) {
			currentShaders.remove(type)
			setDirty()
		}
		
		return this
	}
	
	fun addUniform(name: String): ShaderProgram {
		checkAndCleanUp()
		uniforms.put(name, GL20.glGetUniformLocation(program, name))
		return this
	}
	
	fun addAttribute(name: String, index: Int): ShaderProgram {
		attributes.put(name, index)
		setDirty()
		return this
	}
	
	fun setUniformFloat(name: String, value: Float): ShaderProgram {
		if (uniforms.containsKey(name)) {
			GL20.glUniform1f(uniforms[name] ?: throw  UniformNotFoundException(name), value)
		}
		return this
	}
	
	fun setUniformVector2(name: String, x: Float, y: Float): ShaderProgram {
		if (uniforms.containsKey(name)) {
			GL20.glUniform2f(uniforms[name] ?: throw  UniformNotFoundException(name), x, y)
		}
		return this
	}
	
	fun setUniformVector3(name: String, x: Float, y: Float, z: Float): ShaderProgram {
		if (uniforms.containsKey(name)) {
			GL20.glUniform3f(uniforms[name] ?: throw UniformNotFoundException(name), x, y, z)
		}
		return this
	}
	
	fun setUniformVector4(name: String, x: Float, y: Float, z: Float, w: Float): ShaderProgram {
		if (uniforms.containsKey(name)) {
			GL20.glUniform4f(uniforms[name] ?: throw UniformNotFoundException(name), x, y, z, w)
		}
		return this
	}
	
	fun setUniformVector2(name: String, vec: Vector2f): ShaderProgram {
		setUniformVector2(name, vec.x, vec.y)
		return this
	}
	
	fun setUniformVector3(name: String, vec: Vector3f): ShaderProgram {
		setUniformVector3(name, vec.x, vec.y, vec.z)
		return this
	}
	
	fun setUniformVector4(name: String, vec: Vector4f): ShaderProgram {
		setUniformVector4(name, vec.x, vec.y, vec.z, vec.w)
		return this
	}
	
	fun setUniformMatrix4(name: String, matrixData: FloatBuffer): ShaderProgram {
		if (uniforms.containsKey(name)) {
			GL20.glUniformMatrix4fv(uniforms[name] ?: throw UniformNotFoundException(name), false, matrixData)
		}
		return this
	}
	
	
	fun bind() {
		checkAndCleanUp()
		
		attributes.forEach { _, index ->
			GL20.glEnableVertexAttribArray(index)
		}
		
		if (GL20.glIsProgram(program)) {
			GL20.glUseProgram(program)
		}
		
		current = this
	}
	
	companion object {
		var current: ShaderProgram? = null
		
		fun unbind() {
			current?.attributes?.forEach { _, index -> GL20.glDisableVertexAttribArray(index) }
			GL20.glUseProgram(0)
			current = null
		}
		
		/*	/**
			 * Creates a ShaderProgram by looking inside res/shaders/ and finding files with extension .?s.glsl
			 * vs for vertex shader
			 * fs for fragment shader
			 * gs for geometry shader.
			 *
			 * @param baseName ex: default2d would look for default2d.vs.glsl; default2d.fs.glsl; etc.
			 * @return
			 */
			public static ShaderProgram create(String baseName) {
				List<FileUtils> files;
		
				try {
					files = Engine.files.getShaders();
				} catch (IOException e) {
					Engine.logger.logCritical(e);
					return new ShaderProgram();
				}
		
		
				final String vsName = baseName + ".vs.glsl";
				final String fsName = baseName + ".fs.glsl";
				final String gsName = baseName + ".gs.glsl";
				final String tesName = baseName + ".tes.glsl";
				final String tcsName = baseName + ".tcs.glsl";
		
				Shader vs = null, fs = null, gs = null, tes = null, tcs = null;
				try {
					for (FileUtils shaderFile : files) {
						if (shaderFile.getName(true).equals(vsName))
							vs = new Shader(shaderFile.readAllLines(true), GL20.GL_VERTEX_SHADER);
		
						if (shaderFile.getName(true).equals(fsName))
							fs = new Shader(shaderFile.readAllLines(true), GL20.GL_FRAGMENT_SHADER);
		
						if (shaderFile.getName(true).equals(gsName))
							gs = new Shader(shaderFile.readAllLines(true), GL32.GL_GEOMETRY_SHADER);
		
						if (shaderFile.getName(true).equals(tesName))
							tes = new Shader(shaderFile.readAllLines(true), GL40.GL_TESS_EVALUATION_SHADER);
		
						if (shaderFile.getName(true).equals(tcsName))
							tcs = new Shader(shaderFile.readAllLines(true), GL40.GL_TESS_CONTROL_SHADER);
					}
				} catch (IOException e) {
					Engine.logger.logWarning(e);
				}
		
				return create(vs, fs, gs, tes, tcs);
			}
		*/
		
		fun create(vararg shaders: Shader): ShaderProgram {
			var hasVS = false
			var hasFS = false
			
			for (shader in shaders) {
				if (shader.type == ShaderType.VERTEX) {
					hasVS = true
				} else if (shader.type == ShaderType.FRAGMENT) {
					hasFS = true
				}
			}
			
			if (hasFS && hasVS) {
				return ShaderProgram().addShaders(false, *shaders)
			} else {
				throw RuntimeException("No vertex or fragment shader.")
			}
			
		}
	}
	
}
