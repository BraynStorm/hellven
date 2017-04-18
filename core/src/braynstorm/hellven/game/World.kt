package braynstorm.hellven.game

import braynstorm.hellven.Hellven
import braynstorm.hellven.contains
import braynstorm.hellven.game.cells.AbstractWorldCell
import braynstorm.hellven.game.cells.GameObjectWorldCell
import braynstorm.hellven.getOrNull
import braynstorm.hellven.gui.ScreenGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ichipsea.kotlin.matrix.Matrix
import com.ichipsea.kotlin.matrix.forEachIndexed
import ktx.math.minus
import java.util.Collections


/**
 * TODO Add class description
 * Created by Braynstorm on 1.4.2017 г..
 */
class World(worldLayout: WorldLayout, val gameScreen: ScreenGame) : Table(), GameWorld, PartialInputProcessor {
	override val player: PlayerEntity = PlayerEntity(Realm.PlayerInfo.playerClass!!, Realm.PlayerInfo.playerName!!, level = 1)
	
	// TODO separate the world into regions. and use these MutableCollections in them, rather than here.
	override val entities: MutableSet<NPCEntity> = Collections.synchronizedSet(hashSetOf())
	override val cells: Matrix<AbstractWorldCell> = worldLayout.cells
	val spawnAreas: MutableSet<SpawnArea>
	private val playerController = PlayerController()
	private val camera = gameScreen.worldStage.camera
	
	var destroyed = false
	
	private var destroyAi = false
	private var destroyMovement = false
	private var destroyAuras = false
	private var destroyGameObjects = false
	private var destroyResources = false
	private var destroySpawnAreas = false
	
	private val tickerMovement = Ticker(0.05F, { tickMovement() })
	private val tickerAura = Ticker(0.10F, { tickAuras() })
	private val tickerGameObject = Ticker(0.10F, { tickGameObject() })
	val tickerNPCAI = Ticker(0.20F, { tickNPCAI() })
	private val tickerResource = Ticker(1.00F, { tickResource() })
	private val tickerSpawnArea = Ticker(1.00F, { tickSpawnArea() })
	
	init {
		val pos = worldLayout.playerPosition
		
		cells.forEachIndexed { x, y, cell ->
			cell.pixelLocation = Vector2(x.toFloat() * Hellven.cellSizeF, (cells.cols - y.toFloat() - 1f) * Hellven.cellSizeF)
			cell.cellLocation = Vector2(x.toFloat(), (cells.cols - y.toFloat() - 1f))
			cell.color = Color.PINK
			cell   [Direction.UP] = cells.getOrNull(x, y - 1)
			cell [Direction.DOWN] = cells.getOrNull(x, y + 1)
			cell [Direction.LEFT] = cells.getOrNull(x - 1, y)
			cell[Direction.RIGHT] = cells.getOrNull(x + 1, y)
			cell.world = this
			
			add(cell)
			
			if (x >= cells.cols - 1) {
				row()
			}
			
		}
		
		worldLayout.entities.forEach {
			spawnEntity(it.cell, it.entity)
		}
		
		if(player.dead){
			player.heal(player.health.capacity)
		}
		
		if (spawnEntity(pos.x.toInt(), pos.y.toInt(), player)) {
			println("Spawning the player was successful!")
			gameScreen.playerFrame.entity = player
		}
		
		worldLayout.gameObjects.forEach {
			spawnGameObject(it.cell as GameObjectWorldCell, it.gameObject)
		}
		
		spawnAreas = HashSet(worldLayout.spawnAreas)
		spawnAreas.forEach {
			it.world = this
		}
		
		
		
		println("" + player.pixelLocation + "  " + pos)
		pack()
		
		playerController.player = player
		playerController.world = this
	}
	
	fun destroy() {
		destroyed = true
		tickerMovement.stop()
		tickerNPCAI.stop()
		tickerGameObject.stop()
		tickerResource.stop()
		tickerAura.stop()
		tickerSpawnArea.stop()
		
		while (!(destroyMovement && destroyAi && destroyGameObjects && destroyResources && destroyAuras && destroySpawnAreas)) {
			println("Move $destroyMovement")
			println("Ai $destroyAi")
			println("GO $destroyGameObjects")
			println("Res $destroyResources")
			println("Aura $destroyAuras")
			println("SA $destroySpawnAreas")
		}
		
		entities.clear()
		spawnAreas.clear()
		
		player.containerChanged(null)
	}
	
	
	override fun reset() {
		Realm.switchWorld("world1")
	}
	
	override fun getPrefHeight(): Float {
		return Hellven.cellSizeF * cells.rows
	}
	
	override fun getPrefWidth(): Float {
		if (prefHeight <= 0f)
			return 0f
		return Hellven.cellSizeF * cells.cols
	}
	
	operator fun get(x: Int, y: Int): AbstractWorldCell {
		return cells[x, y]
	}
	
	override fun entityTryMoveTo(cell: WorldCell, entity: Entity): Boolean {
		if (cell !is EntityContainer) {
			return false
		}
		
		if (cell.hasEntity)
			return false
		
		cell.hold(entity)
		
		return cell.hasEntity
		
	}
	
	override fun entityTryMove(direction: Direction, entity: Entity): Boolean {
		val container = entity.container ?: throw IllegalArgumentException("Entity has no container, so Direction.$direction is meaningless.")
		if (container is WorldCell)
			return container[direction]?.let { entityTryMoveTo(it, entity) } ?: return false
		return false
	}
	
	fun spawnGameObject(cell: GameObjectWorldCell, gameObject: GameObject) {
		cell.hold(gameObject)
	}
	
	fun tickResource() {
		destroyResources = false
		entities.forEach(NPCEntity::tickResource)
		
		if (!destroyed)
			player.tickResource()
		destroyResources = true
	}
	
	fun tickGameObject() {
		destroyGameObjects = false
		// TODO implement GameObject Ticks.
		destroyGameObjects = true
	}
	
	fun tickSpawnArea() {
		destroySpawnAreas = false
		spawnAreas.forEach(SpawnArea::tick)
		destroySpawnAreas = true
	}
	
	fun tickNPCAI() {
		destroyAi = false
		entities.removeIf {
			it.tickNPCAI()
			// despawn dead ones
			if (it.dead) {
				it.container?.releaseSilent(it)
				if (player.target == it) {
					gameScreen.targetFrame.entity = null
					player.target = null
				}
				true
			} else {
				false
			}
		}
		destroyAi = true
		if (player.dead) {
			reset()
		}
	}
	
	fun tickAuras() {
		destroyAuras = false
		player.tickAuras()
		
		entities.forEach(NPCEntity::tickAuras)
		destroyAuras = true
	}
	
	fun tickMovement() {
		destroyMovement = false
		if (destroyed)
			return
		
		player.tickMove()
		entities.forEach(NPCEntity::tickMove)
		destroyMovement = true
	}
	
	/**
	 * Spawns an entity at the given location, and changing [EntityContainer].
	 *
	 * @return **True** if it was able to spawn the entity at the location, **False** otherwise
	 */
	override fun spawnEntity(x: Int, y: Int, entity: Entity): Boolean {
		val cell = cells[x, y] as? EntityContainer ?: return false
		println("spawning")
		return spawnEntity(cell, entity)
	}
	
	fun spawnEntity(location: Vector2, entity: Entity): Boolean = spawnEntity(location.x.toInt(), location.y.toInt(), entity)
	fun spawnEntity(cell: EntityContainer, entity: Entity): Boolean {
		if (cell.hasEntity)
			return false
		
		cell.hold(entity)
		
		if (entity is NPCEntity)
			entities += entity
		
		return cell.hasEntity
	}
	
	override fun keyDown(keycode: Int): Boolean {
		val step = Hellven.cellSizeF
		when (keycode) {
			Input.Keys.UP    -> stage.camera.position.y += step
			Input.Keys.DOWN  -> stage.camera.position.y -= step
			Input.Keys.LEFT  -> stage.camera.position.x -= step
			Input.Keys.RIGHT -> stage.camera.position.x += step
			else             -> {
				return playerController.keyDown(keycode)
			}
		}
		
		return true
	}
	
	override fun keyUp(keycode: Int): Boolean {
		return playerController.keyUp(keycode)
	}
	
	override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		if (button == Input.Buttons.LEFT) {
			validate()
			val realCoords = screenToLocalCoordinates(Vector2(screenX.toFloat(), screenY.toFloat()))
			val x = realCoords.x.toInt().let { if (it < 0) -Hellven.cellSizeI else it } / Hellven.cellSizeI
			val y = cells.cols - realCoords.y.toInt().let { if (it < 0) -Hellven.cellSizeI else it } / Hellven.cellSizeI - 1
			
			if (cells.contains(x, y)) {
				val cell = cells[x, y]
				val entity = (cell as? EntityContainer)?.entity
				gameScreen.targetFrame.entity = entity
				player.target = entity
				val gameObject = (cell as? GameObjectContainer)?.onInteract(player)
				println(entity)
				return true
			}
		} else if (button == Input.Buttons.RIGHT) {
			startDragCoords = screenToLocalCoordinates(Vector2(screenX.toFloat(), screenY.toFloat()))
		}
		return false
	}
	
	private var startDragCoords = Vector2()
	
	
	override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			val newCameraPos = startDragCoords.cpy() - screenToLocalCoordinates(Vector2(screenX.toFloat(), screenY.toFloat()))
			camera.position.add(newCameraPos.x, newCameraPos.y, 0f)
			return true
		}
		
		return false
	}
	
}


