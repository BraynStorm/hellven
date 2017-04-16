package braynstorm.hellven.gui

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.entity.EntityClass
import braynstorm.hellven.game.Realm
import com.badlogic.gdx.*
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import ktx.assets.loadOnDemand
import ktx.scene2d.KTableWidget
import ktx.scene2d.Scene2DSkin

/**
 * TODO Add class description
 * Created by Braynstorm on 26.3.2017 г..
 */
class ScreenMainMenu : ScreenAdapter(), InputProcessor {
	private val stage: Stage = Stage(ScreenViewport())
	private val table: KTableWidget = KTableWidget(Scene2DSkin.defaultSkin)
	
	private val startGameButton = TextButton("Start Game", Scene2DSkin.defaultSkin)
	private val exitGameButton = TextButton("Exit", Scene2DSkin.defaultSkin)
	private val nameField = TextField("", Scene2DSkin.defaultSkin)
	
	private val background = loadOnDemand("images/background.png", TextureLoader.TextureParameter())
	
	private val aboutText = "Created by Braynstorm\n(Bojidar Borislavov Stoyanov)\nTechnical University of Varna - 2017"
	
	init {
		
		startGameButton.onClick { event, actor ->
			event.handle()
			goToGameScreen()
			//TODO instead of creating it like this, load it from the save file.
			
		}
		exitGameButton.onClick { event, actor ->
			event.handle()
			Gdx.app.exit()
		}
		
		nameField.setAlignment(Align.center)
		
		table.setFillParent(true)
		table.center()
		table.add(KTableWidget(Scene2DSkin.defaultSkin).apply {
			center()
			background = TextureRegionDrawable(TextureRegion(this@ScreenMainMenu.background.asset))
			add(KTableWidget(Scene2DSkin.defaultSkin).apply {
				add(Label("Name: ", Scene2DSkin.defaultSkin, "mainscreen")).left().width(150f)
				add(nameField).width(250f).height(45f).grow()
			}).colspan(2)
			row().spaceTop(40f)
			add(startGameButton).width(200f)
			add(exitGameButton).width(200f)
		}).width(1024f).height(512f)
		table.row()
		table.add(Label(aboutText, Scene2DSkin.defaultSkin, "about").also { it.setAlignment(Align.center) }).colspan(2).bottom()

//		table.debugAll()
		stage.addActor(table)
		
		Gdx.input.inputProcessor = InputMultiplexer(this, stage)
	}
	
	fun goToGameScreen() {
		Realm.PlayerInfo.playerName = nameField.text
		Realm.PlayerInfo.playerClass = EntityClass.MAGE
		Hellven.screen = ScreenGame()
	}
	
	
	override fun resize(width: Int, height: Int) {
		stage.viewport.update(width, height, true)
//		entityFramesTable.setSize(width.toFloat(), height.toFloat())
//		entityFramesTable.defaults().expand()
	}
	
	override fun render(delta: Float) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		stage.act(delta)
		stage.draw()
	}
	
	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return false
	}
	
	override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
		return false
	}
	
	override fun keyTyped(character: Char): Boolean {
		return false
	}
	
	override fun scrolled(amount: Int): Boolean {
		return false
	}
	
	override fun keyUp(keycode: Int): Boolean {
		when (keycode) {
			Input.Keys.ESCAPE -> Gdx.app.exit()
			Input.Keys.ENTER  -> goToGameScreen()
			else              -> return false
		}
		return true
	}
	
	override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
		return false
	}
	
	override fun keyDown(keycode: Int): Boolean {
		return false
	}
	
	override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return false
	}
	
}
