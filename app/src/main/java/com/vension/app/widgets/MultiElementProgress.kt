package com.vension.app.widgets


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.support.v4.util.ArrayMap
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.jennifer.andy.simpleeyes.utils.DensityUtils
import kotlin.math.min




/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/30 15:30
 * 描  述：自定义圆形菜单
 * ========================================================
 */

class MultiElementProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private var strokeWidth = 20f //画笔宽度
    private var startAngle = -45f //开始角度
    private var centerText = "总资产（元）\n999.99"   //中间文本

    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = this@MultiElementProgress.strokeWidth
        }
    }

    private val rectf by lazy {
        when {
            mWidth > mHeight  -> RectF(mWidth / 2 - mHeight / 2 + strokeWidth / 2, 0f + strokeWidth / 2, mWidth / 2 - mHeight / 2 + mHeight - strokeWidth / 2, mHeight - strokeWidth / 2)
            mWidth == mHeight -> RectF(0f + strokeWidth / 2, 0f + strokeWidth / 2, min(mWidth, mHeight) - strokeWidth / 2, min(mWidth, mHeight) - strokeWidth / 2)
            else              -> RectF(0f + strokeWidth / 2, mHeight / 2 - mWidth / 2 + strokeWidth / 2, mWidth - strokeWidth / 2, mHeight / 2 - mWidth / 2 + mWidth - strokeWidth / 2)
        }
    }
    private val multiElement by lazy { ArrayMap<Int, Float>() }
    private var mHeight: Float = 0f
    private var mWidth: Float = 0f

    init {
        multiElement.put(Color.parseColor("#eadc4b"), 50f)
        multiElement.put(Color.parseColor("#f9a844"), 50f)
        multiElement.put(Color.parseColor("#b5db39"), 50f)
        strokeWidth = DensityUtils.dip2px(context, 16f).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec).toFloat()
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec).toFloat()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        var angle = startAngle
        paint.strokeWidth = strokeWidth;
        var allValue = 0f
        multiElement.all { entry ->
            allValue += entry.value
            true
        }
        for (entry in multiElement) {
            val sweepAngle = (entry.value.toFloat() / allValue) * 360
            paint.color = entry.key
            canvas?.drawArc(rectf, angle, sweepAngle, false, paint)
            angle += sweepAngle
        }

        /**绘制中间文本*/
        val textPaint = TextPaint()
        textPaint.setARGB(0xFF, 0, 0, 0)
        textPaint.textSize = 20.0f
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.style = Paint.Style.FILL

        val fontMetrics = textPaint.fontMetrics
        val top = fontMetrics.top//为基线到字体上边框的距离,即上图中的top
        val bottom = fontMetrics.bottom//为基线到字体下边框的距离,即上图中的bottom
        val baseLineY = (rectf.centerY() - top / 2 - bottom / 2)//基线中间点的y轴计算公式
//        用android的canvas drawText的时候，即使text包行\r\n，画出来的仍然不会换行。使用StaticLayout解决
        val layout = StaticLayout(centerText, textPaint, 300,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true)
        canvas?.save()
        canvas?.translate(rectf.centerX(), baseLineY)//从20，20开始画
        layout.draw(canvas)
        canvas?.restore()//别忘了restore

        super.onDraw(canvas)
    }

    /**
     * 设置每一个选项的 Item
     */
    public fun setProgress(elements: List<Element>) {
        multiElement.clear()
        for (element in elements)
        {
            multiElement.put(element.color, element.progress)
        }
        postInvalidate()
    }

    /**
     * 设置画笔宽度
     */
    public fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        postInvalidate()
    }

    /**
     * 设置开始画的角度
     */
    public fun setStartAngle(startAngle: Float) {
        this.startAngle = startAngle
        postInvalidate()
    }
    /**
     * 设置中间文字
     */
    public fun setCenterText(text: String) {
        this.centerText = text
        postInvalidate()
    }


    public class Element(@ColorInt val color: Int, val progress: Float)

}
