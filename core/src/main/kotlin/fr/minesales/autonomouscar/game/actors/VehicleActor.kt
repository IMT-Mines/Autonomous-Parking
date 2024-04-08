package fr.minesales.autonomouscar.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector3
import fr.minesales.autonomouscar.engine.SceneManager
import fr.minesales.autonomouscar.engine.base.Actor
import fr.minesales.autonomouscar.engine.base.BaseActor
import fr.minesales.autonomouscar.game.scenes.parking

class VehicleActor(actor: Actor) : BaseActor(actor) {
    override fun start() {
        transform.setTranslation(Vector3(0f, 5f, 0f))
        rigidbody?.proceedToTransform(transform)
    }

    override fun update() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE))
            SceneManager.loadScene(::parking)
    }
}
