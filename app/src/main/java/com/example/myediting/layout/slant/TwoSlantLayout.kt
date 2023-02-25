package com.example.myediting.layout.slant

import com.xiaopo.flying.puzzle.Line

/**
 * @author wupanjie
 */
class TwoSlantLayout(theme: Int) : NumberSlantLayout(theme) {
    override val themeCount: Int
        get() = 2

    companion object{
        val themeCount: Int
            get() = 2
    }
    override fun layout() {
        when (theme) {
            0 -> addLine(0, Line.Direction.HORIZONTAL, 0.56f, 0.44f)
            1 -> addLine(0, Line.Direction.VERTICAL, 0.56f, 0.44f)
        }
    }
}