package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.graphics.g3d.ModelBatch
import fr.minesales.autonomouscar.engine.base.Scene
import fr.minesales.autonomouscar.engine.utils.Time
import ktx.app.clearScreen

class Renderer(private val scene: Scene) {
    private val modelBatch = ModelBatch()

    fun render(delta: Float) {
        clearScreen(red = 0f, green = 0f, blue = 0f)

        Time.setTimings(delta)

        scene.actors.forEach { it.update() }

        modelBatch.begin(scene.getCamera())

        for (sceneActor in scene.actors) {
            if (!sceneActor.enabled || sceneActor.actor.model == null) continue
            modelBatch.render(sceneActor.actor.model, scene.environment)
        }

        modelBatch.end()

        for (actor in scene.actors) {
            if (!actor.enabled) continue
            actor.gui()
        }
    }

    fun dispose() {
        modelBatch.dispose()
        scene.dispose()
    }
}
