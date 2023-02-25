package com.example.myediting.layout.slant

import com.xiaopo.flying.puzzle.Line

/**
 * @author wupanjie
 */
class ThreeSlantLayout(theme: Int) : NumberSlantLayout(theme) {
    companion object{
        val themeCount: Int
            get() = 6
    }

    override val themeCount: Int
        get() = 6


    override fun layout() {
        when (theme) {
            0 -> {
                addLine(0, Line.Direction.HORIZONTAL, 0.5f)
                addLine(0, Line.Direction.VERTICAL, 0.56f, 0.44f)
            }
            1 -> {
                addLine(0, Line.Direction.HORIZONTAL, 0.5f)
                addLine(1, Line.Direction.VERTICAL, 0.56f, 0.44f)
            }
            2 -> {
                addLine(0, Line.Direction.VERTICAL, 0.5f)
                addLine(0, Line.Direction.HORIZONTAL, 0.56f, 0.44f)
            }
            3 -> {
                addLine(0, Line.Direction.VERTICAL, 0.5f)
                addLine(1, Line.Direction.HORIZONTAL, 0.56f, 0.44f)
            }
            4 -> {
                addLine(0, Line.Direction.HORIZONTAL, 0.44f, 0.56f)
                addLine(0, Line.Direction.VERTICAL, 0.56f, 0.44f)
            }
            5 -> {
                addLine(0, Line.Direction.VERTICAL, 0.56f, 0.44f)
                addLine(1, Line.Direction.HORIZONTAL, 0.44f, 0.56f)
            }
        }
    }
}