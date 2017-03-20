package braynstorm.rpg.gui

/**
 * TODO Add class description
 * Created by Braynstorm on 15.1.2017 г..
 */

open class ShaderException(string: String, cause: Throwable? = null) : Exception(string, cause)

open class ConfigException(string: String, cause: Throwable? = null) : Exception(string, cause)

class UniformNotFoundException(string: String) : ShaderException(string, null)
