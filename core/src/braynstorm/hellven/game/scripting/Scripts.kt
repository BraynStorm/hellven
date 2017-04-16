package braynstorm.hellven.game.scripting

import braynstorm.hellven.game.Direction
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.CoerceJavaToLua
import javax.script.Bindings
import javax.script.Compilable
import javax.script.ScriptEngineManager

object Scripts {
	private val map: MutableMap<String, LuaAIScript> = hashMapOf()
	val ids get () = map.keys.toSet()
	
	
	private val g = hashMapOf(
			"UP" to Direction.UP,
			"DOWN" to Direction.DOWN,
			"LEFT" to Direction.LEFT,
			"RIGHT" to Direction.RIGHT
	)
	
	private fun loadScript(id: String, fileHandle: FileHandle) {
		map.put(id, LuaAIScript(id, Utils.Lua.compileProgram(fileHandle, g)))
	}
	
	private fun loadScript(id: String) {
		map.put(id, LuaAIScript(id, Utils.Lua.compileProgram(Gdx.files.internal("script/$id.lua"), g)))
	}
	
	operator fun get(id: String): LuaAIScript {
		return map[id] ?: let {
			loadScript(id, Gdx.files.internal("script/$id.lua"))
			map[id] ?: throw RuntimeException("Script not found: $id (also tried ./script/$id.lua)")
		}
	}
	
	object Utils {
		/**
		 * Global SEM for the whole thread.
		 */
		private val sem = ScriptEngineManager()
		
		/**
		 * Lua scripting utilities.
		 */
		object Lua {
			private val engine = sem.getEngineByExtension(".lua")!!
			private val compiler = engine as Compilable
			
			/**
			 * Reads the script from the file and compiles it using the default bindings and adding the [globals] to the table.
			 *
			 * @return Cast to [LuaValue]. Contains the whole script.
			 */
			fun compileProgram(file: FileHandle, globals: Map<String, Any>): Bindings {
				val reader = file.reader()
				val script = Lua.compiler.compile(reader)
				reader.close()
				
				val bindings = Lua.engine.createBindings()
				val table = LuaTable()
				
				globals.forEach {
					table[it.key] = CoerceJavaToLua.coerce(it.value)
				}
				
				bindings["G"] = table
				script.eval(bindings)
				
				return bindings
			}
		}
	}
	
	fun reloadAll() {
		val ids = map.keys.toSet()
		
		ids.forEach {
			loadScript(it)
		}
		
	}
}
