package braynstorm.hellven.game.api

interface ResourceObserver {
	fun currentChange(old: Float, new: Float) {
		anyChange()
	}
	
	fun capacityChange(old: Float, new: Float) {
		anyChange()
	}
	
	fun anyChange()
}
