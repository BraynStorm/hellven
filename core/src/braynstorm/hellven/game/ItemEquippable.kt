package braynstorm.hellven.game

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
