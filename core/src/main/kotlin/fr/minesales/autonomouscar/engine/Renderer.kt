package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.actors.CameraControllerActor
import fr.minesales.autonomouscar.actors.MeshActor
import ktx.app.clearScreen

class Renderer(screen: Screen) {
    private val modelBatch = ModelBatch()
    private val builder = ModelBuilder()
    private val cube = ModelInstance(
        builder.createBox(
            5f,
            5f,
            5f,
            Material(ColorAttribute.createDiffuse(Color.BLUE)),
            Usage.Position.toLong() or Usage.Normal.toLong()
        )
    )
    private val plane = ModelInstance(
        builder.createBox(
            20f,
            0.1f,
            20f,
            Material(ColorAttribute.createDiffuse(Color.GREEN)),
            Usage.Position.toLong() or Usage.Normal.toLong()
        )
    )

    private val cameraController: CameraControllerActor

    companion object {
        val currentScene: Scene = Scene()
    }

    init {
        currentScene.environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f))
        currentScene.environment.add(DirectionalLight().set(1f, 1f, 1f, 0f, -1f, 0f))

        MeshActor("Cube", cube)
        MeshActor("Plane", plane)

        val camera = PerspectiveCamera(60f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        cameraController = CameraControllerActor("MainCamera", camera)
        screen.setViewportCamera(camera)

        startActors()
    }

    fun render(delta: Float) {
        clearScreen(red = 0f, green = 0f, blue = 0f)

        Time.setTimings(delta)
        updateActors()

        modelBatch.begin(cameraController.camera)

        for (actor in currentScene.actors) {
            if (!actor.enabled || actor.model == null) continue
            modelBatch.render(actor.model, currentScene.environment)
        }

        modelBatch.end()

        for (actor in currentScene.actors) {
            if (!actor.enabled) continue
            actor.gui()
        }
    }

    fun startActors() {
        for (actor in currentScene.actors) {
            actor.start()
        }
    }

    fun updateActors() {
        for (actor in currentScene.actors) {
            actor.update()
        }
    }

    fun dispose() {
        modelBatch.dispose()
    }
}
