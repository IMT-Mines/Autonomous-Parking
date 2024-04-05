package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.graphics.g3d.ModelBatch
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.engine.base.Scene
import fr.minesales.autonomouscar.engine.ui.SceneHierarchy
import fr.minesales.autonomouscar.engine.utils.Time
import imgui.ImGui
import ktx.app.clearScreen

class Renderer(private val scene: Scene) {
    private val modelBatch = ModelBatch()
    private val hierarchy = SceneHierarchy()

    var test = false;

    fun render(delta: Float) {
        clearScreen(red = 0f, green = 0f, blue = 0f)

        Time.setTimings(delta)

        scene.actors.forEach { it.update() }

        modelBatch.begin(scene.getCamera())

        scene.actors.forEach {
            if (!it.enabled || it.actor.model == null) return@forEach
            modelBatch.render(it.actor.model, scene.environment)
        }

        modelBatch.end()

        Screen.imGuiRenderer.render {
            scene.actors.forEach {
                if (!it.enabled) return@forEach
                it.gui()
            }

            hierarchy.draw(scene)

            test = ImGui.checkbox("Test", test)
        }
    }

    fun dispose() {
        modelBatch.dispose()
        scene.dispose()
    }
}
