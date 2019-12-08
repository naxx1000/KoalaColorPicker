package com.rakiwow.koalacolorpicker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlin.math.max
import kotlin.math.min

class ColorPickerView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(ctx, attrs, defStyleAttr) {

    private val paint: Paint = Paint()
    private var selectedColor: Int = 0
    private var colorListener: OnColorChosenListener? = null
    private var colorSpectrumArray = ArrayList<Int>()
    private var lineHeight: Float = 0f
    private var circleYPos: Int = 0
    private var circleSize: Float = 0f
    private var circleRadius: Float = 0f
    private var quarterHeight: Int = 0
    private var colorSpectrumXStart: Float = 0f
    private var cursor: Int = -1

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if(cursor < 0){
            cursor = w/2
        }
        quarterHeight = h/4
        circleSize = ((quarterHeight * 3)/2).toFloat()
        circleYPos = circleSize.toInt()
        val arr = ArrayList<Int>(w)
        val huePerTick: Float = 1530f / w
        var red = 255f
        var green = 0f
        var blue = 0f


        for (i in 0 until w) {
            if (red >= 255f && blue == 0f && green < 255f) {
                red = 255f
                green += huePerTick
                if(green >= 255f && red > 0) green = 255f
            } else if (green >= 255f && red > 0f) {
                green = 255f
                red -= huePerTick
                if(red <= 0f && blue < 255f) red = 0f
            } else if (red <= 0f && blue < 255f) {
                red = 0f
                blue += huePerTick
                if(blue >= 255f && green > 0f) blue = 255f
            } else if (blue >= 255f && green > 0f) {
                blue = 255f
                green -= huePerTick
                if(green <= 0f && red < 255f) green = 0f
            } else if (green <= 0f && red < 255f) {
                green = 0f
                red += huePerTick
                if(red >= 255f) red = 255f
            } else if (red >= 255f) {
                red = 255f
                blue -= huePerTick
            }
            arr.add(Color.rgb(red.toInt(), green.toInt(), blue.toInt()))
            colorSpectrumArray = arr
        }

        selectedColor = colorSpectrumArray[cursor]
        colorListener?.onColorChoose(selectedColor, cursor)
    }

    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until colorSpectrumArray.size){
            paint.color = colorSpectrumArray[i]
            paint.strokeWidth = 0f
            canvas?.drawLine(i.toFloat(), colorSpectrumXStart, i.toFloat(), height.toFloat(), paint)
        }
        paint.color = Color.BLACK
        paint.strokeWidth = 10f
        canvas?.drawLine(cursor.toFloat(), lineHeight, cursor.toFloat(), height.toFloat(), paint)
        if(!colorSpectrumArray.isEmpty()) paint.color = colorSpectrumArray[cursor]
        canvas?.drawCircle(cursor.toFloat(), circleYPos.toFloat(), circleRadius, paint)
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_MOVE || event?.action == MotionEvent.ACTION_DOWN){
            cursor = event.x.toInt()
            circleRadius = circleSize
            if (cursor < 0) cursor = 0
            if (cursor >= colorSpectrumArray.size) cursor = colorSpectrumArray.size - 1
            colorSpectrumXStart = (quarterHeight * 3).toFloat()
            lineHeight = (quarterHeight * 3).toFloat()
            invalidate()
        }
        if(event?.action == MotionEvent.ACTION_UP){
            circleRadius = 0f
            colorSpectrumXStart = 0f
            lineHeight = 0f
            selectedColor = colorSpectrumArray[cursor]
            colorListener?.onColorChoose(selectedColor, cursor)
            invalidate()
        }
        return true
    }

    fun setOnColorChosenListener(listener: OnColorChosenListener){
        colorListener = listener
    }

    interface OnColorChosenListener{
        fun onColorChoose(color: Int, cursorPosition: Int)
    }

    fun setCursorPos(pos: Int?) : ColorPickerView{
        if(pos != null){
            cursor = pos
            invalidate()
        }
        return this
    }
}