package fr.minesales.autonomouscar.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.engine.base.Scene
import fr.minesales.autonomouscar.engine.base.StaticMeshActor
import fr.minesales.autonomouscar.engine.utils.ModelLoader
import fr.minesales.autonomouscar.game.actors.CameraControllerActor
import ktx.assets.toInternalFile

class ParkingScene(private val screen: Screen) : Scene() {

    private lateinit var camera: Camera

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

    private lateinit var cameraController: CameraControllerActor

    override fun init() {
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f))
        environment.add(DirectionalLight().set(1f, 1f, 1f, 0f, -1f, 0f))

        //StaticMeshActor("Cube", cube)
        //StaticMeshActor("Plane", plane)
        StaticMeshActor(ModelLoader.createActorFromFile("sedan/sedan.g3db".toInternalFile()))

        camera = PerspectiveCamera(60f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        cameraController = CameraControllerActor("MainCamera", camera)
        screen.setViewportCamera(camera)

        actors.forEach { it.start() }
    }

    override fun getCamera(): Camera = camera

    override fun dispose() {}
}
