package braynstorm.hellven

import braynstorm.hellven.Hellven.Atlas.npc
import braynstorm.hellven.game.World
import braynstorm.hellven.game.WorldLayout
import braynstorm.hellven.game.dataparsing.NPCDescription
import braynstorm.hellven.game.dataparsing.NPCDescriptionLoader
import braynstorm.hellven.game.items.ItemDescriptionLoader
import braynstorm.hellven.gui.ScreenGame
import braynstorm.hellven.gui.ScreenMainMenu
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.MusicLoader
import com.badlogic.gdx.assets.loaders.SkinLoader
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.I18NBundle
import ktx.assets.Assets
import ktx.assets.loadOnDemand
import ktx.scene2d.Scene2DSkin
import java.util.Locale
import kotlin.concurrent.thread


object Hellven : Game() {
	
	lateinit var skin: Skin
	lateinit var gameSkin: Skin
	
	var cellSizeF: Float = 42f
	val cellSizeI: Int = Hellven.cellSizeF.toInt()
	
	val worlds: MutableMap<String, WorldLayout> = HashMap()
	
	var gameScreen: ScreenGame? = null
	
	fun getWorld(id: String): World? {
		return World(worlds[id] ?: return null, gameScreen!!)
	}
	
	override fun create() {
		val uiFile = Gdx.files.internal("locale/ui")
		val npcsFile = Gdx.files.internal("locale/npcs")
		val itemsFile = Gdx.files.internal("locale/items")
		val abilitiesFile = Gdx.files.internal("locale/abilities")
		val bgLoc = Locale("bg")
		val enLoc = Locale.ENGLISH
		Locale.setDefault(enLoc)
		
		Localization.loadLocale(LocalizedBundle(
				I18NBundle.createBundle(uiFile, enLoc),
				I18NBundle.createBundle(npcsFile, enLoc),
				I18NBundle.createBundle(itemsFile, enLoc),
				I18NBundle.createBundle(abilitiesFile, enLoc)
		))
		Localization.loadLocale(LocalizedBundle(
				I18NBundle.createBundle(uiFile, bgLoc),
				I18NBundle.createBundle(npcsFile, bgLoc),
				I18NBundle.createBundle(itemsFile, bgLoc),
				I18NBundle.createBundle(abilitiesFile, bgLoc)
		))
//		Localization.setCurrentLocale(Locale.getDefault())
		
		Localization.setCurrentLocale(bgLoc)
		
		
		Assets.manager.setLoader(NPCDescription::class.java, NPCDescriptionLoader())
		
		loadOnDemand("ui_skin.atlas", TextureAtlasLoader.TextureAtlasParameter(false))
		loadOnDemand("game_skin.atlas", TextureAtlasLoader.TextureAtlasParameter(false))
		skin = loadOnDemand("ui_skin.json", SkinLoader.SkinParameter()).asset
		gameSkin = loadOnDemand("game_skin.json", SkinLoader.SkinParameter()).asset
		
		npc = loadOnDemand("npc.atlas", TextureAtlasLoader.TextureAtlasParameter(false)).asset
		
		Scene2DSkin.defaultSkin = skin
		setScreen(ScreenMainMenu())
		
		// load all items
		ItemDescriptionLoader.load(Gdx.files.internal("items"))
		
		// Load worlds
		Gdx.files.internal("worlds/").list(".json").forEach {
			worlds += it.nameWithoutExtension() to WorldLayout.fromFile(it)
		}
		
		
		val themeSong = loadOnDemand("sounds/theme.mp3", MusicLoader.MusicParameter()).asset
		themeSong.volume = 0.1f
		thread(isDaemon = true, start = true) {
			themeSong.play()
		}
		
		themeSong.setOnCompletionListener(Music::play)
	}
	
	object Atlas {
		lateinit var npc: TextureAtlas
			internal set
		
		
	}
	
}
