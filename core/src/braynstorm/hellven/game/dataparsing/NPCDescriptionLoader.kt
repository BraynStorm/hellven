package braynstorm.hellven.game.dataparsing

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.attributes.Attributes
import braynstorm.hellven.game.entity.EntityType
import braynstorm.hellven.game.entity.Hostility
import braynstorm.hellven.game.entity.EntityClass
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.JsonReader

/**
 * A description of an NPC from a json file.
 */
internal class RawNPCDescription(val id: String,
                                 val ai: String,
                                 val texture: String,
                                 val npcType: String,
                                 val npcClass: String,
                                 val hostility: Map<String, String>,
                                 val attributes: Map<String, Float>,
                                 val abilities: Map<String, Int>
) {
	fun parse() = NPCDescription(
			id,
			ai,
			Hellven.Atlas.npc.createSprite(texture) ?: Hellven.Atlas.npc.createSprite("npc_w1_sullivan") /*throw RuntimeException("Texture not found $texture")*/,
			EntityType.valueOf(npcType.toUpperCase()),
			EntityClass.valueOf(npcClass.toUpperCase()),
			hostility.mapValues { Hostility.valueOf(it.value.toUpperCase()) },
			Attributes.valueOf(attributes),
			abilities
			//			null // TODO fix this, add items
	)
}


/**
 * Loads [NPCDescription]s.
 */
class NPCDescriptionLoader : SynchronousAssetLoader<NPCDescription, NPCDescriptionLoader.Parameter>(InternalFileHandleResolver()) {
	override fun getDependencies(fileName: String?, file: FileHandle?, parameter: Parameter?): Array<AssetDescriptor<Any>> {
		return Array()
	}
	
	override fun load(assetManager: AssetManager, fileName: String, file: FileHandle, parameter: Parameter?): NPCDescription {
		val jsonData = JsonReader().parse(file)
		
		println(fileName)
		val raw = RawNPCDescription(
				file.nameWithoutExtension(),
				jsonData.getString("ai"),
				jsonData.getString("texture"),
				jsonData.getString("npc_type"),
				jsonData.getString("npc_class"),
				let {
					val map = hashMapOf<String, String>()
					if (jsonData.has("hostility")) {
						jsonData.get("hostility").forEach {
							map += it.name to it.asString()
						}
					} else {
						map += "all" to "hostile"
					}
					map
				},
				let {
					val map = hashMapOf<String, Float>()
					if (jsonData.has("attributes")) {
						jsonData.get("attributes").forEach {
							map += it.name to it.asFloat()
						}
					}
					map
				},
				let{
					val map = hashMapOf<String, Int>()
					jsonData.get("abilities").forEach {
						map += it.name to it.asInt()
					}
					map
				}
		)
		
		return raw.parse()
	}
	
	
	class Parameter : AssetLoaderParameters <NPCDescription>()
	
}

