package braynstorm.hellven.game.api

import braynstorm.hellven.game.attributes.Attributes
import braynstorm.hellven.game.items.EquipmentSlotType

interface ItemEquippable : Item {
	/**
	 * In which slot does this item get equipped.
	 */
	val slot: EquipmentSlotType
	
	/**
	 * The attributes that this item affects.
	 */
	val attributes: Attributes
}
