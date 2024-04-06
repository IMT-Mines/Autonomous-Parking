package fr.minesales.autonomouscar.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import fr.minesales.autonomouscar.engine.InputManager
import fr.minesales.autonomouscar.engine.SceneManager
import fr.minesales.autonomouscar.engine.base.BaseActor
import fr.minesales.autonomouscar.engine.utils.Time
import imgui.ImGui

class CameraControllerActor(name: String, val camera: Camera) : BaseActor(name, null) {
    private var sensitivity = 10f
    private var x = 0f
    private var y = 0f
    private var prevX = 0f
    private var prevY = 0f

    init {
        SceneManager.currentScene?.mainCamera = camera
    }

    override fun start() {
        camera.near = 0.03f
        camera.position.set(0f, 0f, 5f)
        camera.update()
    }

    override fun update() {
        if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
            Gdx.input.isCursorCatched = true

            x += -Gdx.input.deltaX * sensitivity * Time.unscaledDeltaTime
            y += Gdx.input.deltaY * sensitivity * Time.unscaledDeltaTime

            y = MathUtils.clamp(y, -90f, 90f)

            val forward = camera.direction.cpy().nor()
            camera.rotate(Vector3.Y, x - prevX)
            val up = camera.up.cpy().nor()
            val right = up.cpy().crs(camera.direction).nor()
            camera.rotate(right, y - prevY)

            prevX = x
            prevY = y

            val movementSpeed = InputManager.getInstance().cameraSpeed

            if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.translate(forward.scl(Time.unscaledDeltaTime * movementSpeed))
            if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.translate(forward.scl(-Time.unscaledDeltaTime * movementSpeed))
            if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.translate(right.scl(Time.unscaledDeltaTime * movementSpeed))
            if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.translate(right.scl(-Time.unscaledDeltaTime * movementSpeed))
            if (Gdx.input.isKeyPressed(Input.Keys.E)) camera.translate(up.scl(Time.unscaledDeltaTime * movementSpeed))
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) camera.translate(up.scl(-Time.unscaledDeltaTime * movementSpeed))
        } else {
            Gdx.input.isCursorCatched = false
        }

        camera.update()
    }

    override fun gui() {
        /*val q = camera.view.getRotation(Quaternion())
        ImGui.begin("Camera Controller")
        ImGui.text("Camera position ${camera.position.x}, ${camera.position.y}, ${camera.position.z}")
        ImGui.text("Camera rotation ${q.pitch}, ${q.yaw}, ${q.roll}")
        ImGui.text("Axis x $x y $y")
        ImGui.text("Forward ${camera.direction.cpy()}")
        ImGui.text("Right ${camera.direction.cpy().crs(Vector3(0f, 1f, 0f))}")
        ImGui.end()*/
    }
}
