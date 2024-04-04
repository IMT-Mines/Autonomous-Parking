package fr.minesales.autonomouscar.actors;

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Quaternion
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.engine.base.BaseActor
import imgui.ImGui

class CameraControllerActor(name: String, val camera: Camera) : BaseActor(name, null) {

    override fun start() {
        camera.position.set(0f, 1f, 10f)
        camera.update()
    }

    override fun update() {
        camera.update()
    }

    override fun gui() {
        Screen.imGuiRenderer.render {
            val q = camera.view.getRotation(Quaternion())
            ImGui.text("Camera position ${camera.position.x}, ${camera.position.y}, ${camera.position.z}")
            ImGui.text("Camera rotation ${q.pitch}, ${q.yaw}, ${q.roll}")
        }
    }
}
