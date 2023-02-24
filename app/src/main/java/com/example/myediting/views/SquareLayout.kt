package com.photo.editorapp.picadot.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class SquareLayout : RelativeLayout {
    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context,
        attributeSet,
        i
    ) {
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet) {}
    constructor(context: Context?) : super(context) {}

    public override fun onMeasure(i: Int, i2: Int) {
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2))
        val makeMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824)
        super.onMeasure(makeMeasureSpec, makeMeasureSpec)
    }
}