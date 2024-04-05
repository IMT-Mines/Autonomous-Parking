@file:JvmName("Lwjgl3Launcher")

package fr.minesales.autonomouscar.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import fr.minesales.autonomouscar.AutonomousParking

/** Launches the desktop (LWJGL3) application. */
fun main() {
    if (StartupHelper.startNewJvmIfRequired())
      return
    Lwjgl3Application(AutonomousParking(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("autonomous-parking")
        setWindowedMode(1280, 720)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
