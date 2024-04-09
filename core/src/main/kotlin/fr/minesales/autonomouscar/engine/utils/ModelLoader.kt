package fr.minesales.autonomouscar.engine.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.utils.UBJsonReader
import fr.minesales.autonomouscar.engine.base.Actor
import ktx.assets.toInternalFile
import net.mgsx.gltf.loaders.gltf.GLTFLoader
import net.mgsx.gltf.scene3d.scene.SceneAsset

class ModelLoader {
    companion object {
        fun loadGLTFfromFile(file: FileHandle): Model {
           return GLTFLoader().load(file).scene.model
        }

        fun loadG3DFromFile(file: FileHandle): Model {
            val model = G3dModelLoader(UBJsonReader()).loadModel(Gdx.files.internal(file.path()))
            return model
        }

        fun createActorFromG3DFile(file: FileHandle): Actor {
            val model = G3dModelLoader(UBJsonReader()).loadModel(Gdx.files.internal(file.path()))
            return Actor(file.nameWithoutExtension(), model)
        }
    }
}
