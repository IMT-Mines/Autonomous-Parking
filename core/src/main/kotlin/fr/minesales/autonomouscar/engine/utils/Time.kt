package fr.minesales.autonomouscar.engine.utils

class Time {
    companion object {
        var time: Float = 0f
        var timeScale: Float = 1f
        var deltaTime: Float = 0f
        var unscaledDeltaTime: Float = 0f
        var fixedDeltaTime: Float = 0f

        fun setTimings(delta: Float) {
            time += delta
            deltaTime = delta * timeScale
            unscaledDeltaTime = delta
        }
    }
}
