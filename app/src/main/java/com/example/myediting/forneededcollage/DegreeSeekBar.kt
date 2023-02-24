package com.example.myediting.forneededcollage

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * @author wupanjie
 */
class DegreeSeekBar : View {
    private var mTextPaint: Paint? = null
    private var mCirclePaint: Paint? = null
    private var mFontMetrics: Paint.FontMetricsInt? = null
    private var mBaseLine = 0
    private lateinit var mTextWidths: FloatArray
    private val mCanvasClipBounds = Rect()
    private var mScrollingListener: ScrollingListener? = null
    private var mLastTouchedPosition = 0f
    private var mPointPaint: Paint? = null
    private var mPointMargin = 0f
    private var mScrollStarted = false
    private var mTotalScrollDistance = 0
    private val mIndicatorPath = Path()
    private var mCurrentDegrees = 0
    private val mPointCount = 51
    private var mPointColor = Color.WHITE
    private var mTextColor = Color.WHITE
    private var mCenterTextColor = Color.WHITE

    //阻尼系数的倒数
    var dragFactor = 2.1f
    private var mMinReachableDegrees = -45
    private var mMaxReachableDegrees = 45
    private var suffix = ""

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        mPointPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPointPaint!!.style = Paint.Style.STROKE
        mPointPaint!!.color = mPointColor
        mPointPaint!!.strokeWidth = 2f
        mTextPaint = Paint()
        mTextPaint!!.color = mTextColor
        mTextPaint!!.style = Paint.Style.STROKE
        mTextPaint!!.isAntiAlias = true
        mTextPaint!!.textSize = 24f
        mTextPaint!!.textAlign = Paint.Align.LEFT
        mTextPaint!!.alpha = 100
        mFontMetrics = mTextPaint!!.fontMetricsInt
        mTextWidths = FloatArray(1)
        mTextPaint!!.getTextWidths("0", mTextWidths)
        mCirclePaint = Paint()
        mCirclePaint!!.style = Paint.Style.FILL
        mCirclePaint!!.alpha = 255
        mCirclePaint!!.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mPointMargin = w.toFloat() / mPointCount
        mBaseLine = (h - mFontMetrics!!.bottom + mFontMetrics!!.top) / 2 - mFontMetrics!!.top
        mIndicatorPath.moveTo((w / 2).toFloat(), (h / 2 + mFontMetrics!!.top / 2 - 18).toFloat())
        mIndicatorPath.rLineTo(-8f, -8f)
        mIndicatorPath.rLineTo(16f, 0f)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastTouchedPosition = event.x
                if (!mScrollStarted) {
                    mScrollStarted = true
                    if (mScrollingListener != null) {
                        mScrollingListener!!.onScrollStart()
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                mScrollStarted = false
                if (mScrollingListener != null) {
                    mScrollingListener!!.onScrollEnd()
                }
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                val distance = event.x - mLastTouchedPosition
                if (mCurrentDegrees >= mMaxReachableDegrees && distance < 0) {
                    mCurrentDegrees = mMaxReachableDegrees
                    invalidate()
                }
                if (mCurrentDegrees <= mMinReachableDegrees && distance > 0) {
                    mCurrentDegrees = mMinReachableDegrees
                    invalidate()
                }
                if (distance != 0f) {
                    onScrollEvent(event, distance)
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.getClipBounds(mCanvasClipBounds)
        val zeroIndex = mPointCount / 2 + (0 - mCurrentDegrees) / 2
        mPointPaint!!.color = mPointColor
        for (i in 0 until mPointCount) {
            if (i > zeroIndex - Math.abs(mMinReachableDegrees) / 2 && i < zeroIndex + Math.abs(
                    mMaxReachableDegrees
                ) / 2 && mScrollStarted
            ) {
                mPointPaint!!.alpha = 255
            } else {
                mPointPaint!!.alpha = 100
            }
            if (i > mPointCount / 2 - 8 && i < mPointCount / 2 + 8 && i > zeroIndex - Math.abs(
                    mMinReachableDegrees
                ) / 2 && i < zeroIndex + Math.abs(mMaxReachableDegrees) / 2
            ) {
                if (mScrollStarted) {
                    mPointPaint!!.alpha = Math.abs(mPointCount / 2 - i) * 255 / 8
                } else {
                    mPointPaint!!.alpha = Math.abs(mPointCount / 2 - i) * 100 / 8
                }
            }
            canvas.drawPoint(
                mCanvasClipBounds.centerX() + (i - mPointCount / 2) * mPointMargin,
                mCanvasClipBounds.centerY().toFloat(), mPointPaint!!
            )
            if (mCurrentDegrees != 0 && i == zeroIndex) {
                if (mScrollStarted) {
                    mTextPaint!!.alpha = 255
                } else {
                    mTextPaint!!.alpha = 192
                }
                mPointPaint!!.strokeWidth = 4f
                canvas.drawPoint(
                    mCanvasClipBounds.centerX() + (i - mPointCount / 2) * mPointMargin,
                    mCanvasClipBounds.centerY().toFloat(), mPointPaint!!
                )
                mPointPaint!!.strokeWidth = 2f
                mTextPaint!!.alpha = 100
            }
        }
        var i = -180
        while (i <= 180) {
            if (i >= mMinReachableDegrees && i <= mMaxReachableDegrees) {
                drawDegreeText(i, canvas, true)
            } else {
                drawDegreeText(i, canvas, false)
            }
            i += 15
        }
        mTextPaint!!.textSize = 28f
        mTextPaint!!.alpha = 255
        mTextPaint!!.color = mCenterTextColor
        if (mCurrentDegrees >= 10) {
            canvas.drawText(
                mCurrentDegrees.toString() + suffix,
                width / 2 - mTextWidths[0],
                mBaseLine.toFloat(),
                mTextPaint!!
            )
        } else if (mCurrentDegrees <= -10) {
            canvas.drawText(
                mCurrentDegrees.toString() + suffix,
                width / 2 - mTextWidths[0] / 2 * 3,
                mBaseLine.toFloat(),
                mTextPaint!!
            )
        } else if (mCurrentDegrees < 0) {
            canvas.drawText(
                mCurrentDegrees.toString() + suffix,
                width / 2 - mTextWidths[0],
                mBaseLine.toFloat(),
                mTextPaint!!
            )
        } else {
            canvas.drawText(
                mCurrentDegrees.toString() + suffix,
                width / 2 - mTextWidths[0] / 2,
                mBaseLine.toFloat(),
                mTextPaint!!
            )
        }
        mTextPaint!!.alpha = 100
        mTextPaint!!.textSize = 24f
        mTextPaint!!.color = mTextColor
        //画中心三角
        mCirclePaint!!.color = mCenterTextColor
        canvas.drawPath(mIndicatorPath, mCirclePaint!!)
        mCirclePaint!!.color = mCenterTextColor
    }

    private fun drawDegreeText(degrees: Int, canvas: Canvas, canReach: Boolean) {
        if (canReach) {
            if (mScrollStarted) {
                mTextPaint!!.alpha = Math.min(255, Math.abs(degrees - mCurrentDegrees) * 255 / 15)
                if (Math.abs(degrees - mCurrentDegrees) <= 7) {
                    mTextPaint!!.alpha = 0
                }
            } else {
                mTextPaint!!.alpha = 100
                if (Math.abs(degrees - mCurrentDegrees) <= 7) {
                    mTextPaint!!.alpha = 0
                }
            }
        } else {
            mTextPaint!!.alpha = 100
        }
        if (degrees == 0) {
            if (Math.abs(mCurrentDegrees) >= 15 && !mScrollStarted) {
                mTextPaint!!.alpha = 180
            }
            canvas.drawText(
                "0°",
                width / 2 - mTextWidths[0] / 2 - mCurrentDegrees / 2 * mPointMargin,
                (
                        height / 2 - 10).toFloat(), mTextPaint!!
            )
        } else {
            canvas.drawText(
                degrees.toString() + suffix,
                width / 2 + mPointMargin * degrees / 2 - mTextWidths[0] / 2 * 3 - mCurrentDegrees / 2 * mPointMargin,
                (height / 2 - 10).toFloat(),
                mTextPaint!!
            )
        }
    }

    private fun onScrollEvent(event: MotionEvent, distance: Float) {
        mTotalScrollDistance -= distance.toInt()
        postInvalidate()
        mLastTouchedPosition = event.x
        mCurrentDegrees = (mTotalScrollDistance * dragFactor / mPointMargin).toInt()
        if (mScrollingListener != null) {
            mScrollingListener!!.onScroll(mCurrentDegrees)
        }
    }

    fun setDegreeRange(min: Int, max: Int) {
        if (min > max) {
            Log.e(TAG, "setDegreeRange: error, max must greater than min")
        } else {
            mMinReachableDegrees = min
            mMaxReachableDegrees = max
            if (mCurrentDegrees > mMaxReachableDegrees || mCurrentDegrees < mMinReachableDegrees) {
                mCurrentDegrees = (mMinReachableDegrees + mMaxReachableDegrees) / 2
            }
            mTotalScrollDistance = (mCurrentDegrees * mPointMargin / dragFactor).toInt()
            invalidate()
        }
    }

    fun setCurrentDegrees(degrees: Int) {
        if (degrees <= mMaxReachableDegrees && degrees >= mMinReachableDegrees) {
            mCurrentDegrees = degrees
            mTotalScrollDistance = (degrees * mPointMargin / dragFactor).toInt()
            invalidate()
        }
    }

    fun setScrollingListener(scrollingListener: ScrollingListener?) {
        mScrollingListener = scrollingListener
    }

    var pointColor: Int
        get() = mPointColor
        set(pointColor) {
            mPointColor = pointColor
            mPointPaint!!.color = mPointColor
            postInvalidate()
        }
    var textColor: Int
        get() = mTextColor
        set(textColor) {
            mTextColor = textColor
            mTextPaint!!.color = textColor
            postInvalidate()
        }
    var centerTextColor: Int
        get() = mCenterTextColor
        set(centerTextColor) {
            mCenterTextColor = centerTextColor
            postInvalidate()
        }

    fun setSuffix(suffix: String) {
        this.suffix = suffix
    }

    interface ScrollingListener {
        fun onScrollStart()
        fun onScroll(currentDegrees: Int)
        fun onScrollEnd()
    }

    companion object {
        private const val TAG = "DegreeSeekBar"
    }
}