package braynstorm.hellven.game

import braynstorm.hellven.Hellven
import braynstorm.hellven.game.ability.Ability
import braynstorm.hellven.game.dataparsing.NPCDescription
import braynstorm.hellven.get
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.Batch
import ktx.assets.Assets
import ktx.assets.load

/**
 * This object is used for the creation of any NPC.
 */
object NPCFactory {
	fun create(npcID: String, level:Int = 1): NPCEntity {
		val description = let {
			val fileName = "npc/$npcID.json"
			if (Assets.manager.isLoaded(fileName))
				Assets[fileName]
			else {
				load(AssetDescriptor(fileName, NPCDescription::class.java)).apply { finishLoading() }.asset
			}
		}
		
		return object : AbstractNPCEntity(
				description.id,
				description.ai,
				description.npcType,
				description.npcClass,
				description.getResourceMap()), TickReceiverMovement {
			override var level: Int = level
			override var calculatedAttributes: Attributes = Attributes()
			
			override var moving: Boolean = false
			override var movementDirection: Direction = Direction.UP
			
			// TODO add the attributes form the json
			override val baseAttributes: Attributes = description.attributes
			
			
			init {
				validateAttributes()
				dead = false
			}
			
			val texture = description.texture.apply { setSize(Hellven.cellSizeF, Hellven.cellSizeF) }
			
			override fun getHostilityTowards(other: Entity): Hostility {
				val hostilities = description.hostility
				
				if (other is PlayerEntity) {
					return hostilities["player"] ?: hostilities["all"]!!
				} else if (other is NPCEntity) {
					return hostilities[other.id] ?: hostilities["all"]!!
				} else {
					return hostilities["all"]!!
				}
			}
			
			override fun isCapableOfUsing(ability: Ability): Boolean {
				return false
			}
			
			override fun canUseNow(ability: Ability): Boolean {
				return false
			}
			
			override fun useAbility(ability: Ability): Boolean {
				return false
			}
			
			override fun draw(batch: Batch) {
				texture.setPosition(location.x, location.y)
				texture.draw(batch)
				super.draw(batch)
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
				
				return "NPC[dead=$dead, combat=$inCombat id=$id, type=$entityType, class=$entityClass, resources=$res]"
			}
		}
	}
}
