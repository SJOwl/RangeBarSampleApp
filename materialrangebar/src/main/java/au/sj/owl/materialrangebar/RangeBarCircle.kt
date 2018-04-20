package au.sj.owl.rangebarsampleapp.materialrangebar

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Rect
import android.view.animation.DecelerateInterpolator

/**
 * thumb circle
 * draw itself
 */
class RangeBarCircle(var parent: MaterialRangeBar) {

    var animationDurationMS = 1000

    // range 0..1
    var pos: Float = 0F
    /**
     * size and position
     */
    var color = Color.parseColor("#f000fa")
        set(value) {
            field = value
            paint.color = value
        }

    var posx = 0F // 0.5 of canvas
        set(value) {
            field = if (value < minx) minx
            else if (value > maxx) maxx
            else value
        }
    var sizeFree: Float = 60F
        set(value) {
            field = value
            size = sizeFree
        }
    var sizePressed: Float = 120F
    var posy = 0F // 0.5 of canvas
    var minx = 0F
    var maxx = 100000F

    /**
     * shadow
     */
    var shadowColor = Color.parseColor("#90000000")
        set(value) {
            field = value
            paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, shadowColor)
        }
    var shadowRadius = 10F
        set(value) {
            field = value
            paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, shadowColor)
        }
    var shadowDX = 3F
        set(value) {
            field = value
            paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, shadowColor)
        }
    var shadowDY = 3F
        set(value) {
            field = value
            paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, shadowColor)
        }

    fun setRange(min: Int,
                 max: Int) {
        minx = min.toFloat()
        maxx = max.toFloat()
    }

    val paint = Paint()
    private var size = sizeFree
    private var rect = Rect()
    private var isDragging = false

    init {
        paint.style = Style.FILL
        paint.isAntiAlias = true
        paint.color = color
        paint.setShadowLayer(shadowRadius, shadowDX, shadowDY, shadowColor)
    }

    fun draw(canvas: Canvas?) {
        canvas!!.drawCircle(posx, posy, size, paint)
    }

    fun touchDown(tx: Int,
                  ty: Int,
                  rect: Rect) {
        //        defineRect()
        this.rect = rect
        if (this.rect.contains(tx, ty)) {
            isDragging = true
            posx = tx.toFloat()
            val animator = ValueAnimator.ofFloat(sizeFree, sizePressed)
            animator.duration = animationDurationMS.toLong()
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation ->
                size = animation!!.animatedValue as Float
                parent.invalidate()
            }
            animator.start()
        }
    }

    fun touchUp(tx: Int,
                ty: Int) {
        if (isDragging) {
            isDragging = false
            val animator = ValueAnimator.ofFloat(size, sizeFree)
            animator.duration = animationDurationMS.toLong()
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation ->
                size = animation!!.getAnimatedValue() as Float
                parent.invalidate()
            }
            animator.start()
        }
    }

    fun touchMove(tx: Int,
                  ty: Int) {
        defineRect()
        if (isDragging) {
            posx = tx.toFloat()
        }
    }

    fun defineRect() {
        rect.left = (posx - size / 2).toInt()
        rect.top = (posy - size / 2).toInt()
        rect.right = (posx + size / 2).toInt()
        rect.bottom = (posy + size / 2).toInt()
    }
}