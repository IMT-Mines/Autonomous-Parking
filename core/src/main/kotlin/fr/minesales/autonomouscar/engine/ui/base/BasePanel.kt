package fr.minesales.autonomouscar.engine.ui.base

import fr.minesales.autonomouscar.engine.Scene

abstract class BasePanel {
    abstract fun init()
    abstract fun draw(scene: Scene)
}
