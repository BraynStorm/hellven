package braynstorm.hellven.game

import braynstorm.hellven.PixmapColorException
import braynstorm.hellven.game.api.Entity
import braynstorm.hellven.game.api.GameObject
import braynstorm.hellven.game.api.GameObjectContainer
import braynstorm.hellven.game.cells.AbstractWorldCell
import braynstorm.hellven.game.cells.PlainWorldCell
import braynstorm.hellven.game.cells.WorldCellFactory
import braynstorm.hellven.game.dataparsing.GameObjectDescription
import braynstorm.hellven.game.dataparsing.RawGameObjectDescription
import braynstorm.hellven.game.entity.NPCFactory
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
	val gameObjects = mutableSetOf<PendingGameObject>()
	val spawnAreas = mutableSetOf<SpawnArea>()
	
	data class PendingEntity(val cell: PlainWorldCell, val entity: Entity)
	data class PendingGameObject(val cell: GameObjectContainer, val gameObject: GameObject)
	
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
		
		val colorMap = hashMapOf<Int, Variant>()
		val dataMap = hashMapOf<Int, Any>()
		
		for (child in data.iterator()) {
			
			val type = Variant.valueOf(child.getString("type").toUpperCase())
			val color = Color.valueOf(child.name)
			val intColor = Color.rgb888(color)
			colorMap.put(intColor, type)
			
			// another map for settings to the objects
			when (type) {
				Variant.NPC        -> {
					dataMap.put(intColor, child.getString("id"))
				}
				Variant.SPAWNAREA  -> {
					val spawnAreaDescription = SpawnAreaDescription.valueOf(child.get("settings") ?: throw ParseException("No settings field for spawn area"))
					val spawnArea = SpawnArea(spawnAreaDescription)
					spawnAreas += spawnArea
					dataMap.put(intColor, spawnArea)
				}
				Variant.GAMEOBJECT -> {
					val gameObject = RawGameObjectDescription.valueOf(child).parse()
					dataMap.put(intColor, gameObject)
					
				}
				else               -> {
				
				}
			}
		}
		
		width = pixmap.width
		height = pixmap.height
		
		
		cells = createMatrix(width, height, { x, y ->
			val pixel = pixmap.getPixel(x, y) ushr 8
			val t = colorMap[pixel] ?: throw PixmapColorException("Pixmap has unspecified color: #%06X, position: x=$x, y=$y, pixmap=${jsonData.getString("pixelmap")}", pixel)
			
			when (t) {
				Variant.EMPTY      -> {
					WorldCellFactory.createPlainCell()
				}
				Variant.SOLID      -> {
					WorldCellFactory.createSolidCell()
				}
				Variant.NPC        -> {
					val cell = WorldCellFactory.createPlainCell()
					val entityID = dataMap[pixel] as String
					if (entityID.isNotEmpty()) {
						entities += PendingEntity(cell, NPCFactory.create(entityID))
					}
					cell
				}
				Variant.PLAYER     -> {
					playerPosition = Vector2(x.toFloat(), y.toFloat())
					WorldCellFactory.createPlainCell()
				}
				Variant.SPAWNAREA  -> {
					val cell = WorldCellFactory.createPlainCell()
					val spawnArea = dataMap[pixel] as SpawnArea
					spawnArea += cell
					cell
				}
				Variant.GAMEOBJECT -> {
					// TODO
					val cell = WorldCellFactory.createGameObjectCell()
					val gameObject = (dataMap[pixel] as GameObjectDescription).createGameObject()
					gameObjects += PendingGameObject(cell, gameObject)
					cell
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
