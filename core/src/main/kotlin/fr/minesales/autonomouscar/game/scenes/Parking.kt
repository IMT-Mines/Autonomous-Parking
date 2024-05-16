package fr.minesales.autonomouscar.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.collision.btBoxShape
import fr.minesales.autonomouscar.engine.Physics
import fr.minesales.autonomouscar.engine.RigidBodyInfo
import fr.minesales.autonomouscar.engine.base.Actor
import fr.minesales.autonomouscar.engine.Scene
import fr.minesales.autonomouscar.engine.base.StaticMeshActor
import fr.minesales.autonomouscar.engine.utils.ModelLoader
import fr.minesales.autonomouscar.game.actors.CameraControllerActor
import fr.minesales.autonomouscar.game.actors.VehicleActor
import ktx.assets.toInternalFile
import org.lwjgl.system.linux.Stat

fun parking(scene: Scene){
    scene.environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f))
    scene.environment.add(DirectionalLight().set(1f, 1f, 1f, 0f, -1f, 0f))

    val mb = ModelBuilder()

    val ground = mb.createBox(100f, 0.1f, 100f, Material(ColorAttribute(ColorAttribute.Diffuse, 0.5f, 0.5f, 0.5f, 1f)), Usage.Position.toLong() or Usage.Normal.toLong())

    CameraControllerActor(Actor("MainCamera"), PerspectiveCamera(60f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))

    //StaticMeshActor(Actor("Ground", ground, RigidBodyInfo(0f, btBoxShape(Vector3(50f, 0.05f, 50f)))))
    StaticMeshActor(Actor("Parking", ModelLoader.loadG3DFromFile("parking.g3db".toInternalFile()), RigidBodyInfo(0f, btBoxShape(Vector3(50f, 0.05f, 50f)))))

    val test = StaticMeshActor(Actor("Test", mb.createBox(3f, 3f, 3f, Material(ColorAttribute(ColorAttribute.Diffuse, 1f, 0f, 0f, 1f)), Usage.Position.toLong() or Usage.Normal.toLong()), RigidBodyInfo(0f, btBoxShape(Vector3(1.5f, 1.5f, 1.5f)))))
    test.rigidbody?.translate(Vector3(0f, 1.5f, 10f))

    val wheelFL = StaticMeshActor(Actor("WheelFL", ModelLoader.loadG3DFromFile("tesla/wheel.g3db".toInternalFile()), null))
    val wheelFR = StaticMeshActor(Actor("WheelFR", ModelLoader.loadG3DFromFile("tesla/wheel.g3db".toInternalFile()), null))
    val wheelRL = StaticMeshActor(Actor("WheelRL", ModelLoader.loadG3DFromFile("tesla/wheel.g3db".toInternalFile()), null))
    val wheelRR = StaticMeshActor(Actor("WheelRR", ModelLoader.loadG3DFromFile("tesla/wheel.g3db".toInternalFile()), null))

    val carModel = ModelLoader.loadG3DFromFile("tesla/tesla.g3db".toInternalFile())
    VehicleActor(Actor("Vehicle", carModel, RigidBodyInfo(1765f, btBoxShape(Vector3(1.1f, 0.5f, 2.7f)))), mutableListOf(wheelFL, wheelFR, wheelRL, wheelRR))
}
