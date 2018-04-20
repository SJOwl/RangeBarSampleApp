package au.sj.owl.rangebarsampleapp.materialrangebar

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style

class RangeBarLine {
    var xBegin = 0
    var xEnd = 200
    var posy = 0
    var color = Color.parseColor("#fa0000")
        set(value) {
            field = value
            paint.color = value
        }
    var thickness = 10
        set(value) {
            field = value
            paint.strokeWidth = thickness.toFloat()
        }
    var shadowRadius = 0F
        set(value) {
            field = value
            paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, Color.parseColor(shadowColor))
        }
    var shadowDX = 3F
        set(value) {
            field = value
            paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, Color.parseColor(shadowColor))
        }
    var shadowDY = 3F
        set(value) {
            field = value
            paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, Color.parseColor(shadowColor))
        }
    var shadowColor = "#90000000"
        set(value) {
            field = value
            paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, Color.parseColor(shadowColor))
        }

    var paint = Paint()

    init {
        paint.style = Style.FILL
        paint.isAntiAlias = true
        paint.color = color
        paint.strokeWidth = thickness.toFloat()
        paint.strokeCap = Paint.Cap.ROUND
        paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, Color.parseColor(shadowColor))
    }

    fun setRange(begin: Int,
                 end: Int) {
        xBegin = begin
        xEnd = end
    }

    fun draw(canvas: Canvas) {
        canvas.drawLine(xBegin.toFloat(), posy.toFloat(), xEnd.toFloat(), posy.toFloat(), paint)
    }
}