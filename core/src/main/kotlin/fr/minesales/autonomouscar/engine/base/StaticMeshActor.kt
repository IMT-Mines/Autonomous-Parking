package fr.minesales.autonomouscar.engine.base

import com.badlogic.gdx.graphics.g3d.ModelInstance

class StaticMeshActor(actor: Actor) : BaseActor(actor) {

    constructor(name: String, model: ModelInstance? = null) : this(Actor(name, model))

    override fun start() {}

    override fun update() {}
}
