package fr.minesales.autonomouscar.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Vector3
import fr.minesales.autonomouscar.engine.SceneManager
import fr.minesales.autonomouscar.engine.base.BaseActor
import fr.minesales.autonomouscar.game.scenes.parking

class VehicleActor(name: String, model: ModelInstance) : BaseActor(name, model) {
    override fun start() {
    }

    override fun update() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE))
            SceneManager.loadScene(::parking)
    }
}
