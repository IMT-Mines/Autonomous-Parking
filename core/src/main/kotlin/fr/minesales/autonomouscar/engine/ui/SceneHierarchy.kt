package fr.minesales.autonomouscar.engine.ui

import fr.minesales.autonomouscar.engine.base.Scene
import fr.minesales.autonomouscar.engine.ui.base.BasePanel
import imgui.ImGui

class SceneHierarchy : BasePanel() {
    override fun init() {

    }

    override fun draw(scene: Scene) {
        ImGui.begin("Hierarchy")
        ImGui.setWindowPos(100f, 50f)
        scene.actors.forEach {
            ImGui.checkbox(it.actor.name, it.enabled)
        }
        ImGui.end()
    }
}
