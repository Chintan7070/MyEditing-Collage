package com.example.myediting.layout.slant

import android.util.Log
import com.xiaopo.flying.puzzle.slant.SlantPuzzleLayout

/**
 * @author wupanjie
 */
abstract class NumberSlantLayout(theme: Int) : SlantPuzzleLayout() {
    var theme: Int
        protected set

    init {
        if (theme >= themeCount) {
            Log.e(
                TAG, "NumberSlantLayout: the most theme count is "
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
        const val TAG = "NumberSlantLayout"
    }
}