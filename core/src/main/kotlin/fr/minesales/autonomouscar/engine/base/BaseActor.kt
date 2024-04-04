package fr.minesales.autonomouscar.engine.base

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import fr.minesales.autonomouscar.engine.Renderer

abstract class BaseActor(var name: String, var model: ModelInstance?) {
    init {
        Renderer.currentScene.addSceneActor(this)
    }

    var enabled: Boolean = true

    var position: Vector3
        get() = Vector3.Zero
        set(value) {}

    var rotation: Quaternion
        get() = Quaternion()
        set(value) {}

    var scale: Vector3
        get() = Vector3.Zero
        set(value) {}

    abstract fun start()
    abstract fun update()
    open fun gui() {}
}
