package braynstorm.hellven.game

import braynstorm.hellven.game.cells.PlainWorldCell
import braynstorm.hellven.game.entity.EntityType
import braynstorm.hellven.game.entity.NPCFactory
import com.badlogic.gdx.math.MathUtils

data class SpawnArea(val description: SpawnAreaDescription) {
	private val cells = mutableListOf<PlainWorldCell>()
	
	lateinit var world: World
	
	operator fun plusAssign(cell: PlainWorldCell) {
		cells.add(cell)
	}
	
	fun tick() {
		val currentMobsSpawned = cells.filter { it.hasEntity && it.entity!!.entityType != EntityType.PLAYER }.count()
		val cellCount = cells.count() - 1
//		println("$id, Spawned: $currentMobsSpawned/${description.count}")
		if (currentMobsSpawned < description.count) {
			val cell = cells[MathUtils.random(0, cellCount)]
			world.spawnEntity(cell, NPCFactory.create(description.entityID, MathUtils.random(description.minlevel, description.maxlevel + 1)))
		}
		
	}
	
	
}
