package braynstorm.rpg.gui.shaders

import braynstorm.rpg.gui.ShaderException
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL32
import org.lwjgl.opengl.GL40


/**
 * Current possible values:
 * VERTEX, FRAGMENT, GEOMETRY,
 * TESSELLATION_EVALUATION, TESSELLATION_CONTROL.
 *
 *
 * Created by Braynstorm on 28.2.2016 г..
 */
enum class ShaderType constructor(val glShader: Int) {
	VERTEX(GL20.GL_VERTEX_SHADER),
	FRAGMENT(GL20.GL_FRAGMENT_SHADER),
	GEOMETRY(GL32.GL_GEOMETRY_SHADER),
	TESSELLATION_EVALUATION(GL40.GL_TESS_EVALUATION_SHADER),
	TESSELLATION_CONTROL(GL40.GL_TESS_CONTROL_SHADER);
	
	
	companion object {
		fun getFromGL(glShader: Int): ShaderType {
			return when (glShader) {
				GL20.GL_VERTEX_SHADER          -> VERTEX
				GL20.GL_FRAGMENT_SHADER        -> FRAGMENT
				GL32.GL_GEOMETRY_SHADER        -> GEOMETRY
				GL40.GL_TESS_EVALUATION_SHADER -> TESSELLATION_EVALUATION
				GL40.GL_TESS_CONTROL_SHADER    -> TESSELLATION_CONTROL
				else                           -> {
					throw ShaderException("No known shader type for index $glShader")
				}
			}
		}
	}
}
