package fr.minesales.autonomouscar.engine.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.utils.UBJsonReader
import fr.minesales.autonomouscar.engine.base.Actor

class ModelLoader {
    companion object {
        fun loadFromFile(file: FileHandle): Model {
            val model = G3dModelLoader(UBJsonReader()).loadModel(Gdx.files.internal(file.path()))
            return model
        }

        fun createActorFromFile(file: FileHandle): Actor {
            val model = G3dModelLoader(UBJsonReader()).loadModel(Gdx.files.internal(file.path()))
            return Actor(file.nameWithoutExtension(), model)
        }
    }
}
