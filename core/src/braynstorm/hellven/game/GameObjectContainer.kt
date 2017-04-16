package braynstorm.hellven.game


interface GameObjectContainer {
	fun hold(obj: GameObject)
	fun release(obj: GameObject)
	
	val gameObject: GameObject?
	
	val hasGameObject: Boolean
		get() = gameObject != null
}
