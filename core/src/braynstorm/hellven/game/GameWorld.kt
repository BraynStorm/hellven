package braynstorm.hellven.game

import com.ichipsea.kotlin.matrix.Matrix

interface GameWorld {
	val player: PlayerEntity
	val npcs: MutableSet<NPCEntity>
	val cells: Matrix<WorldCell>
	
	
	fun entityTryMove(direction: Direction, entity: Entity): Boolean {
		println("trying")
		val container = entity.container ?: throw IllegalArgumentException("Entity has no container, so Direction.$direction is meaningless.")
		if (container is WorldCell)
			return container[direction]?.let { entityTryMoveTo(it, entity) } ?: return false
		return false
	}
	
	fun entityTryMoveTo(cell: WorldCell, entity: Entity): Boolean
	fun enttiyTryMoveTo(x: Int, y: Int, entity: Entity): Boolean = entityTryMoveTo(cells[x, y], entity)
	fun spawnEntity(x: Int, y: Int, entity: Entity): Boolean
	
}

