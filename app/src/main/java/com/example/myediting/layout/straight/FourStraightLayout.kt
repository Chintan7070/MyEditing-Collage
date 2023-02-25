package com.example.myediting.layout.straight

import com.xiaopo.flying.puzzle.Line

/**
 * @author wupanjie
 */
class FourStraightLayout(theme: Int) : NumberStraightLayout(theme) {
    companion object{
        val themeCount: Int = 8
            private const val TAG = "FourStraightLayout"
    }
    override val themeCount: Int
        get() = 8

    override fun layout() {
        when (theme) {
            0 -> cutAreaEqualPart(0, 4, Line.Direction.HORIZONTAL)
            1 -> cutAreaEqualPart(0, 4, Line.Direction.VERTICAL)
            2 -> addCross(0, 1f / 2)
            3 -> {
                addLine(0, Line.Direction.HORIZONTAL, 1f / 3)
                cutAreaEqualPart(0, 3, Line.Direction.VERTICAL)
            }
            4 -> {
                addLine(0, Line.Direction.HORIZONTAL, 2f / 3)
                cutAreaEqualPart(1, 3, Line.Direction.VERTICAL)
            }
            5 -> {
                addLine(0, Line.Direction.VERTICAL, 1f / 3)
                cutAreaEqualPart(0, 3, Line.Direction.HORIZONTAL)
            }
            6 -> {
                addLine(0, Line.Direction.VERTICAL, 2f / 3)
                cutAreaEqualPart(1, 3, Line.Direction.HORIZONTAL)
            }
            7 -> {
                addLine(0, Line.Direction.VERTICAL, 1f / 2)
                addLine(1, Line.Direction.HORIZONTAL, 2f / 3)
                addLine(1, Line.Direction.HORIZONTAL, 1f / 3)
            }
            else -> cutAreaEqualPart(0, 4, Line.Direction.HORIZONTAL)
        }
    }


}