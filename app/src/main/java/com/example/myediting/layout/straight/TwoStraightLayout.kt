package com.example.myediting.layout.straight

import android.util.Log
import com.xiaopo.flying.puzzle.Line

/**
 * @author wupanjie
 */
class TwoStraightLayout : NumberStraightLayout {
    private var mRadio = 1f / 2

    constructor(theme: Int) : super(theme) {}
    constructor(radio: Float, theme: Int) : super(theme) {
        if (mRadio > 1) {
            Log.e(
                NumberStraightLayout.Companion.TAG,
                "CrossLayout: the radio can not greater than 1f"
            )
            mRadio = 1f
        }
        mRadio = radio
    }

    override val themeCount: Int
        get() = 6

    override fun layout() {
        when (theme) {
            0 -> addLine(0, Line.Direction.HORIZONTAL, mRadio)
            1 -> addLine(0, Line.Direction.VERTICAL, mRadio)
            2 -> addLine(0, Line.Direction.HORIZONTAL, 1f / 3)
            3 -> addLine(0, Line.Direction.HORIZONTAL, 2f / 3)
            4 -> addLine(0, Line.Direction.VERTICAL, 1f / 3)
            5 -> addLine(0, Line.Direction.VERTICAL, 2f / 3)
            else -> addLine(0, Line.Direction.HORIZONTAL, mRadio)
        }
    }
}