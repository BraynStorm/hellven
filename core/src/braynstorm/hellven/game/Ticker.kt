package braynstorm.hellven.game

import com.badlogic.gdx.utils.Timer

/**
 * @param [interval] in seconds
 * Created by Braynstorm on 1.4.2017 г..
 */
class Ticker(val interval: Float = 1f, private val onTick: () -> Unit) {
	val schedule = Timer.schedule(object : Timer.Task() {
		override fun run() {
			onTick.invoke()
			passedTime += 1
		}
	}, interval, interval)!!
	
	fun stop() {
		schedule.cancel()
	}
	
	var passedTime: Long = 0L
	
}
