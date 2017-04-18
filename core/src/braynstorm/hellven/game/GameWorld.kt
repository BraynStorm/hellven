package braynstorm.hellven.game

import com.ichipsea.kotlin.matrix.Matrix

interface GameWorld {
	val player: PlayerEntity
	val entities: MutableSet<NPCEntity>
	val cells: Matrix<WorldCell>
	
	fun entityTryMove(direction: Direction, entity: Entity): Boolean
	fun entityTryMoveTo(cell: WorldCell, entity: Entity): Boolean
	fun enttiyTryMoveTo(x: Int, y: Int, entity: Entity): Boolean = entityTryMoveTo(cells[x, y], entity)
	fun spawnEntity(x: Int, y: Int, entity: Entity): Boolean
	fun reset()
	
}

