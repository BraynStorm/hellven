package braynstorm.hellven

import braynstorm.hellven.Hellven.Atlas.npc
import braynstorm.hellven.game.ItemDescriptionLoader
import braynstorm.hellven.game.dataparsing.NPCDescription
import braynstorm.hellven.game.dataparsing.NPCDescriptionLoader
import braynstorm.hellven.gui.ScreenMainMenu
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.I18NBundleLoader
import com.badlogic.gdx.assets.loaders.MusicLoader
import com.badlogic.gdx.assets.loaders.SkinLoader
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
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
	
	override fun create() {
		val ui = loadOnDemand("locale/ui", I18NBundleLoader.I18NBundleParameter()).asset
		val npcs = loadOnDemand("locale/npcs", I18NBundleLoader.I18NBundleParameter()).asset
		val items = loadOnDemand("locale/items", I18NBundleLoader.I18NBundleParameter()).asset
		val abilities = loadOnDemand("locale/abilities", I18NBundleLoader.I18NBundleParameter()).asset
		Localization.loadLocale(LocalizedBundle(ui, npcs, items, abilities))
		Localization.setCurrentLocale(Locale.getDefault())
		
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
		
		val themeSong = loadOnDemand("sounds/theme.mp3", MusicLoader.MusicParameter()).asset
		themeSong.volume = 0.2f
		thread(isDaemon = true, start = true) {
			themeSong.play()
		}
		
		themeSong.setOnCompletionListener(Music::play)

//		Resources.Sounds.themeSong.volume = 0.1f
//		Resources.Sounds.themeSong.play()
	}
	
	object Atlas {
		lateinit var npc: TextureAtlas
			internal set
		
		
	}
}
