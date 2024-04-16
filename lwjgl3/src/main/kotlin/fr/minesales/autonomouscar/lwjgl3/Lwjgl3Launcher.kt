@file:JvmName("Lwjgl3Launcher")

package fr.minesales.autonomouscar.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3NativesLoader
import com.badlogic.gdx.utils.GdxNativesLoader
import fr.minesales.autonomouscar.AutonomousParking

/** Launches the desktop (LWJGL3) application. */
fun main() {
    if (StartupHelper.startNewJvmIfRequired())
      return
    Lwjgl3NativesLoader.load()
    Lwjgl3Application(AutonomousParking(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("Autonomous Parking")
        setWindowedMode(1280, 720)
        //setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode())
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
