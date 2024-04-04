package fr.minesales.autonomouscar.actors;

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.engine.Time
import fr.minesales.autonomouscar.engine.base.BaseActor
import imgui.ImGui

class CameraControllerActor(name: String, val camera: Camera) : BaseActor(name, null) {

    private var cameraSpeed = 10f

    override fun start() {
        camera.position.set(0f, 1f, 10f)
        camera.update()
    }

    override fun update() {
        if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
            camera.rotate(Vector3.Y, Gdx.input.deltaX * cameraSpeed * Time.unscaledDeltaTime)
            camera.rotate(Vector3.X, Gdx.input.deltaY * cameraSpeed * Time.unscaledDeltaTime)
        }
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
