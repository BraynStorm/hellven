package braynstorm.hellven.game

/**
 * TODO Add class description
 * Created by Braynstorm on 1.4.2017 г..
 */


interface TickReceiverResource {
	fun tickResource()
}

interface TickReceiverGameObject {
	fun tickGameObject()
}

interface TickReceiverFieldGroup {
	fun tickFieldGroup()
}

interface TickReceiverNPCAI {
	fun tickNPCAI()
}

interface TickReceiverAura {
	fun tickAuras()
}

interface TickReceiverMovement {
	fun tickMove()
}
