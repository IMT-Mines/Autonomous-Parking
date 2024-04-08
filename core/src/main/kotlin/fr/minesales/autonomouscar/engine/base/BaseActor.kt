package fr.minesales.autonomouscar.engine.base

import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody
import com.badlogic.gdx.utils.Disposable
import fr.minesales.autonomouscar.engine.Physics
import fr.minesales.autonomouscar.engine.RigidBodyMotionState
import fr.minesales.autonomouscar.engine.SceneManager
import imgui.type.ImBoolean

abstract class BaseActor private constructor(val name: String, actor: Actor) : ModelInstance(actor.model ?: Model()), Disposable {

    constructor(actor: Actor) : this(actor.name, actor)

    var enabled: ImBoolean = ImBoolean(true)
    var rigidbody: btRigidBody? = null

    private lateinit var motionState: RigidBodyMotionState

    init {
        SceneManager.registerSceneActor(this)

        if (model != null) {
            val shape = actor.rigidbodyInfo?.collisionShape
            val mass = actor.rigidbodyInfo?.mass ?: 0f
            val localInertia = Vector3(0f, 0f, 0f)

            if (mass > 0f){
                shape?.calculateLocalInertia(mass, localInertia)
            }

            if (shape != null) {
                motionState = RigidBodyMotionState(transform)
                rigidbody = btRigidBody(btRigidBody.btRigidBodyConstructionInfo(mass, motionState, shape, localInertia))
                rigidbody?.worldTransform = transform
                rigidbody?.collisionFlags = rigidbody!!.collisionFlags and btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK
                Physics.addRigidBody(rigidbody!!)
            }
        }
    }

    abstract fun start()
    abstract fun update()
    open fun gui() {}

    override fun dispose() {
        model?.dispose()
        rigidbody?.dispose()
    }
}
