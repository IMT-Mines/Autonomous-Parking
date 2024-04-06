package fr.minesales.autonomouscar.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import fr.minesales.autonomouscar.engine.base.Scene
import fr.minesales.autonomouscar.engine.utils.ModelLoader
import fr.minesales.autonomouscar.game.actors.CameraControllerActor
import fr.minesales.autonomouscar.game.actors.VehicleActor
import ktx.assets.toInternalFile

fun parking(scene: Scene){
    scene.environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f))
    scene.environment.add(DirectionalLight().set(1f, 1f, 1f, 0f, -1f, 0f))

    CameraControllerActor("MainCamera", PerspectiveCamera(60f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    VehicleActor("Vehicle", ModelLoader.loadFromFile("sedan/sedan.g3db".toInternalFile()))
}
