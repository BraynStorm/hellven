package braynstorm.hellven.game

import braynstorm.hellven.game.ability.Ability
import braynstorm.hellven.game.ability.Damage
import braynstorm.hellven.game.aura.AttributeChange
import braynstorm.hellven.game.aura.Aura
import braynstorm.hellven.game.aura.AuraStack
import braynstorm.hellven.game.entity.EntityClass
import braynstorm.hellven.game.resource.Health
import braynstorm.hellven.game.resource.Mana
import braynstorm.hellven.game.resource.Rage
import com.badlogic.gdx.math.MathUtils
import java.util.EnumMap

/**
 * TODO Add class description
 * Created by Braynstorm on 4.4.2017 г..
 */
abstract class AbstractEntity(override final val entityType: EntityType,
                              override final val entityClass: EntityClass,
                              override final val resources: ResourceMap) : Entity {
	
	override final val health: Health = resources[Health::class.java] as Health
	
	override final var target: Entity? = null
	override final var container: WorldCellEntityContainer? = null
	override final var world: GameWorld? = null
	
	override final val auras: MutableList<AuraStack> = ArrayList(2)
	
	protected val equipment: MutableList<EquippableItemSlot> = arrayListOf<EquippableItemSlot>().apply {
		add(EquippableItemSlot(EquipmentSlotType.HEAD))
		add(EquippableItemSlot(EquipmentSlotType.CHEST))
		add(EquippableItemSlot(EquipmentSlotType.FEET))
		add(EquippableItemSlot(EquipmentSlotType.HANDS))
		add(EquippableItemSlot(EquipmentSlotType.LEGS))
		add(EquippableItemSlot(EquipmentSlotType.MAIN_HAND))
		add(EquippableItemSlot(EquipmentSlotType.OFF_HAND))
		add(EquippableItemSlot(EquipmentSlotType.TRINKET))
		add(EquippableItemSlot(EquipmentSlotType.TRINKET))
	}
	
	override val abilities: MutableList<Ability> = arrayListOf()
	
	override final var inCombat: Boolean = false
		set(value) {
			if (value)
				combatTimer = 7f
			else
				combatTimer = 0f
			field = value
		}
	
	override var dead = health.empty
		set(value) {
			if (value)
				inCombat = false
			field = value
		}
	
	override val pixelLocation get() = container?.pixelLocation!!
	override val cellLocation get() = container?.cellLocation!!
	
	var combatTimer = 0f
	var spellCastTimer = 0
	
	val walkTime = 0f
	
	init {
		resources.values.forEach {
			it.entity = this
		}
	}
	
	private var invalid = true
	
	open fun validateAttributes(): Boolean =
			if (invalid) {
				invalid = false
				calculateAttributes()
				true
			} else false
	
	
	fun invalidateAttributes() {
		invalid = true
	}
	
	override fun calculateAttributes() {
		val base = baseAttributes.copy()
		
		// "sums" all changes to the stats of this entity based on Auras
		val changesByAttribute: MutableMap<Attribute, AttributeChange> = EnumMap<Attribute, AttributeChange>(Attribute::class.java)
		Attribute.values().forEach { attr ->
			val change = AttributeChange()
			
			equipment.forEach {
				change.flat += it.itemStack?.attributes?.get(attr) ?: 0f
			}
			
			auras.forEach {
				change += it.aura.getChange(attr)
			}
			
			
			changesByAttribute[attr] = change
		}
		
		// @formatter:off
		val str             = changesByAttribute[Attribute.STRENGTH         ]!! applyTo (base[Attribute.STRENGTH            ]                                       )
		val agi             = changesByAttribute[Attribute.AGILITY          ]!! applyTo (base[Attribute.AGILITY             ]                                       )
		val int             = changesByAttribute[Attribute.INTELLECT        ]!! applyTo (base[Attribute.INTELLECT           ]                                       )
		val wis             = changesByAttribute[Attribute.WISDOM           ]!! applyTo (base[Attribute.WISDOM              ]                                       )
		val stam            = changesByAttribute[Attribute.STAMINA          ]!! applyTo (base[Attribute.STAMINA             ]                                       )
		val armor           = changesByAttribute[Attribute.ARMOR            ]!! applyTo (base[Attribute.ARMOR               ]                                       )
		
		val phyDmg          = changesByAttribute[Attribute.PHYSICAL_DAMAGE  ]!! applyTo (base[Attribute.PHYSICAL_DAMAGE     ] + str * 1.8f + agi * 0.9f             ) // TODO add LEVEL in the calc
		val magDmg          = changesByAttribute[Attribute.MAGICAL_DAMAGE   ]!! applyTo (base[Attribute.MAGICAL_DAMAGE      ] + int * 2.3f + wis * 0.5f             ) // TODO add LEVEL in the calc
		
		val castSpeed       = changesByAttribute[Attribute.CAST_SPEED       ]!! applyTo (base[Attribute.CAST_SPEED          ] - wis * 0.003f                        )
		val moveSpeed       = changesByAttribute[Attribute.MOVEMENT_SPEED   ]!! applyTo (base[Attribute.MOVEMENT_SPEED      ] - agi * 0.1f                          )
		val attackSpeed     = changesByAttribute[Attribute.ATTACK_SPEED     ]!! applyTo (base[Attribute.ATTACK_SPEED        ] - str * 0.006f - agi * 0.15f           )
		
		val health          = changesByAttribute[Attribute.HEALTH           ]!! applyTo (base[Attribute.HEALTH              ] + stam * (15f + level * 1.1f)         )
		val rage            = changesByAttribute[Attribute.RAGE             ]!! applyTo (base[Attribute.RAGE                ]                                       )
		val mana            = changesByAttribute[Attribute.MANA             ]!! applyTo (base[Attribute.MANA                ] + int * 23f + wis * 9f)
		
		val weapDmgMin      = changesByAttribute[Attribute.WEAPON_DAMAGE_MIN]!! applyTo (base[Attribute.WEAPON_DAMAGE_MIN   ] + phyDmg                              )
		val weapDmgMax      = changesByAttribute[Attribute.WEAPON_DAMAGE_MAX]!! applyTo (base[Attribute.WEAPON_DAMAGE_MAX   ] + phyDmg                              )
		
		val healthPerSec    = changesByAttribute[Attribute.HEALTH_PER_SEC   ]!! applyTo (base[Attribute.HEALTH_PER_SEC      ] + level * 2f                          )
		val manaPerSec      = changesByAttribute[Attribute.MANA_PER_SEC     ]!! applyTo (base[Attribute.MANA_PER_SEC        ] + wis * 1.4f + level * 2f             )
		
		base[Attribute.STRENGTH         ] = str
		base[Attribute.AGILITY          ] = agi
		base[Attribute.INTELLECT        ] = int
		base[Attribute.WISDOM           ] = wis
		base[Attribute.STAMINA          ] = stam
		
		base[Attribute.ARMOR            ] = armor
		
		base[Attribute.PHYSICAL_DAMAGE  ] = phyDmg
		base[Attribute.MAGICAL_DAMAGE   ] = magDmg
		
		base[Attribute.MOVEMENT_SPEED   ] = Utils.FloatMath.lowerBounded(5f, moveSpeed)
		base[Attribute.CAST_SPEED       ] = Utils.FloatMath.lowerBounded(0.2f, castSpeed)
		base[Attribute.ATTACK_SPEED     ] = Utils.FloatMath.lowerBounded(5f, attackSpeed)
		
		base[Attribute.HEALTH           ] = Utils.FloatMath.lowerBounded(1f, health)
		base[Attribute.RAGE             ] = rage
		base[Attribute.MANA             ] = mana
		
		base[Attribute.WEAPON_DAMAGE_MIN] = Utils.FloatMath.lowerBounded(1f, weapDmgMin)
		base[Attribute.WEAPON_DAMAGE_MAX] = Utils.FloatMath.lowerBounded(1f, weapDmgMax)
		
		base[Attribute.HEALTH_PER_SEC   ] = healthPerSec
		base[Attribute.MANA_PER_SEC     ] = manaPerSec
		// @formatter:on
		
		this.health.capacity = base[Attribute.HEALTH]
		this.resources[Mana::class.java]?.capacity = base[Attribute.MANA]
		this.resources[Rage::class.java]?.capacity = base[Attribute.RAGE]
		
		calculatedAttributes = base
	}
	
	override fun containerChanged(newContainer: WorldCellEntityContainer?) {
		if (newContainer === container)
			return // prevents EntityContainer.release(this) from going into infinite recursion
		
		
		container?.releaseSilent(this)
		container = newContainer
		world = container?.world
		pixelLocation.x = newContainer?.pixelLocation?.x ?: -1f
		pixelLocation.y = newContainer?.pixelLocation?.y ?: -1f // #ElvisOP
	}
	
	override fun tickResource() {
		if (!dead) {
			if (inCombat) {
				combatTimer--
			}
			
			spellCastTimer--
			
			if (MathUtils.isEqual(combatTimer, 0f)) {
				inCombat = false
			}
			
			resources.values.forEach(TickReceiverResource::tickResource)
		}
	}
	
	override fun setInCastStress() {
		spellCastTimer = 7
	}
	
	override fun isInCastStress(): Boolean {
		return spellCastTimer > 0
	}
	
	override fun tickAuras() {
		// iterates the whole set, removing any expired auras
		auras.removeIf(AuraStack::tick)
	}
	
	override fun hasAura(aura: Aura): Boolean {
		return auras.find { it.aura == aura } != null
	}
	
	override fun applyAura(auraStack: AuraStack) {
		auraStack.receiver = this
		auras.add(auraStack)
		invalidateAttributes()
	}
	
	override fun removeAura(auraStack: AuraStack) {
		auras.remove(auraStack)
		invalidateAttributes()
	}
	
	override fun hasEmptySlot(slot: EquipmentSlotType): Boolean {
		return getFirstEmptySlot(slot) != null
	}
	
	override fun getFirstEmptySlot(slot: EquipmentSlotType): EquippableItemSlot? {
		return equipment.find { slot == it.slot }
	}
	
	override fun equip(itemStack: EquippableItemStack): Boolean {
		if (inCombat) {
			return false
		}
		
		val slot = getFirstEmptySlot(itemStack.slot) ?: return false
		if (slot.isEmpty()) {
			val result = slot.insertItem(itemStack)
			
			if (result)
				invalidateAttributes()
			
			return result
		}
		
		return false
	}
	
	/**
	 * Unequips the [EquippableItemStack] from [slot], returning it.
	 *
	 * @return the [EquippableItemStack] that was in the slot, or null if the slot was empty
	 */
	override fun unequip(slot: EquippableItemSlot): EquippableItemStack? {
		val stack = slot.removeItem()
		invalidateAttributes()
		return stack
	}
	
	/**
	 * Used in [autoEquip].
	 *
	 * @return If there is an empty slot, valid for this [EquippableItemStack] then it returns it, otherwise, it returns the first slot that is valid (regardless of it being empty or not).
	 */
	override fun getAutoEquipSlot(slot: EquipmentSlotType): EquippableItemSlot {
		val validSlots = equipment.filter {
			slot == it.slot
		}
		
		return validSlots.find { it.isEmpty() } ?: validSlots.first()
	}
	
	/**
	 * Easy-equip an item
	 *
	 * @return the [EquippableItemStack] that was in the slot before the equip action.
	 */
	override fun autoEquip(itemStack: EquippableItemStack): EquippableItemStack? {
		val slot = getAutoEquipSlot(itemStack.slot)
		val oldItemStack = slot.removeItem()
		slot.insertItem(itemStack)
		invalidateAttributes()
		return oldItemStack
	}
	
	fun heal(healing: Float) {
		if (!dead) {
			health.fill(healing, true)
		}
	}
	
	override fun receiveDamage(damage: Damage) {
		if (!dead) {
			if (health.drain(damage.calculateAgainst(this), true) > 0) {
				damage.source.inCombat = true
			}
			
			if (health.empty) {
				dead = true
			} else {
				inCombat = true
			}
		}
		
	}
	
	override fun hasResource(resource: Class<ResourcePool>): Boolean {
		return resources.containsKey(resource)
	}
	
	override fun get(attr: Attribute): Float {
		validateAttributes()
		return calculatedAttributes[attr]
	}
	
	
	fun printItems() {
		equipment.forEach(::println)
	}
	
}

