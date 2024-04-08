package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState

class RigidBodyMotionState(private val transform: Matrix4) : btMotionState() {
    override fun getWorldTransform(worldTransform: Matrix4) {
        worldTransform.set(transform)
    }

    override fun setWorldTransform(worldTransform: Matrix4?) {
        transform.set(worldTransform)
    }
}
