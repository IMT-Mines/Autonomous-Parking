package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.DebugDrawer
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw


class Physics {
    companion object {
        lateinit var collisionConfig: btDefaultCollisionConfiguration
        lateinit var dispatcher: btCollisionDispatcher
        lateinit var broadphase: btDbvtBroadphase
        lateinit var solver: btSequentialImpulseConstraintSolver
        lateinit var physicsScene: btDiscreteDynamicsWorld
        lateinit var debugDrawer: DebugDrawer

        fun init() {
            collisionConfig = btDefaultCollisionConfiguration()
            dispatcher = btCollisionDispatcher(collisionConfig)
            broadphase = btDbvtBroadphase()
            solver = btSequentialImpulseConstraintSolver()
            physicsScene = btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfig)
            physicsScene.gravity = Vector3(0f, -9.81f, 0f)

            debugDrawer = DebugDrawer()
            debugDrawer.debugMode = btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE
            physicsScene.debugDrawer = debugDrawer
        }

        fun step(delta: Float){
            val timeStep = (1 / 30f).coerceAtMost(delta)
            physicsScene.stepSimulation(timeStep, 5, 1 / 100f)
        }

        fun addRigidBody(rigidBody: btRigidBody) {
            physicsScene.addRigidBody(rigidBody)
        }

        fun dispose(){
            solver.dispose()
            physicsScene.dispose()
        }
    }
}
