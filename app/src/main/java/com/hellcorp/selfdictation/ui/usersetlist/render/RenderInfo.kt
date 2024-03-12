package com.hellcorp.selfdictation.ui.usersetlist.render

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.opengl.GLES20.GL_NEAREST
import android.opengl.GLES20.GL_TEXTURE_2D
import android.opengl.GLES20.GL_TEXTURE_MAG_FILTER
import android.opengl.GLES20.GL_TEXTURE_MIN_FILTER
import android.opengl.GLES20.glBindTexture
import android.opengl.GLES20.glDeleteTextures
import android.opengl.GLES20.glGenTextures
import android.opengl.GLES20.glTexParameteri
import android.opengl.GLUtils
import android.view.View
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class RenderInfo(
    private var particleSize: Int
) {

    var columnCount = 0
    var rowCount = 0
    var textureLeft = 0f
    var textureTop = 0f

    private var sourceBitmap: Bitmap? = null

    var textureId = 0

    var particlesIndicesBuffer: FloatBuffer? = null

    var animationStartTime: Long = -1

    var canBeRendered = true

    var isReadyForRender = true
    private var isTextureLoaded = false


    fun renderInfo(particleSize: Int) {
        this.particleSize = particleSize
    }

    fun loadTextureIfNeeded() {
        if (isTextureLoaded) {
            return
        }
        val sourceBitmap = sourceBitmap
        check(!(sourceBitmap == null || sourceBitmap.isRecycled)) { "Source bitmap can't be used: null or recycled." }
        val textureHandle = IntArray(1)
        glGenTextures(1, textureHandle, 0)
        if (textureHandle[0] != 0) {
            glBindTexture(GL_TEXTURE_2D, textureHandle[0])
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
            GLUtils.texImage2D(GL_TEXTURE_2D, 0, sourceBitmap, 0)
            this.sourceBitmap = null
        }
        if (textureHandle[0] == 0) {
            throw RuntimeException("Error loading texture.")
        }
        textureId = textureHandle[0]
        isTextureLoaded = true
    }

    fun composeView(view: View?, offset: IntArray?) {
        if (isReadyForRender) {
            return
        }

        val viewVisibleRect = Rect()
        val isViewVisible = view!!.getLocalVisibleRect(viewVisibleRect)

        if (!isViewVisible || viewVisibleRect.width() == 0 || viewVisibleRect.height() == 0) {
            canBeRendered = false
            return
        }

        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)

        columnCount = viewVisibleRect.width() / particleSize
        rowCount = viewVisibleRect.height() / particleSize
        textureLeft = (viewLocation[0] + viewVisibleRect.left).toFloat()
        textureTop = (viewLocation[1] + viewVisibleRect.top).toFloat()

        if (offset != null && offset.size >= 2) {
            textureLeft += offset[0]
            textureTop += offset[1]
        }

        val executorService = Executors.newFixedThreadPool(2)

        executorService.submit {
            val viewBitmap = Bitmap.createBitmap(
                viewVisibleRect.width(),
                viewVisibleRect.height(),
                Bitmap.Config.ARGB_8888
            )
            val c = Canvas(viewBitmap)
            c.translate(-viewVisibleRect.left.toFloat(), -viewVisibleRect.top.toFloat())
            view.draw(c)
            sourceBitmap = viewBitmap
        }

        executorService.submit {
            val particlesCount = columnCount * rowCount
            val particlesIndices = FloatArray(particlesCount)
            for (i in 0 until particlesCount) {
                particlesIndices[i] = i.toFloat()
            }
            particlesIndicesBuffer = createFloatBuffer(particlesIndicesBuffer, particlesIndices)
        }

        executorService.shutdown()

        try {
            val terminated = executorService.awaitTermination(1, TimeUnit.MINUTES)
            if (!terminated) {
                executorService.shutdownNow()
            }
        } catch (ie: InterruptedException) {
            ie.printStackTrace()
        }

        isReadyForRender = true
    }

    fun createFloatBuffer(buffer: FloatBuffer?, data: FloatArray): FloatBuffer {
        val capacity = data.size * java.lang.Float.SIZE / java.lang.Byte.SIZE

        val floatBuffer = buffer ?: ByteBuffer.allocateDirect(capacity)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()

        floatBuffer.clear()
        floatBuffer.put(data)
        floatBuffer.flip()

        return floatBuffer
    }

    fun recycle() {
        val textureHandle = intArrayOf(textureId)
        glDeleteTextures(1, textureHandle, 0)
    }
}