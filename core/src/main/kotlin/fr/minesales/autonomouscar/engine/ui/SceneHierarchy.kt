package fr.minesales.autonomouscar.engine.ui

import fr.minesales.autonomouscar.engine.base.Scene
import fr.minesales.autonomouscar.engine.ui.base.BasePanel
import imgui.ImGui

class SceneHierarchy : BasePanel() {
    override fun draw(scene: Scene) {
        ImGui.begin("Hierarchy")
        scene.actors.forEach {
            ImGui.checkbox(it.actor.name, it.enabled)
        }
        ImGui.end()
    }
}
