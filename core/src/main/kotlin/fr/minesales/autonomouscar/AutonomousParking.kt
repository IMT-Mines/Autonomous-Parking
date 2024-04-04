package fr.minesales.autonomouscar

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.minesales.autonomouscar.engine.Renderer
import fr.minesales.autonomouscar.engine.base.Scene
import fr.minesales.autonomouscar.game.ParkingScene
import ktx.app.KtxGame
import ktx.app.KtxScreen

class AutonomousParking : KtxGame<KtxScreen>() {

    override fun create() {
        addScreen(Screen())
        setScreen<Screen>()
    }

}

class Screen : KtxScreen {
    private val viewport: ScreenViewport = ScreenViewport()
    private val renderer: Renderer

    companion object {
        lateinit var currentScene: Scene
        lateinit var imGuiRenderer: ImGuiRenderer
    }

    init {
        currentScene = ParkingScene(this)
        currentScene.init()
        renderer = Renderer(currentScene)
        imGuiRenderer = ImGuiRenderer()
        imGuiRenderer.init()
    }

    override fun render(delta: Float) {
        renderer.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, false)
    }

    override fun dispose() {
        imGuiRenderer.dispose()
        renderer.dispose()
    }

    fun setViewportCamera(camera: Camera) {
        viewport.camera = camera;
    }

    fun setActiveScene(scene: Scene) {
        currentScene.dispose()
        currentScene = scene
        currentScene.init()
    }

}
