package braynstorm.hellven

import com.badlogic.gdx.assets.AssetDescriptor
import com.ichipsea.kotlin.matrix.Matrix
import ktx.assets.Assets

/**
 * TODO Add class description
 * Created by Braynstorm on 26.3.2017 г..
 */
inline operator fun <reified T> Assets.get(name: String): T = Assets.manager[name]

inline operator fun <reified T> Assets.get(descriptor: AssetDescriptor<T>): T = Assets.manager[descriptor]

inline fun <T> Matrix<T>.contains(x: Int, y: Int) = x < cols && y < rows && y >= 0 && x >= 0
inline fun <T> Matrix<T>.getOrNull(x: Int, y: Int): T? = if (contains(x, y)) this[x, y] else null



open class WorldGenerationException(message: String? = null) : Exception(message)
class PixmapColorException(message: String, color: Int) : WorldGenerationException(String.format(message, color))
