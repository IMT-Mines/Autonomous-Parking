package fr.minesales.autonomouscar.engine.utils

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader
import fr.minesales.autonomouscar.engine.base.Actor

class ObjImporter {

    companion object {
        fun loadFromFile(file: FileHandle): Model {
            return ObjLoader().loadModel(file)
        }

        fun createActorFromObjFile(file: FileHandle): Actor {
            ObjLoader().loadModel(file).let {
                return Actor(file.nameWithoutExtension(), it)
            }
        }
    }

}
