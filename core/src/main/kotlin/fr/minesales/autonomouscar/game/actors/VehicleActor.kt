package fr.minesales.autonomouscar.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback
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


class VehicleActor(actor: Actor, val wheelModels: MutableList<ModelInstance>) : BaseActor(actor) {
    private lateinit var vehicleRaycaster: btDefaultVehicleRaycaster
    private lateinit var tuning: btVehicleTuning
    private lateinit var vehicle: btRaycastVehicle

    private var steer = 0f
    private var accelerate = 0f

    private var hits: Array<ClosestRayResultCallback?> = arrayOfNulls(8)
    private val RAY_COUNT = 8

    override fun start() {
        transform.set(Vector3(0f, 5f, 5f), Quaternion().setEulerAngles(180f, 0f, 0f))
        rigidbody?.proceedToTransform(transform)

        vehicleRaycaster = btDefaultVehicleRaycaster(Physics.physicsScene)
        tuning = btVehicleTuning()
        tuning.suspensionStiffness = 15f
        tuning.maxSuspensionTravelCm = 30f
        vehicle = btRaycastVehicle(tuning, rigidbody, vehicleRaycaster)

        rigidbody?.activationState = Collision.DISABLE_DEACTIVATION
        vehicle.setCoordinateSystem(0, 1, 2)

        val wheelConnectionPointHeight = 0.1f
        val wheelRadius = 0.33f
        val wheelDirection = Vector3(0f, -1f, 0f)
        val wheelAxle = Vector3(-1f, 0f, 0f)
        vehicle.addWheel(Vector3(0.9f, wheelConnectionPointHeight, 1.75f), wheelDirection, wheelAxle, 0.6f, wheelRadius, tuning, true)
        vehicle.addWheel(Vector3(-0.9f, wheelConnectionPointHeight, 1.75f), wheelDirection, wheelAxle, 0.6f, wheelRadius, tuning, true)
        vehicle.addWheel(Vector3(0.95f, wheelConnectionPointHeight, -1.45f), wheelDirection, wheelAxle, 0.6f, wheelRadius, tuning, false)
        vehicle.addWheel(Vector3(-0.95f, wheelConnectionPointHeight, -1.45f), wheelDirection, wheelAxle, 0.6f, wheelRadius, tuning, false)
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

        for (i in 0..<RAY_COUNT){
            val rayDir = Vector3(0f, 0f, 1f).rotate(Vector3(0f, 1f, 0f), i * (360f / RAY_COUNT)).scl(5f).rot(transform)
            hits[i] = Physics.raycast(transform.getTranslation(Vector3()), transform.getTranslation(Vector3()).add(rayDir))
        }

        for (i in wheelModels.indices) {
            wheelModels[i].transform.set(vehicle.getWheelTransformWS(i).rotate(Quaternion().setEulerAngles(180f * (i % 2), 0f, 0f)))
        }
    }

    override fun gui() {
        ImGui.begin("Vehicle")
        ImGui.text("Speed: %.2f".format(abs(vehicle.currentSpeedKmHour)))
        ImGui.text("Steer: %.2f".format(steer))
        ImGui.text("Accelerate: %.2f".format(accelerate))
        ImGui.end()

        for (i in 0..<RAY_COUNT){
            Physics.drawRayDebug(transform.getTranslation(Vector3()), transform.getTranslation(Vector3()).add(Vector3(0f, 0f, -5f).rot(transform)), hits[i])
        }
    }
}
