package fr.minesales.autonomouscar

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.minesales.autonomouscar.engine.Renderer
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
    private val renderer = Renderer(this)

    companion object {
        lateinit var imGuiRenderer: ImGuiRenderer
    }

    init {
        imGuiRenderer = ImGuiRenderer()
        imGuiRenderer.init()
    }

    override fun render(delta: Float) {
        renderer.render(delta)
//        G3dModelLoader(JsonReader()).loadModel("model.g3dj".toInternalFile())
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

}
