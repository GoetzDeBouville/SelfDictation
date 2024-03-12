package com.hellcorp.selfdictation.ui.usersetlist.render

import android.R.attr.duration
import android.content.Context
import android.opengl.GLES20.GL_BLEND
import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_FRAGMENT_SHADER
import android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA
import android.opengl.GLES20.GL_POINTS
import android.opengl.GLES20.GL_SRC_ALPHA
import android.opengl.GLES20.GL_TEXTURE0
import android.opengl.GLES20.GL_TEXTURE_2D
import android.opengl.GLES20.GL_VERTEX_SHADER
import android.opengl.GLES20.glActiveTexture
import android.opengl.GLES20.glBindTexture
import android.opengl.GLES20.glBlendFunc
import android.opengl.GLES20.glClear
import android.opengl.GLES20.glDisableVertexAttribArray
import android.opengl.GLES20.glDrawArrays
import android.opengl.GLES20.glEnable
import android.opengl.GLES20.glEnableVertexAttribArray
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.GLES20.glGetUniformLocation
import android.opengl.GLES20.glUniform1f
import android.opengl.GLES20.glUniform1i
import android.opengl.GLES20.glUseProgram
import android.opengl.GLES20.glVertexAttribPointer
import android.opengl.GLES20.glViewport
import android.opengl.GLSurfaceView
import android.view.View
import com.hellcorp.selfdictation.R
import java.util.concurrent.ConcurrentLinkedQueue
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class DustRenderer(
    private val context: Context
) : GLSurfaceView.Renderer {
    private var particlesProgramId: Int = 0
    private var aParticleIndex: Int = 0
    private val particleSize = 5
    private val renderInfos: ConcurrentLinkedQueue<RenderInfo> = ConcurrentLinkedQueue()

    override fun onSurfaceCreated(arg0: GL10?, arg1: EGLConfig?) {
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        val particlesVertexShaderId: Int =
            ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.particles_vert)
        val particlesFragmentShaderId: Int =
            ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.particles_frag)
        particlesProgramId =
            ShaderUtils.createProgram(particlesVertexShaderId, particlesFragmentShaderId)

        aParticleIndex = glGetAttribLocation(particlesProgramId, "a_ParticleIndex")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        glUseProgram(particlesProgramId)
        glUniform1f("u_ViewportWidth", width.toFloat())
        glUniform1f("u_ViewportHeight", height.toFloat())
    }

    private fun glUniform1f(s: String, param: Float) {
        val location = glGetUniformLocation(particlesProgramId, s)
        glUniform1f(location, param)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        if (renderInfos.isEmpty()) {
            return
        }

        glUseProgram(particlesProgramId)

        glUniform1f("u_AnimationDuration", duration.toFloat())
        glUniform1f("u_ParticleSize", particleSize.toFloat())

        val currentTime = System.currentTimeMillis()

        for (renderInfo in renderInfos) {
            if (!renderInfo.canBeRendered) {
                renderInfos.remove(renderInfo)
                continue
            }

            if (!renderInfo.isReadyForRender) {
                continue
            }
            renderInfo.loadTextureIfNeeded()
            val isFrameDrawn: Boolean = drawFrame(renderInfo, currentTime)
            if (!isFrameDrawn) {
                renderInfo.recycle()
                renderInfos.remove(renderInfo)
            }
        }
    }

    fun composeView(view: View?, offset: IntArray?) {
        val renderInfo = RenderInfo(particleSize)
        renderInfos.add(renderInfo)
        renderInfo.composeView(view, offset)
    }

    private fun drawFrame(renderInfo: RenderInfo, currentTime: Long): Boolean {
        if (renderInfo.animationStartTime == -1L) {
            renderInfo.animationStartTime = System.currentTimeMillis()
        }
        val elapsedTime = currentTime - renderInfo.animationStartTime
        if (elapsedTime > duration) {
            return false
        }
        val uTexture = glGetUniformLocation(particlesProgramId, "u_Texture")
        glUniform1i(uTexture, 0)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, renderInfo.textureId)
        glUniform1f("u_ElapsedTime", elapsedTime.toFloat())
        glUniform1f("u_TextureWidth", renderInfo.columnCount.toFloat())
        glUniform1f("u_TextureHeight", renderInfo.rowCount.toFloat())
        glUniform1f("u_TextureLeft", renderInfo.textureLeft)
        glUniform1f("u_TextureTop", renderInfo.textureTop)
        glVertexAttribPointer(
            aParticleIndex,
            1,
            GL_FLOAT,
            false,
            0,
            renderInfo.particlesIndicesBuffer
        )
        glEnableVertexAttribArray(aParticleIndex)
        glDrawArrays(GL_POINTS, 0, renderInfo.columnCount * renderInfo.rowCount)
        glDisableVertexAttribArray(aParticleIndex)
        return true
    }
}