package fr.minesales.autonomouscar.engine.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.profiling.GLProfiler
import fr.minesales.autonomouscar.engine.Scene
import fr.minesales.autonomouscar.engine.ui.base.BasePanel
import fr.minesales.autonomouscar.engine.utils.Time
import imgui.ImGui

class Statistics: BasePanel(){
    private var timer = 0f
    private var frames = 0f
    private var count = 0
    private var fps = 0

    val FPS_INTERVAL = 0.5f

    private val profiler : GLProfiler = GLProfiler(Gdx.graphics)
    private var previousGlCalls = 0
    private var previousDrawCalls = 0
    private var previousVertexCount = 0

    init {
        profiler.enable()
    }

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
        ImGui.text("FPS: $fps")
        ImGui.text("GL Calls: ${profiler.calls - previousGlCalls}")
        ImGui.text("Draw Calls: ${profiler.drawCalls - previousDrawCalls}")
        ImGui.text("Vertex Count: ${profiler.vertexCount.total.toInt() - previousVertexCount}")
        ImGui.end()

        previousGlCalls = profiler.calls
        previousDrawCalls = profiler.drawCalls
        previousVertexCount = profiler.vertexCount.total.toInt()
    }
}
