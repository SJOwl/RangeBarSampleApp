package au.sj.owl.rangebarsampleapp.materialrangebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import au.sj.owl.materialrangebar.R

class MaterialRangeBar : View {
    // attrs
    var thumbRadiusFree = 30
        set(value) {
            field = value
            thumbLeft.sizeFree = value.toFloat()
            thumbRight.sizeFree = value.toFloat()
        }
    var thumbRadiusActive = 40
        set(value) {
            field = value
            padding = thumbRadiusActive
            thumbLeft.sizePressed = value.toFloat()
            thumbRight.sizePressed = value.toFloat()
        }
    var thumbShadowColor = Color.parseColor("#88000000")
        set(value) {
            field = value
            thumbLeft.shadowColor = value
            thumbRight.shadowColor = value
        }
    var thumbShadowRadius = 10
        set(value) {
            field = value
            thumbLeft.shadowRadius = value.toFloat()
            thumbRight.shadowRadius = value.toFloat()
        }
    var thumbShadowDX = 5
        set(value) {
            field = value
            thumbLeft.shadowDX = value.toFloat()
            thumbRight.shadowDX = value.toFloat()
        }
    var thumbShadowDY = 5
        set(value) {
            field = value
            thumbLeft.shadowDY = value.toFloat()
            thumbRight.shadowDY = value.toFloat()
        }
    var thumbColor = Color.parseColor("#E8E8E8")
        set(value) {
            field = value
            thumbLeft.color = value
            thumbRight.color = value
        }

    var animationDuarionMS = 300
        set(value) {
            field = value
            thumbLeft.animationDurationMS = value
        }

    var rangeOuterThickness = 10
        set(value) {
            field = value
            rangeOuter.thickness = value
        }
    var rangeInnerColor = Color.parseColor("#FF9900")
        set(value) {
            field = value
            rangeOuter.color = value
        }

    var rangeInnerThickness = 15
        set(value) {
            field = value
            rangeInner.thickness = value
        }
    var rangeOuterColor = Color.parseColor("#D1D1D1")
        set(value) {
            field = value
            rangeOuter.color = value
        }

    // in abslolute units (km, kg, ...)
    var minValue = 0
    var maxValue = 100
    var thumbLeftPos = 30F
        set(value) {
            if (field.toInt() == value.toInt()) return
            field = value
            rangeChangedListener?.onRangeChanged(thumbLeftPos.toInt(), thumbRightPos.toInt())
        }
    var thumbRightPos = 70F
        set(value) {
            if (field.toInt() == value.toInt()) return
            field = value
            rangeChangedListener?.onRangeChanged(thumbLeftPos.toInt(), thumbRightPos.toInt())
        }

    // listener
    var rangeChangedListener: RangeChangedListener? = null

    // views
    private var thumbLeft = RangeBarCircle(this)
    private var thumbRight = RangeBarCircle(this)
    private var rangeOuter = RangeBarLine()
    private var rangeInner = RangeBarLine()

    // vars
    private var centerY = 0
    private var padding = thumbRadiusActive
        set(value) {
            field = value + thumbShadowRadius
        }

    init {
        padding = thumbRadiusActive

        initThumb(thumbLeft)
        initThumb(thumbRight)

        rangeInner.xBegin = 0
        rangeInner.xEnd = 700
        rangeInner.color = rangeInnerColor
        rangeInner.thickness = rangeInnerThickness

        rangeOuter.xBegin = 0
        rangeOuter.xEnd = 700
        rangeOuter.color = rangeOuterColor
        rangeOuter.thickness = rangeOuterThickness
    }

    fun initThumb(t: RangeBarCircle) {
        t.sizeFree = thumbRadiusFree.toFloat()
        t.sizePressed = thumbRadiusActive.toFloat()
        t.color = thumbColor
        t.shadowColor = thumbShadowColor
        t.animationDurationMS = animationDuarionMS
        t.shadowRadius = thumbShadowRadius.toFloat()
        t.shadowDX = thumbShadowDX.toFloat()
        t.shadowDY = thumbShadowDY.toFloat()
        setLayerType(LAYER_TYPE_SOFTWARE, t.paint)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet) : super(context, attrs) {
        loadAttrs(attrs)
    }

    constructor(context: Context,
                attrs: AttributeSet,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        loadAttrs(attrs)
    }

    fun loadAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MaterialRangeBar, 0, 0)

        thumbRadiusFree = a.getDimension(R.styleable.MaterialRangeBar_sjmrb_thumbRadiusFree,
                                         thumbRadiusFree.toFloat()).toInt()
        thumbRadiusActive = a.getDimension(R.styleable.MaterialRangeBar_sjmrb_thumbRadiusActive,
                                           thumbRadiusActive.toFloat()).toInt()
        thumbShadowColor = a.getColor(R.styleable.MaterialRangeBar_sjmrb_thumbShadowColor, thumbShadowColor)
        thumbShadowRadius = a.getDimension(R.styleable.MaterialRangeBar_sjmrb_thumbShadowRadius,
                                           thumbShadowRadius.toFloat()).toInt()
        thumbShadowDX = a.getDimension(R.styleable.MaterialRangeBar_sjmrb_thumbShadowDX,
                                       thumbShadowDX.toFloat()).toInt()
        thumbShadowDY = a.getDimension(R.styleable.MaterialRangeBar_sjmrb_thumbShadowDY,
                                       thumbShadowDY.toFloat()).toInt()
        thumbColor = a.getColor(R.styleable.MaterialRangeBar_sjmrb_thumbColor, thumbColor)
        animationDuarionMS = a.getInteger(R.styleable.MaterialRangeBar_sjmrb_animationDuarionMS, animationDuarionMS)
        rangeOuterThickness = a.getDimension(R.styleable.MaterialRangeBar_sjmrb_rangeOuterThickness,
                                             rangeOuterThickness.toFloat()).toInt()
        rangeInnerColor = a.getColor(R.styleable.MaterialRangeBar_sjmrb_rangeInnerColor, rangeInnerColor)
        rangeInnerThickness = a.getDimension(R.styleable.MaterialRangeBar_sjmrb_rangeInnerThickness,
                                             rangeInnerThickness.toFloat()).toInt()
        rangeOuterColor = a.getColor(R.styleable.MaterialRangeBar_sjmrb_rangeOuterColor, rangeOuterColor)
        minValue = a.getInteger(R.styleable.MaterialRangeBar_sjmrb_valueMin, minValue)
        maxValue = a.getInteger(R.styleable.MaterialRangeBar_sjmrb_valueMax, maxValue)
        thumbLeftPos = a.getInteger(R.styleable.MaterialRangeBar_sjmrb_thumbPosLeft, thumbLeftPos.toInt()).toFloat()
        thumbRightPos = a.getInteger(R.styleable.MaterialRangeBar_sjmrb_thumbPosRight,
                                     thumbRightPos.toInt()).toFloat()

        var max = Math.max(thumbRightPos, thumbLeftPos)
        var min = Math.min(thumbRightPos, thumbLeftPos)
        thumbRightPos = max
        thumbLeftPos = min

        var maxf = Math.max(thumbRadiusFree, thumbRadiusActive)
        var minf = Math.min(thumbRadiusFree, thumbRadiusActive)
        thumbRadiusActive = maxf
        thumbRadiusFree = minf

        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int,
                           heightMeasureSpec: Int) {
        val desiredWidth = thumbRadiusActive * 3 + padding * 2
        val desiredHeight = padding * 2

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val width: Int
        val height: Int
        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            width = desiredWidth
        }

        height = desiredHeight


        setMeasuredDimension(width, height)
        centerY = measuredHeight / 2
        rangeOuter.posy = centerY

        rangeInner.posy = centerY

        thumbLeft.posy = centerY.toFloat()
        thumbRight.posy = centerY.toFloat()

    }

    override fun onDraw(canvas: Canvas?) {
        rangeOuter.xBegin = padding
        rangeOuter.xEnd = measuredWidth - padding
        rangeOuter.draw(canvas!!)

        thumbLeft.posx = getXFromPos(thumbLeftPos)
        thumbRight.posx = getXFromPos(thumbRightPos)

        rangeInner.xBegin = thumbLeft.posx.toInt()
        rangeInner.xEnd = thumbRight.posx.toInt()
        rangeInner.draw(canvas)

        thumbLeft.setRange(padding, thumbRight.posx.toInt())
        thumbLeft.draw(canvas)

        thumbRight.setRange(thumbLeft.posx.toInt(), measuredWidth - padding)
        thumbRight.draw(canvas)

        //        Log.d("jsp", "left=$thumbLeftPos, right=$thumbRightPos")
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val action = event!!.action
        val x = (event!!.x).toInt()
        val y = (event!!.y).toInt()
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                thumbLeft.touchDown(x,
                                    y,
                                    Rect(0,
                                         0,
                                         (thumbLeft.posx + (thumbRight.posx - thumbLeft.posx) / 2).toInt(),
                                         measuredHeight))
                thumbRight.touchDown(x,
                                     y,
                                     Rect((thumbRight.posx - (thumbRight.posx - thumbLeft.posx) / 2).toInt(),
                                          0,
                                          measuredWidth,
                                          measuredHeight))
                thumbLeftPos = getPosFromX(thumbLeft.posx)
                thumbRightPos = getPosFromX(thumbRight.posx)
            }

            MotionEvent.ACTION_UP   -> {
                thumbLeft.touchUp(x, y)
                thumbRight.touchUp(x, y)
            }

            MotionEvent.ACTION_MOVE -> {
                // set thumb pos absolute value
                thumbLeft.touchMove(x, y)
                thumbRight.touchMove(x, y)
                thumbLeftPos = getPosFromX(thumbLeft.posx)
                thumbRightPos = getPosFromX(thumbRight.posx)
            }
        }

        invalidate()

        return true
    }

    /**
     * convert position (usual numbers) to position on canvas
     */
    private fun getXFromPos(pos: Float): Float {
        var p = 1.0F * padding * (maxValue - minValue) / (measuredWidth - 2 * padding)
        var k = 1.0F * (maxValue - minValue) / (measuredWidth - 2 * padding)
        return ((p + pos - minValue) / k)
    }

    /**
     * convert position on canvas to value
     */
    private fun getPosFromX(x: Float): Float {
        var p = 1.0F * padding * (maxValue - minValue) / (measuredWidth - 2 * padding)
        var k = 1.0F * (maxValue - minValue) / (measuredWidth - 2 * padding)
        return k * x + minValue - p
    }

    interface RangeChangedListener {
        // todo make lambda here
        fun onRangeChanged(left: Int,
                           right: Int)
    }

    fun setRangeChangeListener(listener: (Int, Int) -> Unit) {
        rangeChangedListener = object : RangeChangedListener {
            override fun onRangeChanged(left: Int,
                                        right: Int) = listener(left, right)

        }
    }

}
