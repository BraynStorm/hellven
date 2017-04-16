package braynstorm.hellven.game

interface ResourceObserver {
	fun currentChange(old: Float, new: Float) {
		anyChange()
	}
	
	fun capacityChange(old: Float, new: Float) {
		anyChange()
	}
	
	fun anyChange()
}
