package com.example.myediting.layout.straight

import android.util.Log
import com.xiaopo.flying.puzzle.straight.StraightPuzzleLayout

/**
 * @author wupanjie
 */
abstract class NumberStraightLayout(theme: Int) : StraightPuzzleLayout() {
    var theme: Int
        protected set

    init {
        if (theme >= themeCount) {
            Log.e(
                TAG, "NumberStraightLayout: the most theme count is "
                        + themeCount
                        + " ,you should let theme from 0 to "
                        + (themeCount - 1)
                        + " ."
            )
        }
        this.theme = theme
    }

    abstract val themeCount: Int

    companion object {
        const val TAG = "NumberStraightLayout"
    }
}