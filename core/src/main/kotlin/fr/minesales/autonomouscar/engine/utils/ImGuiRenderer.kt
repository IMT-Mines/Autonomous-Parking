package fr.minesales.autonomouscar.engine.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics
import imgui.ImGui
import imgui.ImGuiIO
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw

class ImGuiRenderer {

    private var tmpProcessor: InputProcessor? = null
    private val imGuiGlfw = ImGuiImplGlfw()
    private val imGuiGl3 = ImGuiImplGl3()

    fun init() {
        val windowHandle = (Gdx.graphics as Lwjgl3Graphics).window.windowHandle
        ImGui.createContext()
        val io: ImGuiIO = ImGui.getIO()
        io.iniFilename = null
        io.fonts.addFontDefault()
        io.fonts.build()
        imGuiGlfw.init(windowHandle, true)
        imGuiGl3.init("#version 150")
    }

    fun render(block: () -> Unit) {
        if (tmpProcessor != null) {
            Gdx.input.inputProcessor = tmpProcessor
            tmpProcessor = null
        }
        imGuiGlfw.newFrame()
        ImGui.newFrame()
        block()
        ImGui.render()
        imGuiGl3.renderDrawData(ImGui.getDrawData())

        if (ImGui.getIO().wantCaptureKeyboard || ImGui.getIO().wantCaptureMouse) {
            tmpProcessor = Gdx.input.inputProcessor
            Gdx.input.inputProcessor = null
        }
    }

    fun dispose() {
        imGuiGl3.dispose()
        imGuiGlfw.dispose()
        ImGui.destroyContext()
    }

}
