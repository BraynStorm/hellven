package braynstorm.hellven.gui

import braynstorm.hellven.game.Realm
import braynstorm.hellven.game.World
import braynstorm.hellven.game.WorldLayout
import braynstorm.hellven.gui.elements.AbilityBar
import braynstorm.hellven.gui.elements.FrameEntity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.scene2d.Scene2DSkin

/**
 * TODO Add class description
 * Created by Braynstorm on 29.3.2017 г..
 */
class ScreenGame : ScreenAdapter() {
	
	lateinit var staticUiStage: Stage
	lateinit var worldStage: Stage
	lateinit var entityFramesTable: Table
	
	lateinit var playerFrame: FrameEntity
	lateinit var targetFrame: FrameEntity
	lateinit var abilityBar: AbilityBar
	
	override fun show() {
		super.show()
		
		staticUiStage = Stage(ScreenViewport())
		worldStage = Stage(ScreenViewport())

		playerFrame = FrameEntity()
		targetFrame = FrameEntity()
		
		Realm.world = World(WorldLayout.fromFile(Gdx.files.internal("worlds/world1.json")), this)
		
		
		entityFramesTable = Table(Scene2DSkin.defaultSkin)
		entityFramesTable.setFillParent(true)
		entityFramesTable.zIndex = 100
		
		entityFramesTable.top().left()
		entityFramesTable.add(playerFrame).width(200f).pad(15f, 15f, 0f, 0f)
		entityFramesTable.add(targetFrame).width(200f).pad(15f, 15f, 0f, 0f)
		
		
		
		worldStage.camera.position.x = -worldStage.width / 2f
		worldStage.camera.position.y = -worldStage.height / 2f
//		staticUiStage.setDebugAll(true)
		Gdx.input.inputProcessor = InputMultiplexer(staticUiStage, Realm.world)
		
		
		abilityBar = AbilityBar()
		abilityBar.setFillParent(true)
		staticUiStage.addActor(entityFramesTable)
		staticUiStage.addActor(abilityBar)
		worldStage.addActor(Realm.world)
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
	
}
