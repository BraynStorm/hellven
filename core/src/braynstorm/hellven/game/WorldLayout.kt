package braynstorm.hellven.game

import braynstorm.hellven.PixmapColorException
import braynstorm.hellven.game.cells.AbstractWorldCell
import braynstorm.hellven.game.cells.WorldCellFactory
import com.badlogic.gdx.assets.loaders.PixmapLoader
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.ichipsea.kotlin.matrix.Matrix
import com.ichipsea.kotlin.matrix.createMatrix
import ktx.assets.loadOnDemand

class WorldLayout : Json.Serializable {
	enum class Variant {
		EMPTY,
		SOLID,
		NPC,
		PLAYER,
		SPAWNAREA,
		GAMEOBJECT,
		
	}
	
	var width = 0
		private set
	
	var height = 0
		private set
	
	var playerPosition: Vector2 = Vector2()
		private set
	
	lateinit var cells: Matrix<AbstractWorldCell>
	val entities = mutableSetOf<PendingEntity>()
	
	data class PendingEntity(val location: Vector2, val entity: Entity)
	
	override fun write(json: Json?) {
		TODO("write is not implemented")
	}
	
	/**
	 * Required fields:
	 * - pixelmap - string path to the map layout image.
	 * - data - contains the locations for every thing
	 */
	override fun read(json: Json, jsonData: JsonValue) {
		val pixmap = loadOnDemand(jsonData.getString("pixelmap"), PixmapLoader.PixmapParameter()).apply { finishLoading() }.asset
		val data = jsonData.get("mapping")
		
		val colorMap = hashMapOf <Int, Variant>()
		val dataMap = hashMapOf<Int, Any>()
		
		for (child in data.iterator()) {
			
			val type = Variant.valueOf(child.getString("type").toUpperCase())
			val color = Color.valueOf(child.name)
			val intColor = Color.rgb888(color)
			colorMap.put(intColor, type)
			
			// another map for settings to the objects
			when (type) {
				WorldLayout.Variant.NPC -> {
					dataMap.put(intColor, child.getString("id"))
				}
				Variant.SPAWNAREA       -> {
//					val spawnArea = SpawnArea()
//					dataMap.put(intColor, )
				}
				else                    -> {
				
				}
			}
		}
		
		width = pixmap.width
		height = pixmap.height
		
		
		cells = createMatrix(width, height, { x, y ->
			val pixel = pixmap.getPixel(x, y) ushr 8
			val t = colorMap[pixel] ?: throw PixmapColorException("Pixmap has unspecified color: #%06X, position: x=$x, y=$y", pixel)
			
			when (t) {
				WorldLayout.Variant.EMPTY      -> {
					WorldCellFactory.createPlainCell()
				}
				WorldLayout.Variant.SOLID      -> {
					WorldCellFactory.createSolidCell()
				}
				WorldLayout.Variant.NPC        -> {
					val entityID = dataMap[pixel] as String
					if (entityID.isNotEmpty()) {
						entities += PendingEntity(Vector2(x.toFloat(), y.toFloat()), NPCFactory.create(entityID))
						println("Adding")
					}
					WorldCellFactory.createPlainCell()
				}
				WorldLayout.Variant.PLAYER     -> {
					playerPosition = Vector2(x.toFloat(), y.toFloat())
					WorldCellFactory.createPlainCell()
				}
				WorldLayout.Variant.SPAWNAREA  -> {
					
					WorldCellFactory.createPlainCell()
				}
				WorldLayout.Variant.GAMEOBJECT -> {
					
					WorldCellFactory.createSolidCell()
				}
			}
			
		})
		
		println("reading")
		pixmap.dispose()
	}
	
	companion object {
		fun fromFile(file: FileHandle): WorldLayout {
			val json = Json()
			return json.fromJson(WorldLayout::class.java, file)
		}
		
	}
	
	
}