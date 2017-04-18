package braynstorm.hellven.game


interface GameObjectContainer : HasLocation {
	fun hold(obj: GameObject)
	fun release(obj: GameObject)
	fun silentRelease(obj: GameObject)
	
	val gameObject: GameObject?
	
	val hasGameObject: Boolean
		get() = gameObject != null
	
	
	fun onInteract(entity: Entity)
}

