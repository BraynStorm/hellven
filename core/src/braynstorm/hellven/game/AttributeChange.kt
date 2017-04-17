package braynstorm.hellven.game

data class AttributeChange(var flat: Float, var multiplier: Float) {
	constructor() : this(0f, 1f)
	
	operator fun plusAssign(attributeChange: AttributeChange) {
		flat += attributeChange.flat
		multiplier *= attributeChange.multiplier
	}
	
	
	infix fun applyTo(raw: Float): Float = (raw + flat) * multiplier
	
	
	companion object {
		val NO_CHANGE = AttributeChange()
	}
	
}
