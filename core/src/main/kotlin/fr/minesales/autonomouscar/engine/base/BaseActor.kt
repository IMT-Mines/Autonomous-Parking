package fr.minesales.autonomouscar.engine.base

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import fr.minesales.autonomouscar.Screen

abstract class BaseActor(val actor: Actor) {

    constructor(name: String, model: ModelInstance? = null) : this(Actor(name, model))

    init {
        Screen.currentScene.addSceneActor(this)
    }

    var enabled: Boolean = true

    var position: Vector3
        get() = actor.model?.transform?.getTranslation(Vector3()) ?: Vector3.Zero
        set(value) {
            actor.model?.transform?.setToTranslation(value)
        }

    var rotation: Quaternion
        get() = actor.model?.transform?.getRotation(Quaternion()) ?: Quaternion()
        set(value) {
            actor.model?.transform?.set(value)
        }

    var scale: Vector3
        get() = actor.model?.transform?.getScale(Vector3()) ?: Vector3(1f, 1f, 1f)
        set(value) {
            actor.model?.transform?.scale(value.x, value.y, value.z)
        }

    abstract fun start()
    abstract fun update()
    open fun gui() {}
}
