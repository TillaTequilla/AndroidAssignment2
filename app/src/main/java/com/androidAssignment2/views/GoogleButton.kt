package com.androidAssignment2.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.androidAssignment2.R

@SuppressLint("Recycle", "UseCompatLoadingForDrawables")
class GoogleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_WIDTH = 60
        private const val DEFAULT_HEIGHT = 10
        private const val DEFAULT_TEXT_SIZE = 55F
        private const val DEFAULT_ICON_SIZE = 55F
        private const val DEFAULT_BUTTON_COLOR = Color.WHITE
        private const val DEFAULT_TEXT = "GOOGLE"
        private const val DEFAULT_ROUNDING = 14F
        private const val INDENT_TEXT_G_SIGN = 60
    }


    private var text: String? = DEFAULT_TEXT
    private var buttonColor = DEFAULT_BUTTON_COLOR
    private var googleIcon: Drawable = context.getDrawable(R.drawable.icon_google)!!
    private var font = ResourcesCompat.getFont(context, R.font.semi_bold)
    private var textSize = DEFAULT_TEXT_SIZE
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var iconSize = DEFAULT_ICON_SIZE
    private var textLength: Float = 0F
    private var edgeRounding = DEFAULT_ROUNDING

    init {
        paint.isAntiAlias = true
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.GoogleButton)
            text = ta.getString(R.styleable.GoogleButton_text) ?: DEFAULT_TEXT
            buttonColor = ta.getColor(R.styleable.GoogleButton_button_color, DEFAULT_BUTTON_COLOR)
            font = ta.getFont(R.styleable.GoogleButton_fontFamily)
            googleIcon = (ta.getDrawable(R.styleable.GoogleButton_icon)
                ?: context.getDrawable(R.drawable.icon_google))!!
            textSize = ta.getDimension(R.styleable.GoogleButton_textSize, DEFAULT_TEXT_SIZE)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val initWidth = resolveDefaultWidth(widthMeasureSpec)
        val initHeight = resolveDefaultHeight(heightMeasureSpec)

        setMeasuredDimension(initWidth, initHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            drawBackground(canvas)
            drawText(canvas)
            drawG(canvas)
        }
    }

    private fun drawText(canvas: Canvas) {
        paint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            typeface = font
            paint.color = Color.BLACK
            textAlignment = TEXT_ALIGNMENT_CENTER
        }
        paint.textSize = textSize
        val y = (height - paint.descent() - paint.ascent()) / 2
        textLength = paint.measureText(text)
        val x = (width - textLength) / 2.toFloat() + iconSize / 2
        canvas.drawText(
            text!!, x, y, paint
        )
    }

    private fun drawG(canvas: Canvas) {
        iconSize = textSize
        val y: Int = ((height - iconSize) / 2).toInt()
        val x: Int = ((width - iconSize - textLength - INDENT_TEXT_G_SIGN) / 2).toInt()
        googleIcon.setBounds(x, y, (x + iconSize).toInt(), (y + iconSize).toInt())
        googleIcon.draw(canvas)

    }

    private fun drawBackground(canvas: Canvas) {
        paint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            paint.color = buttonColor
        }

        canvas.drawRoundRect(
            0F,
            0F,
            measuredWidth.toFloat(),
            measuredHeight.toFloat(),
            edgeRounding,
            edgeRounding,
            paint
        )

    }

    private fun resolveDefaultHeight(spec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> {
                dpToPx(DEFAULT_HEIGHT).toInt()
            }
            MeasureSpec.AT_MOST -> {
                MeasureSpec.getSize(spec)
            }
            MeasureSpec.EXACTLY -> {
                MeasureSpec.getSize(spec)
            }
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun resolveDefaultWidth(spec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> {
                dpToPx(DEFAULT_WIDTH).toInt()
            }
            MeasureSpec.AT_MOST -> {
                MeasureSpec.getSize(spec)
            }
            MeasureSpec.EXACTLY -> {
                MeasureSpec.getSize(spec)
            }
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun dpToPx(dp: Int): Float {
        return dp * context.resources.displayMetrics.density
    }
}