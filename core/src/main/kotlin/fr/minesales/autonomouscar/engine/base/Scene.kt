package fr.minesales.autonomouscar.engine.base

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g3d.Environment

abstract class Scene {
    val actors: MutableList<BaseActor> = mutableListOf()
    val environment: Environment = Environment()

    abstract fun init()

    abstract fun dispose()

    abstract fun getCamera(): Camera

    open fun addSceneActor(actor: BaseActor) {
        actors.add(actor)
    }
}
