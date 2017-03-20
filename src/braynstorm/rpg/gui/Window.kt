package braynstorm.rpg.gui

import braynstorm.dirty.Dirty
import braynstorm.logger.GlobalKogger
import braynstorm.rpg.files.Config
import braynstorm.rpg.gui.shaders.ShaderProgram
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.WindowConstants

object Window : Dirty {
	
	val window: Long
	
	var monitor: Monitor
		private set
	var videoMode: GLFWVidMode
		private set
	var mode: WindowMode = WindowMode.UNKNOWN
		private set(value) {
			Config.setStringValue("window_mode", value.name)
			field = value
		}
	
	var width: Int = Config.getIntValue("window_width")
		private set(value) {
			Config.setIntValue("window_width", value)
			field = value
		}
	
	var height: Int = Config.getIntValue("window_height")
		private set(value) {
			Config.setIntValue("window_height", value)
			field = value
		}
	
	var refreshRate: Int = Config.getIntValue("window_refreshRate")
		private set(value) {
			Config.setIntValue("window_refreshRate", value)
			field = value
		}
	
	
	val projectionBuffer = BufferUtils.createFloatBuffer(16)!!
	val matrix = Matrix4f()
	
	fun useOrthoProjection() {
		checkAndCleanUp()
		ShaderProgram.current!!.setUniformMatrix4("projection", projectionBuffer)
	}
	
	init {
		GLFW.glfwSetErrorCallback(GLFWErrorCallback.createThrow())
		
		if (!GLFW.glfwInit()) {
			GlobalKogger.logWarning("GLFW Failed to initialize")
			val frame = JFrame(Config.getStringValue("window_title"))
			frame.setSize(200, 100)
			frame.add(JLabel("GLFW Failed to initialize, so the application cannot proceed."))
			frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
			
			frame.pack()
			frame.setLocationRelativeTo(null)
			frame.isVisible = true
			
			while (true) {
				Thread.sleep(100)
			}
		}
		
		monitor = Monitors.find(Config.getStringValue("monitor"))
		
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE)
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE)
		window = GLFW.glfwCreateWindow(1, 1, Config.getStringValue("window_title"), 0, 0)
		
		// Debug
		Monitors.forEach { GlobalKogger.logDebug(it.toString()) }
		
		// Make context and make OpenGL
		GLFW.glfwMakeContextCurrent(window)
		GL.createCapabilities()
		
		setupEvents()
		setupListeners()
		val configMode = WindowMode.valueOf(Config.getStringValue("window_mode").toUpperCase())
		when (configMode) {
			WindowMode.FULLSCREEN -> {
				videoMode = monitor.getClosestVideomodeSupported(width, height, 60)
				setFullscreen(monitor, videoMode)
			}
			WindowMode.BORDERLESS -> {
				videoMode = monitor.maximumResolution
				setBorderless()
			}
			else                  -> {
				videoMode = monitor.maximumResolution
				setWindowed(width, height)
			}
		}
//		GLFW.glfwSetWindowIcon(window, GLFWImage())
//		ResourceManager.loadAllLists()
//		ResourceManager.loadAllShaders()
	}
	
	
	private var dirty = true
	
	override fun isDirty(): Boolean = dirty
	
	override fun setDirty() {
		dirty = true
	}
	
	override fun cleanUp() {
		matrix.setOrtho2D(0f, width.toFloat(), 0f, height.toFloat())
		matrix.get(projectionBuffer)
	}
	
	
	private fun setupEvents() {
		// Resizing
		GLFW.glfwSetWindowSizeCallback(window, { _, width, height ->
			WindowResizeEvent fire WindowSizeArgument(width, height)
		})
		
		// Window Focus
		GLFW.glfwSetWindowFocusCallback(window, { _, state ->
			when (state) {
				true  -> WindowGainedFocusEvent fire Unit
				false -> WindowLostFocusEvent fire Unit
			}
		})
		
		GLFW.glfwSetWindowCloseCallback(window, { _ ->
			// TODO WindowCloseCallback
			GlobalKogger.logDebug("ASDASDASD   CLOSING")
			WindowCloseEvent fire Unit
		})
		
		// Key presses
		GLFW.glfwSetKeyCallback(window, { _, key, scancode, action, mods ->
			Keyboard.setKeyState(key, action != GLFW.GLFW_RELEASE)
			
			when (action) {
				GLFW.GLFW_PRESS   -> {
					Keyboard.setKeyState(key, true)
					KeyDownEvent fire KeyArgument(key)
				}
				
				GLFW.GLFW_RELEASE -> {
					Keyboard.setKeyState(key, false)
					KeyUpEvent fire KeyArgument(key)
				}
			}
		})
		
		GLFW.glfwSetCharCallback(window, { _, char ->
			// TODO add an onCharTyped event
		})
		
		// Mouse
		GLFW.glfwSetCursorPosCallback(window, { _, x, y ->
			Mouse.x = x.toInt()
			Mouse.y = y.toInt()
			
			MouseMoveEvent fire MousePositionArgument(Mouse.x, Mouse.y)
		})
		
		GLFW.glfwSetMouseButtonCallback(window, { _, button, action, _ ->
			when (action) {
				GLFW.GLFW_PRESS   -> {
					Mouse.setButtonState(button, true)
					MouseDownEvent fire MouseButtonArgument(button)
				}
				
				GLFW.GLFW_RELEASE -> {
					Mouse.setButtonState(button, false)
					MouseUpEvent fire MouseButtonArgument(button)
				}
			}
		})
		
	}
	
	private fun setupListeners() {
		WindowResizeEvent on {
			GL11.glViewport(0, 0, width, height)
			setDirty()
		}
	}
	
	/**
	 * Sets the window to fullscreen on this monitor
	 * using the maximum supported resolution.
	 */
	fun setFullscreen(monitor: Monitor = this.monitor, videoMode: GLFWVidMode = this.videoMode) {
		this.monitor = monitor
		Config.setStringValue("monitor", monitor.name)
		
		if (!monitor.videoModes.contains(videoMode)) {
			this.videoMode = videoMode
			GlobalKogger.logCritical("Monitor doesn't support this video mode. monitor=$monitor videomode=$videoMode")
		} else {
			this.videoMode = monitor.maximumResolution
		}
		
		width = videoMode.width()
		height = videoMode.height()
		refreshRate = videoMode.refreshRate()
		
		mode = WindowMode.FULLSCREEN
		GLFW.glfwSetWindowMonitor(window, monitor.id, 0, 0, width, height, refreshRate)
	}
	
	
	fun setWindowed(width: Int = this.width, height: Int = this.height) {
		this.width = width
		this.height = height
		
		
		val monitor = Monitors.primaryMonitor
		val xArr = IntArray(1)
		val yArr = IntArray(1)
		GLFW.glfwGetMonitorPos(Window.monitor.id, xArr, yArr)
		val monitorX = xArr[0]
		val monitorY = yArr[0]
		
		val maxRes = monitor.maximumResolution
		
		if (mode == WindowMode.WINDOWED) {
			GLFW.glfwSetWindowSize(window, width, height)
			GLFW.glfwSetWindowPos(window, monitorX + maxRes.width() / 2 - width / 2, monitorY + maxRes.height() / 2 - height / 2)
			return
		}
		
		mode = WindowMode.WINDOWED
		
		GLFW.glfwSetWindowMonitor(window, 0, monitorX + maxRes.width() / 2 - width / 2, monitorY + maxRes.height() / 2 - height / 2, width, height, 0)
		GLFW.glfwSetWindowAttrib(window, GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE)
	}
	
	fun setBorderless(monitor: Monitor = this.monitor) {
		this.monitor = monitor
		Config.setStringValue("monitor", monitor.name)
		
		videoMode = monitor.maximumResolution
		width = videoMode.width()
		height = videoMode.height()
		refreshRate = videoMode.refreshRate()
		
		val xArr = IntArray(1)
		val yArr = IntArray(1)
		GLFW.glfwGetMonitorPos(Window.monitor.id, xArr, yArr)
		val monitorX = xArr[0]
		val monitorY = yArr[0]
		println("$monitorX $monitorY  $width $height")
		if (mode == WindowMode.BORDERLESS) {
			GLFW.glfwSetWindowSize(window, width, height)
			GLFW.glfwSetWindowPos(window, monitorX, monitorY)
			return
		}
		
		mode = WindowMode.BORDERLESS
		GLFW.glfwSetWindowMonitor(window, 0, monitorX, monitorY, width, height, refreshRate)
		GLFW.glfwSetWindowAttrib(window, GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE)
		GLFW.glfwSetWindowSize(window, width, height) // bugfix
		GLFW.glfwSetWindowPos(window, monitorX, monitorY) // bugfix
		
	}
	
	fun frameStart() {
		GL11.glClearColor(0f, 0f, 0f, 1f)
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
	}
	
	fun frameEnd() {
		GLFW.glfwPollEvents()
		GLFW.glfwSwapBuffers(window)
	}
	
	
	fun destroy() {
		GLFW.glfwDestroyWindow(window)
		GLFW.glfwTerminate()
	}
	
}
