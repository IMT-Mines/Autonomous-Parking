package fr.minesales.autonomouscar

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.physics.bullet.Bullet
import com.badlogic.gdx.utils.GdxNativesLoader
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.minesales.autonomouscar.engine.InputManager
import fr.minesales.autonomouscar.engine.Physics
import fr.minesales.autonomouscar.engine.Renderer
import fr.minesales.autonomouscar.engine.SceneManager
import fr.minesales.autonomouscar.engine.utils.ImGuiRenderer
import fr.minesales.autonomouscar.game.scenes.parking
import ktx.app.KtxGame
import ktx.app.KtxScreen

class AutonomousParking : KtxGame<KtxScreen>() {
    override fun create() {
        addScreen(Screen())
        setScreen<Screen>()
    }
}

class Screen : KtxScreen {
    private val renderer: Renderer

    companion object {
        val viewport: ScreenViewport = ScreenViewport()
        lateinit var imGuiRenderer: ImGuiRenderer
    }

    init {
        Bullet.init(false, false)
        Physics.init()

        SceneManager.loadScene(::parking)

        renderer = Renderer()
        imGuiRenderer = ImGuiRenderer()
        imGuiRenderer.init()

        Gdx.input.inputProcessor = InputManager.getInstance()
        Gdx.graphics.setVSync(false)
    }

    override fun render(delta: Float) {
        renderer.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, false)
    }

    override fun dispose() {
        Physics.dispose()
        imGuiRenderer.dispose()
        renderer.dispose()
    }
}
