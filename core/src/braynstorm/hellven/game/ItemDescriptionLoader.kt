package braynstorm.hellven.game

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.JsonReader

/**
 * Loads [Item] instances from json files or json-file-containing directories
 */
object ItemDescriptionLoader {
	fun load(file: FileHandle) {
		if (file.isDirectory) {
			file.list().forEach {
				loadSingleFile(it)
			}
		} else {
			loadSingleFile(file)
		}
	}
	
	private fun loadSingleFile(file: FileHandle) {
		val json = JsonReader().parse(file.reader())
		json.forEach {
			Items.load(it)
		}
	}
}
