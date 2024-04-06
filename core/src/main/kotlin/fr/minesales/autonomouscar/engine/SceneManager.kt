package fr.minesales.autonomouscar.engine

import fr.minesales.autonomouscar.engine.base.BaseActor
import fr.minesales.autonomouscar.engine.base.Scene
import kotlin.reflect.KFunction1

class SceneManager {
    companion object{
        var currentScene: Scene? = null

        private var loadableScene: KFunction1<Scene, Unit>? = null

        fun loadScene(sceneDescriptor: KFunction1<Scene, Unit>) {
            if(currentScene == null)
                load(sceneDescriptor)
            else
                loadableScene = sceneDescriptor
        }

        fun onEndOfFrame(){
            load(loadableScene ?: return)
            loadableScene = null
        }

        private fun load(sceneDescriptor: KFunction1<Scene, Unit>) {
            disposeScene()
            currentScene = Scene()
            currentScene?.init(sceneDescriptor)
        }

        fun registerSceneActor(actor: BaseActor) {
            currentScene?.actors?.add(actor)
        }

        private fun disposeScene() {
            currentScene?.actors?.forEach{
                it.actor.model?.model?.dispose()
            }
        }
    }
}
