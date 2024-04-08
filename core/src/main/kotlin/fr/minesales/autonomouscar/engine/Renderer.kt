package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.collision.BoundingBox
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.engine.base.BaseActor
import fr.minesales.autonomouscar.engine.ui.SceneHierarchy
import fr.minesales.autonomouscar.engine.ui.Statistics
import fr.minesales.autonomouscar.engine.utils.Time
import ktx.app.clearScreen

class Renderer {
    private val modelBatch = ModelBatch()
    private val hierarchy = SceneHierarchy()
    private val statistics = Statistics()

    fun render(delta: Float) {
        clearScreen(red = 0f, green = 0f, blue = 0f)
        val scene = SceneManager.currentScene
        val camera = scene?.mainCamera ?: return

        Time.setTimings(delta)

        scene.actors.forEach {
            if (!it.enabled.get()) return@forEach
            it.update()
        }

        modelBatch.begin(camera)

        scene.actors.forEach {
            if (!it.enabled.get() || it.model == null /*|| cull(camera, it.actor)*/) return@forEach
            modelBatch.render(it, scene.environment)
        }

        modelBatch.end()

        Screen.imGuiRenderer.render {
            renderGui(scene)
            renderEngineGui(scene)
        }

        Physics.step(delta)
        Physics.debugDrawer.begin(camera)
        Physics.physicsScene.debugDrawWorld()
        Physics.debugDrawer.end()

        SceneManager.onEndOfFrame()
    }

    private fun cull(camera: Camera, actor: BaseActor): Boolean {
        val aabb = BoundingBox()
        actor.calculateBoundingBox(aabb)
        return !camera.frustum.boundsInFrustum(aabb)
    }

    private fun renderGui(scene: Scene){
        scene.actors.forEach {
            if (!it.enabled.get()) return@forEach
            it.gui()
        }
    }

    private fun renderEngineGui(scene: Scene){
        hierarchy.draw(scene)
        statistics.draw(scene)
    }

    fun dispose() {
        modelBatch.dispose()
    }
}
