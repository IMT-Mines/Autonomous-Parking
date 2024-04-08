package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.physics.bullet.collision.btCollisionShape

data class RigidBodyInfo(val mass: Float, val collisionShape: btCollisionShape)
