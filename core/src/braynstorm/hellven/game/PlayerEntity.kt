package braynstorm.hellven.game

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.ability.Ability
import braynstorm.hellven.game.ability.FixedCostAbility
import braynstorm.hellven.game.ability.FreeAbility
import braynstorm.hellven.game.entity.EntityClass
import com.badlogic.gdx.graphics.g2d.Batch

class PlayerEntity(entityClass: EntityClass,
                   override val name: String,
                   resources: ResourceMap = Utils.NewPlayer.getResources(entityClass),
                   override var level: Int
) : AbstractMovingEntity(EntityType.PLAYER, entityClass, resources), TickReceiverMovement {
	var texture = Hellven.gameSkin.getSprite("player").apply { setSize(Hellven.cellSizeF, Hellven.cellSizeF) }
		private set
	
	
	override val baseAttributes: Attributes = Utils.NewPlayer.getStats(entityClass)
	override var calculatedAttributes: Attributes = Attributes()
	
	override fun draw(batch: Batch) {
		texture.setPosition(location.x, location.y)
		texture.draw(batch)
		super.draw(batch)
	}
	
	init {
		validateAttributes()
	}
	
	override fun isCapableOfUsing(ability: Ability): Boolean {
		if (ability is FreeAbility) {
			return true
		}
		
		return (ability as FixedCostAbility).userHasEnoughResource(this)
	}
	
	override fun canUseNow(ability: Ability): Boolean {
		TODO("canUseNow is not implemented")
	}
	
	override fun useAbility(ability: Ability): Boolean {
		TODO("useAbility is not implemented")
	}
	
	override fun getHostilityTowards(other: Entity): Hostility = when (other.entityType) {
		EntityType.PLAYER -> Hostility.FRIENDLY
		else              -> Hostility.NEUTRAL
	}
	
	override fun toString(): String {
		val res = StringBuilder()
		
		resources.forEach { k, v ->
			res.append(k)
			res.append('=')
			res.append(v.current)
			res.append('/')
			res.append(v.capacity)
		}
		
		
		
		
		return "Player[dead=$dead, combat=$inCombat class=$entityClass, resources=$res]"
	}
}

