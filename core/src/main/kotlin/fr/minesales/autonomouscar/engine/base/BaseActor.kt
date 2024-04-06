package fr.minesales.autonomouscar.engine.base

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Matrix4
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.engine.SceneManager
import imgui.type.ImBoolean

abstract class BaseActor(val actor: Actor) {

    constructor(name: String, model: ModelInstance? = null) : this(Actor(name, model))

    init {
        SceneManager.registerSceneActor(this)
    }

    var enabled: ImBoolean = ImBoolean(true)

    var transform: Matrix4
        get() = actor.model?.transform ?: Matrix4()
        set(value) {
            actor.model?.transform?.set(value)
        }

    abstract fun start()
    abstract fun update()
    open fun gui() {}
}
