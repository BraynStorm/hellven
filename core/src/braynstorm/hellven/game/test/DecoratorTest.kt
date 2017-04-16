package braynstorm.hellven.game.test

import braynstorm.hellven.game.*
import braynstorm.hellven.game.entity.EntityClass

/**
 * TODO Add class description
 * Created by Braynstorm on 16.4.2017 г..
 */
interface Entity {
	val id: Int
	val type: EntityType
	val clazz: EntityClass
}

open class EmptyEntity(override val id: Int,
                       override val type: EntityType,
                       override val clazz: EntityClass) : Entity {
	constructor(entity: Entity) : this(entity.id, entity.type, entity.clazz)
}


open class MovingEntity(entity: Entity) : EmptyEntity(entity), EntityWithMovement {
	override fun tickMove() {
		TODO("tickMove is not implemented")
	}
	
}

open class ResourcefulEntity(entity: Entity) : EmptyEntity(entity), EntityWithResources {
	override fun tickResource() {
		TODO("tickResource is not implemented")
	}
	
}

open class InventoryEntity(entity: Entity, override val inventory: Inventory) : EmptyEntity(entity), EntityWithInventory {

}

open class AiEntity(entity: Entity, val ai: String) : EmptyEntity(entity), EntityWithAi {
	override fun tickNPCAI() {
		TODO("tickNPCAI is not implemented")
	}
	
}


interface EntityWithInventory : Entity, InventoryContainer
interface EntityWithMovement : Entity, TickReceiverMovement
interface EntityWithResources : Entity, TickReceiverResource
interface EntityWithAi : Entity, TickReceiverNPCAI

object EntityDecorator {
	fun addMovement(entity: Entity): EntityWithMovement {
		return MovingEntity(entity)
	}
	
	fun addResources(entity: Entity): EntityWithResources {
		return ResourcefulEntity(entity)
	}
	
	fun addInventory(inventory: Inventory, entity: Entity): EntityWithInventory {
		return InventoryEntity(entity, inventory)
	}
	
	fun addAi(aiScript: String, entity: Entity): EntityWithAi {
		return AiEntity(entity, aiScript)
	}
	
	fun makePlayer(name: String, clazz: EntityClass, inventory: Inventory): Entity {
		return addMovement(addInventory(inventory, addResources(EmptyEntity(-1, EntityType.PLAYER, clazz))))
	}
}


fun asdf() {
	
	val player = EntityDecorator.makePlayer("", EntityClass.WARRIOR, InventoryImpl())
	
	
	
}
