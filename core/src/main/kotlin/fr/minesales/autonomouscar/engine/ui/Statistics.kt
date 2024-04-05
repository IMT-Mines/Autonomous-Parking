package fr.minesales.autonomouscar.engine.ui

import com.badlogic.gdx.Gdx
import fr.minesales.autonomouscar.engine.base.Scene
import fr.minesales.autonomouscar.engine.ui.base.BasePanel
import fr.minesales.autonomouscar.engine.utils.Time
import imgui.ImGui

class Statistics: BasePanel(){
    private var timer = 0f
    private var frames = 0f
    private var count = 0
    private var fps = 0

    val FPS_INTERVAL = 1f
    override fun init() {
    }

    override fun draw(scene: Scene) {
        timer += Time.unscaledDeltaTime
        frames += (1f / Time.unscaledDeltaTime).toInt()
        count++

        if (timer > FPS_INTERVAL){
            fps = (frames / count).toInt()
            frames = 0f
            count = 0
            timer = 0f
        }

        ImGui.begin("Statistics")
        ImGui.setWindowPos(Gdx.graphics.width.toFloat() - 250, 50f)
        ImGui.setWindowSize(200f, 100f)
        ImGui.text("FPS: $fps")
        ImGui.end()
    }
}
