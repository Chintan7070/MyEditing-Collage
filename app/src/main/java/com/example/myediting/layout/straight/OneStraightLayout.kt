package com.example.myediting.layout.straight

import com.xiaopo.flying.puzzle.Line

/**
 * @author wupanjie
 */
class OneStraightLayout(theme: Int) : NumberStraightLayout(theme) {
    override val themeCount: Int
        get() = 6

    companion object
    {
        val themeCount: Int = 6
    }
    override fun layout() {
        when (theme) {
            0 -> addLine(0, Line.Direction.HORIZONTAL, 1f / 2)
            1 -> addLine(0, Line.Direction.VERTICAL, 1f / 2)
            2 -> addCross(0, 1f / 2)
            3 -> cutAreaEqualPart(0, 2, 1)
            4 -> cutAreaEqualPart(0, 1, 2)
            5 -> cutAreaEqualPart(0, 2, 2)
            else -> addLine(0, Line.Direction.HORIZONTAL, 1f / 2)
        }
    }
}