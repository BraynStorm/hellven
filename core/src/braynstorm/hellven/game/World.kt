package braynstorm.hellven.game

import braynstorm.hellven.Hellven
import braynstorm.hellven.contains
import braynstorm.hellven.game.cells.AbstractWorldCell
import braynstorm.hellven.getOrNull
import braynstorm.hellven.gui.ScreenGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.ichipsea.kotlin.matrix.Matrix
import com.ichipsea.kotlin.matrix.forEachIndexed
import ktx.math.minus


/**
 * TODO Add class description
 * Created by Braynstorm on 1.4.2017 г..
 */
class World(worldLayout: WorldLayout, val gameScreen: ScreenGame) : Table(), GameWorld, PartialInputProcessor {
	override val player: PlayerEntity = PlayerEntity(Realm.PlayerInfo.playerClass!!, Realm.PlayerInfo.playerName!!, level = 1)
	
	// TODO separate the world into regions. and use these MutableCollections in them, rather than here.
	override val npcs: MutableSet<NPCEntity> = hashSetOf()
	override val cells: Matrix<AbstractWorldCell> = worldLayout.cells
	val spawnAreas: Set<SpawnArea>
	private val playerController = PlayerController()
	private val camera = gameScreen.worldStage.camera
	
	init {
		
		val pos = worldLayout.playerPosition
		
		cells.forEachIndexed { x, y, cell ->
			cell.location = Vector2(x.toFloat() * Hellven.cellSizeF, (cells.cols - y.toFloat() - 1f) * Hellven.cellSizeF)
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
			spawnEntity(it.location, it.entity)
		}
		
		if (spawnEntity(pos.x.toInt(), pos.y.toInt(), player)) {
			println("Spawning the player was successful!")
			gameScreen.playerFrame.entity = player
		}
		
		spawnAreas = worldLayout.spawnAreas
		spawnAreas.forEach {
			it.world = this
		}
		
		println("" + player.location + "  " + pos)
		pack()
		
		playerController.player = player
		playerController.world = this
	}
	
	
	// TODO these shouldnt be here
	private val tickerResource = Ticker(1.0F, { tickResource() }, true)
	private val tickerGameObject = Ticker(0.1F, { tickGameObject() }, true)
	private val tickerSpawnArea = Ticker(1F, { tickSpawnArea() }, true)
	private val tickerMobAI = Ticker(1F, { tickNPCAI() }, true)
	private val tickerAura = Ticker(0.1F, { tickAuras() }, true)
	private val tickerMovement = Ticker(0.05F, { tickMovement() }, true)
	
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
	
	override fun draw(batch: Batch?, parentAlpha: Float) {
		super.draw(batch, parentAlpha)
//
//		spawnAreas.forEach { it.cells.forEach {
//			it.draw(batch, parentAlpha)
//		} }
	}
	
	fun tickResource() {
		npcs.forEach(TickReceiverResource::tickResource)
		player.tickResource()
	}
	
	fun tickGameObject() {
		// TODO implement GameObject Ticks.
	}
	
	fun tickSpawnArea() {
		spawnAreas.forEach(SpawnArea::tick)
		
		//TODO implement FieldGroup ticks (cell groups / spawn areas)
	}
	
	fun tickNPCAI() {
		npcs.forEach(NPCEntity::tickNPCAI)
	}
	
	fun tickAuras() {
		player.tickAuras()
		npcs.forEach(TickReceiverAura::tickAuras)
	}
	
	fun tickMovement() {
		player.tickMove()
		npcs.forEach(NPCEntity::tickMove)
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
			npcs += entity
		
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


