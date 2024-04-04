package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.graphics.g3d.Environment
import fr.minesales.autonomouscar.engine.base.BaseActor

class Scene {
    val actors: MutableList<BaseActor> = mutableListOf()
    val environment: Environment = Environment()

    fun addSceneActor(actor: BaseActor) {
        actors.add(actor)
    }
}
