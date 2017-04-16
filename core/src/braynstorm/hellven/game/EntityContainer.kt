package braynstorm.hellven.game

import com.badlogic.gdx.math.Vector2

/**
 * Something that can contain an entity
 */
interface EntityContainer {
	
	/**
	 * Tells the container to 'hold' this entity.
	 * The container keeps a strong reference to the entity.
	 * It also notifies the Entity of the change.
	 */
	fun hold(entity: Entity)
	
	/**
	 * It also notifies the Entity of the change.
	 */
	fun release(entity: Entity)
	
	/**
	 * Does not trigger notify the entity. ([Entity.containerChanged] doesn't get called)
	 */
	fun releaseSilent(entity: Entity)
	
	val entity: Entity?
	
	val location: Vector2
	
	val hasEntity: Boolean
		get() = entity != null
	
}
