package fr.minesales.autonomouscar.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.collision.Collision
import com.badlogic.gdx.physics.bullet.dynamics.btDefaultVehicleRaycaster
import com.badlogic.gdx.physics.bullet.dynamics.btRaycastVehicle
import com.badlogic.gdx.physics.bullet.dynamics.btRaycastVehicle.btVehicleTuning
import fr.minesales.autonomouscar.engine.Physics
import fr.minesales.autonomouscar.engine.SceneManager
import fr.minesales.autonomouscar.engine.base.Actor
import fr.minesales.autonomouscar.engine.base.BaseActor
import fr.minesales.autonomouscar.engine.utils.Time
import fr.minesales.autonomouscar.game.scenes.parking
import imgui.ImGui
import kotlin.math.abs


class VehicleActor(actor: Actor) : BaseActor(actor) {
    private lateinit var vehicleRaycaster: btDefaultVehicleRaycaster
    private lateinit var tuning: btVehicleTuning
    private lateinit var vehicle: btRaycastVehicle

    private var steer = 0f
    private var accelerate = 0f

    override fun start() {
        transform.set(Vector3(0f, 5f, 5f), Quaternion().setEulerAngles(180f, 0f, 0f))
        rigidbody?.proceedToTransform(transform)

        vehicleRaycaster = btDefaultVehicleRaycaster(Physics.physicsScene)
        tuning = btVehicleTuning()
        tuning.suspensionStiffness = 15f
        vehicle = btRaycastVehicle(tuning, rigidbody, vehicleRaycaster)

        rigidbody?.activationState = Collision.DISABLE_DEACTIVATION
        vehicle.setCoordinateSystem(0, 1, 2)

        val wheelConnectionPointHeight = 0.3f
        val wheelRadius = 0.6f
        val wheelDirection = Vector3(0f, -1f, 0f)
        val wheelAxle = Vector3(1f, 0f, 0f)
        vehicle.addWheel(Vector3(-1f, wheelConnectionPointHeight, -1.8f), wheelDirection, wheelAxle, 0.6f, wheelRadius, tuning, true)
        vehicle.addWheel(Vector3(1f, wheelConnectionPointHeight, -1.8f), wheelDirection, wheelAxle, 0.6f, wheelRadius, tuning, true)
        vehicle.addWheel(Vector3(-1f, wheelConnectionPointHeight, 1.4f), wheelDirection, wheelAxle, 0.6f, wheelRadius, tuning, false)
        vehicle.addWheel(Vector3(1f, wheelConnectionPointHeight, 1.4f), wheelDirection, wheelAxle, 0.6f, wheelRadius, tuning, false)
        Physics.physicsScene.addVehicle(vehicle)
    }

    override fun update() {
        vehicle.updateVehicle(Time.deltaTime)

        steer = if (Gdx.input.isKeyPressed(Keys.LEFT)){
            MathUtils.lerp(steer, 0.5f, Time.deltaTime * 2)
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            MathUtils.lerp(steer, -0.5f, Time.deltaTime * 2)
        } else {
            MathUtils.lerp(steer, 0f, Time.deltaTime * 3)
        }

        if (Gdx.input.isKeyPressed(Keys.UP)){
            accelerate += Time.deltaTime * 2
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)){
            accelerate -= Time.deltaTime * 2
        }else{
            accelerate = 0f
        }

        accelerate = MathUtils.clamp(accelerate, -1f, 1f)

        vehicle.setSteeringValue(steer, 0)
        vehicle.setSteeringValue(steer, 1)

        vehicle.applyEngineForce(accelerate * 1000f, 0)
        vehicle.applyEngineForce(accelerate * 1000f, 1)
        vehicle.applyEngineForce(accelerate * 1000f, 2)
        vehicle.applyEngineForce(accelerate * 1000f, 3)

        if (Gdx.input.isKeyJustPressed(Keys.SPACE))
            SceneManager.loadScene(::parking)
    }

    override fun gui() {
        ImGui.begin("Vehicle")
        ImGui.text("Speed: %.2f".format(abs(vehicle.currentSpeedKmHour)))
        ImGui.text("Steer: %.2f".format(steer))
        ImGui.text("Accelerate: %.2f".format(accelerate))
        ImGui.end()
    }
}
