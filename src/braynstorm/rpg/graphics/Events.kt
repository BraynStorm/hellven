package braynstorm.rpg.graphics

import braynstorm.events.SimpleEvent


data class MousePositionArgument(val x: Int, val y: Int)
data class MouseButtonArgument(val button: Int)
data class MouseDragArgument(val button: Int, val startX: Int, val startY: Int)
data class KeyArgument(val keycode: Int)
data class WindowSizeArgument(val width: Int, val height: Int)

object MouseMoveEvent : SimpleEvent<MousePositionArgument>() // TODO [PP] Performance, a lot of MousePositionArgument objects.
object MouseDownEvent : SimpleEvent<MouseButtonArgument>()
object MouseUpEvent : SimpleEvent<MouseButtonArgument>()
object MouseDragStartEvent : SimpleEvent<MouseDragArgument>()
object MouseDragEndEvent : SimpleEvent<MouseDragArgument>()

object KeyDownEvent : SimpleEvent<KeyArgument>()
object KeyUpEvent : SimpleEvent<KeyArgument>()

object WindowResizeEvent : SimpleEvent<WindowSizeArgument>()
object WindowGainedFocusEvent : SimpleEvent<Unit>()
object WindowLostFocusEvent : SimpleEvent<Unit>()
object WindowCloseEvent : SimpleEvent<Unit>()

