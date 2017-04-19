package braynstorm.hellven.game.api

import braynstorm.hellven.game.*
import braynstorm.hellven.game.ability.Ability
import braynstorm.hellven.game.ability.Damage
import braynstorm.hellven.game.attributes.Attribute
import braynstorm.hellven.game.attributes.Attributes
import braynstorm.hellven.game.attributes.Direction
import braynstorm.hellven.game.aura.Aura
import braynstorm.hellven.game.aura.AuraStack
import braynstorm.hellven.game.entity.EntityClass
import braynstorm.hellven.game.entity.EntityType
import braynstorm.hellven.game.entity.Hostility
import braynstorm.hellven.game.items.EquipmentSlotType
import braynstorm.hellven.game.items.EquippableItemSlot
import braynstorm.hellven.game.items.EquippableItemStack
import braynstorm.hellven.game.resource.Health
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

/**
 * TODO Add class description
 * Created by Braynstorm on 3.4.2017 г..
 */
interface Entity : Targetable, TickReceiverResource, TickReceiverAura {
	val abilities: MutableList<Ability>
	/**
	 * The current target of the entity.
	 *
	 * Most abilities affect the target
	 */
	var target: Entity?
	
	/**
	 * The [GameWorld] that this entity belongs to.
	 */
	val world: GameWorld?
	
	/**
	 * Where the Entity is currently located.
	 */
	var container: WorldCellEntityContainer?
	
	val entityType: EntityType
	val entityClass: EntityClass
	
	/**
	 * The level of the entity.
	 */
	var level: Int
	
	/**
	 * All the auras applied to this entity currently
	 */
	val auras: MutableList<AuraStack>
	
	/**
	 * The starting attributes of the entity.
	 *
	 * These are used as the base for [calculateAttributes].
	 *
	 * Put the starting Health, Mana, Rage, etc... here
	 */
	val baseAttributes: Attributes
	
	/**
	 * Contains a cached version of the attributes of the entity, calculated by [calculateAttributes].
	 */
	var calculatedAttributes: Attributes
	
	/**
	 * Computes the [calculatedAttributes] field
	 */
	fun calculateAttributes()
	
	/**
	 * The health of the entity
	 *
	 * MUST BE CONTAINED IN THE [resources]
	 */
	val health: Health
	/**
	 * Is the entity in combat
	 */
	var inCombat: Boolean
	
	/**
	 * Is the entity dead
	 */
	var dead: Boolean
	/**
	 * Is the entity in motion
	 */
	val moving: Boolean
	
	/**
	 * Where is the entity headed to.
	 */
	var movementDirection: Direction
	
	/**
	 * @return the reaction of this entity towards [other]
	 */
	infix fun getHostilityTowards(other: Entity): Hostility
	
	/**
	 * Whether or not an [Ability] can be used by this [Entity]
	 */
	infix fun isCapableOfUsing(ability: Ability): Boolean
	
	/**
	 * Whether or not an [Ability] can be used by this [Entity] now.
	 *
	 * Checks for resources and other conditions.
	 */
	infix fun canUseNow(ability: Ability): Boolean
	
	/**
	 * Called when the [Entity] wants to use an ability.
	 */
	infix fun useAbility(ability: Ability): Boolean
	
	/**
	 * Gets called just before this entity's container changes
	 */
	fun containerChanged(newContainer: WorldCellEntityContainer?)
	
	
	/**
	 * Draws the entity on the screen.
	 */
	fun draw(batch: Batch)
	
	/**
	 * Computes and returns the value for this statistic.
	 */
	operator fun get(attr: Attribute): Float
	
	/**
	 * called when damage is delt to this entity
	 */
	fun receiveDamage(damage: Damage)
	
	/**
	 * Adds an aura to the entity
	 */
	fun applyAura(auraStack: AuraStack)
	
	/**
	 * Removes an aura from the entity
	 */
	fun removeAura(auraStack: AuraStack)
	
	/**
	 * @return true if there an instance of this [Aura] is applied to the entity.
	 */
	fun hasAura(aura: Aura): Boolean
	
	/**
	 * Tries to equip an item in its default slot
	 *
	 * @return True if the item is equippable and the action succeeded
	 */
	infix fun equip(itemStack: EquippableItemStack): Boolean
	
	/**
	 * @return The itemStack that was equipped previously, null if the slot was empty
	 */
	infix fun unequip(slot: EquippableItemSlot): EquippableItemStack?
	
	/**
	 * @return true if the entity can equip an item fitting in the [EquippableItemSlot] without having to unequip another item. False otherwise
	 */
	infix fun hasEmptySlot(slot: EquipmentSlotType): Boolean
	
	/**
	 * @return the first slot where there is no item equipped and can fit an item with this [EquipmentSlotType]
	 */
	infix fun getFirstEmptySlot(slot: EquipmentSlotType): EquippableItemSlot?
	
	/**
	 * Automatically equips an itemStack, in the first empty valid slot.
	 *
	 * If no empty valid slots are found, it removes the [EquippableItemStack] from the first valid slot and equips the new stack there, returning the old stack.
	 */
	infix fun autoEquip(itemStack: EquippableItemStack): EquippableItemStack?
	
	/**
	 * @return the slot to be used for [autoEquip]
	 */
	infix fun getAutoEquipSlot(slot: EquipmentSlotType): EquippableItemSlot
	
	fun isInCastStress(): Boolean
	fun setInCastStress()
	
	/**
	 * The location of entity in PIXELS
	 */
	val pixelLocation: Vector2
	
	/**
	 * The location of the entity in CELLS
	 */
	val cellLocation: Vector2
}

