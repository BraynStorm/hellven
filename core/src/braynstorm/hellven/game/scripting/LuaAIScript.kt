package braynstorm.hellven.game.scripting

import braynstorm.hellven.game.NPCEntity
import org.luaj.vm2.LuaError
import org.luaj.vm2.LuaFunction
import org.luaj.vm2.lib.jse.CoerceJavaToLua
import javax.script.Bindings

class LuaAIScript(var id: String, script: Bindings) : AIScript {
	
	private val tickFunction = script.get("tick") as LuaFunction
	
	override fun tick(entity: NPCEntity) {
		try {
			tickFunction.invoke(CoerceJavaToLua.coerce(entity))
		} catch(e: LuaError) {
			e.printStackTrace()
		}
	}
	
}

