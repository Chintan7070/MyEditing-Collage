package com.example.myediting.layout.slant

import com.xiaopo.flying.puzzle.Line

/**
 * @author wupanjie
 */
class OneSlantLayout(theme: Int) : NumberSlantLayout(theme) {
    override val themeCount: Int
        get() = 4

    companion object{
        val themeCount: Int
            get() = 4
    }

    override fun layout() {
        when (theme) {
            0 -> addLine(0, Line.Direction.HORIZONTAL, 0.56f, 0.44f)
            1 -> addLine(0, Line.Direction.VERTICAL, 0.56f, 0.44f)
            2 -> addCross(0, 0.56f, 0.44f, 0.56f, 0.44f)
            3 -> cutArea(0, 1, 2)
        }
    }
}