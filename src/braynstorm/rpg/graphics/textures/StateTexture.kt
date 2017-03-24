package braynstorm.rpg.graphics.textures

import org.json.JSONArray
import java.util.ArrayList

/**
 * Created by Braynstorm on 3.3.2016 г..
 */
class StateTexture : Texture {
	
	private var textures: MutableList<TextureHandle>
	private var currentState: Int = 0
	
	internal constructor() {
		textures = ArrayList<TextureHandle>()
	}
	
	constructor(first: TextureHandle, vararg rest: TextureHandle) : this() {
		if (true)
			textures.add(first)
		
		rest.filterNotNull().forEach { textures.add(it) }
	}
	
	constructor(other: StateTexture) {
		textures = ArrayList(other.textures)
		currentState = other.currentState
	}
	
	fun addTexture(state: Int, textureHandle: TextureHandle?): StateTexture {
		if (textureHandle != null)
			textures.add(state, textureHandle)
		return this
	}
	
	fun addTexture(textureHandle: TextureHandle?): StateTexture {
		if (textureHandle != null)
			textures.add(textureHandle)
		return this
	}
	
	var state: Int
		get() = currentState
		set(state) = if (textures.size <= state)
			this.currentState = textures.size - 1
		else
			this.currentState = state
	
	override fun getWidth(): Int {
		if (textures.size == 0)
			return 0
		
		return textures[currentState].width
	}
	
	override fun getHeight(): Int {
		if (textures.size == 0)
			return 0
		
		return textures[currentState].height
	}
	
	override fun bind(slot: Int) {
		if (textures.size == 0 || textures.size <= currentState) {
			SingleTexture.TRANSPARENT.bind(slot)
			return
		}
		
		textures[currentState].bind(slot)
	}
	
	class Description(json: JSONArray) {
		val names: Array<String>
		
		init {
			if (json.contains(null)) {
				throw NullPointerException(
						"JSONArray provided for StateTexture.Description constructor contains a null!"
				)
			}
			names = Array<String>(json.length(), {
				json.getString(it)
			})
		}
		
	}
}
