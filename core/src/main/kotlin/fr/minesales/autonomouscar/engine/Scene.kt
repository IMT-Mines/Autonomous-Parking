package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g3d.Environment
import fr.minesales.autonomouscar.Screen
import fr.minesales.autonomouscar.engine.base.BaseActor

class Scene {
    val actors: MutableList<BaseActor> = mutableListOf()
    val environment: Environment = Environment()

    private var camera: Camera? = null

    var mainCamera: Camera?
        get() = camera
        set(value) {
            camera = value
            Screen.viewport.camera = value
        }

    fun init(block: (Scene) -> Unit): Scene {
        block(this)
        actors.forEach{ it.start() }
        return this
    }
}
