package fr.minesales.autonomouscar.actors;

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.engine.utils.Time
import fr.minesales.autonomouscar.engine.base.BaseActor
import imgui.ImGui

class CameraControllerActor(name: String, val camera: Camera) : BaseActor(name, null) {
    private var sensitivity = 10f
    private var x = 0f
    private var y = 0f
    private var prevX = 0f
    private var prevY = 0f

    override fun start() {
        camera.position.set(0f, 1f, 10f)
        camera.update()
    }

    override fun update() {
        if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
            Gdx.input.isCursorCatched = true

            x += -Gdx.input.deltaX * sensitivity * Time.unscaledDeltaTime
            y += Gdx.input.deltaY * sensitivity * Time.unscaledDeltaTime

            y = MathUtils.clamp(y, -90f, 90f)

            val forward = camera.direction.cpy().nor()
            val up = Vector3(0f, 1f, 0f)
            camera.rotate(up, x - prevX)
            val right = up.cpy().crs(camera.direction)
            camera.rotate(right, y - prevY)

            prevX = x
            prevY = y

            if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.translate(forward.scl(Time.unscaledDeltaTime * 10f))
            if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.translate(forward.scl(-Time.unscaledDeltaTime * 10f))
            if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.translate(right.scl(Time.unscaledDeltaTime * 10f))
            if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.translate(right.scl(-Time.unscaledDeltaTime * 10f))
            if (Gdx.input.isKeyPressed(Input.Keys.E)) camera.translate(up.scl(Time.unscaledDeltaTime * 10f))
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) camera.translate(up.scl(-Time.unscaledDeltaTime * 10f))
        }else{
            Gdx.input.isCursorCatched = false
        }

        camera.update()
    }

    override fun gui() {
        val q = camera.view.getRotation(Quaternion())
        ImGui.begin("Camera Controller")
        ImGui.text("Camera position ${camera.position.x}, ${camera.position.y}, ${camera.position.z}")
        ImGui.text("Camera rotation ${q.pitch}, ${q.yaw}, ${q.roll}")
        ImGui.text("Axis x $x y $y")
        ImGui.text("Forward ${camera.direction.cpy()}")
        ImGui.text("Right ${camera.direction.cpy().crs(Vector3(0f, 1f, 0f))}")
        ImGui.end()
    }
}
