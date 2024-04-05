package fr.minesales.autonomouscar.engine.ui

import fr.minesales.autonomouscar.engine.base.Scene
import imgui.ImGui

class SceneHierarchy {
    fun draw(scene: Scene) {
        ImGui.begin("Hierarchy")
        scene.actors.forEach {
            ImGui.checkbox(it.actor.name, it.enabled)
        }
        ImGui.end()
    }
}
