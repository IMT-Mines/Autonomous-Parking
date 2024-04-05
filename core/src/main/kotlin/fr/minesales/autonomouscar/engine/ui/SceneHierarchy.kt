package fr.minesales.autonomouscar.engine.ui

import fr.minesales.autonomouscar.engine.base.Scene
import imgui.ImGui

class SceneHierarchy {
    fun draw(scene: Scene) {
        ImGui.begin("Hierarchy")
        scene.actors.forEach {
            ImGui.checkbox("##", it.enabled)
            ImGui.sameLine()
            ImGui.text(it.actor.name)
        }
        ImGui.end()
    }
}
