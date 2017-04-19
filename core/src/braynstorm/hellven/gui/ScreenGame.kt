package braynstorm.hellven.gui

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.World
import braynstorm.hellven.gui.elements.AbilityBar
import braynstorm.hellven.gui.elements.FrameEntity
import braynstorm.hellven.gui.elements.FrameInventory
import braynstorm.hellven.gui.elements.Tooltip
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport

/**
 * TODO Add class description
 * Created by Braynstorm on 29.3.2017 г..
 */
class ScreenGame : ScreenAdapter() {
	
	var staticUiStage: Stage
	var worldStage: Stage
	var entityFramesTable: Table
	
	var playerFrame: FrameEntity
	var targetFrame: FrameEntity
	var inventoryFrame: FrameInventory
	var abilityBar: AbilityBar
	var tooltip: Tooltip = Tooltip()
	
	init {
		Hellven.gameScreen = this
		
		staticUiStage = Stage(ScreenViewport())
		worldStage = Stage(ScreenViewport())
		
		playerFrame = FrameEntity()
		targetFrame = FrameEntity()
		inventoryFrame = FrameInventory()
		
		
		abilityBar = AbilityBar()
		entityFramesTable = Table(Hellven.skin)
	}
	
	override fun show() {
		super.show()
		
		entityFramesTable.setFillParent(true)
		entityFramesTable.zIndex = 100
		
		entityFramesTable.top().left()
		entityFramesTable.add(playerFrame).width(200f).pad(15f, 15f, 0f, 0f)
		entityFramesTable.add(targetFrame).width(200f).pad(15f, 15f, 0f, 0f)
		
		
		
		worldStage.camera.position.x = -worldStage.width / 2f
		worldStage.camera.position.y = -worldStage.height / 2f
//		staticUiStage.setDebugAll(true)
		
		
		abilityBar.setFillParent(true)
		staticUiStage.addActor(entityFramesTable)
		staticUiStage.addActor(abilityBar)
		staticUiStage.addActor(inventoryFrame)
		staticUiStage.addActor(tooltip)
		
		tooltip.showWithText("asdf Tooltip")
	}
	
	override fun render(delta: Float) {
		super.render(delta)
		Gdx.gl.glClearColor(213f / 255f, 213f / 255f, 213f / 255f, 1f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		worldStage.act(delta)
		worldStage.draw()
		staticUiStage.act(delta)
		staticUiStage.draw()
		
	}
	
	override fun resize(width: Int, height: Int) {
		super.resize(width, height)
		staticUiStage.viewport.update(width, height, true)
		worldStage.viewport.update(width, height, true)
	}
	
	fun setWorld(world: World) {
		worldStage.clear()
		worldStage.addActor(world)
		Gdx.input.inputProcessor = InputMultiplexer(staticUiStage, world)
		
	}
	
}
