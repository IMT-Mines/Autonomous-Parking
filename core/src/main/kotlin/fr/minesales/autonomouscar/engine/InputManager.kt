package fr.minesales.autonomouscar.engine

import com.badlogic.gdx.InputProcessor

class InputManager : InputProcessor {

    var cameraSpeed = 10f

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        cameraSpeed -= amountY
        cameraSpeed = cameraSpeed.coerceAtLeast(0.1f)
        return true
    }

    companion object {

        @Volatile
        private var instance: InputManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: InputManager().also { instance = it }
            }
    }
}
