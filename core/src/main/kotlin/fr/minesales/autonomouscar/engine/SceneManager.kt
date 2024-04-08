package fr.minesales.autonomouscar.engine

import fr.minesales.autonomouscar.engine.base.BaseActor

class SceneManager {
    companion object{
        var currentScene: Scene? = null

        private var loadableScene: ((Scene) -> Unit)? = null

        fun loadScene(sceneDescriptor: (Scene) -> Unit) {
            if(currentScene == null)
                load(sceneDescriptor)
            else
                loadableScene = sceneDescriptor
        }

        fun onEndOfFrame(){
            load(loadableScene ?: return)
            loadableScene = null
        }

        private fun load(sceneDescriptor: (Scene) -> Unit) {
            disposeScene()
            currentScene = Scene()
            currentScene?.init(sceneDescriptor)
        }

        fun registerSceneActor(actor: BaseActor) {
            currentScene?.actors?.add(actor)
        }

        private fun disposeScene() {
            Physics.dispose()
            Physics.init()

            currentScene?.actors?.forEach{
                it.dispose()
            }
        }
    }
}
