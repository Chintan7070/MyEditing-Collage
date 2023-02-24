package com.example.myediting.forneededcollage

import com.example.myediting.layout.slant.OneSlantLayout
import com.example.myediting.layout.slant.SlantLayoutHelper
import com.example.myediting.layout.slant.ThreeSlantLayout
import com.example.myediting.layout.slant.TwoSlantLayout
import com.example.myediting.layout.straight.*
import com.xiaopo.flying.puzzle.PuzzleLayout


/**
 * @author wupanjie
 */

object PuzzleUtils {
    private const val TAG = "PuzzleUtils"
    fun getPuzzleLayout(type: Int, borderSize: Int, themeId: Int): PuzzleLayout {
        return if (type == 0) {
            when (borderSize) {
                1 -> OneSlantLayout(themeId)
                2 -> TwoSlantLayout(themeId)
                3 -> ThreeSlantLayout(themeId)
                else -> OneSlantLayout(themeId)
            }
        } else {
            when (borderSize) {
                1 -> OneStraightLayout(themeId)
                2 -> TwoStraightLayout(themeId)
                3 -> ThreeStraightLayout(themeId)
                4 -> FourStraightLayout(themeId)
                5 -> FiveStraightLayout(themeId)
                6 -> SixStraightLayout(themeId)
                7 -> SevenStraightLayout(themeId)
                8 -> EightStraightLayout(themeId)
                9 -> NineStraightLayout(themeId)
                else -> OneStraightLayout(themeId)
            }
        }
    }
    //slant layout

    // straight layout

    val allPuzzleLayouts: List<Any>
        get() {
            val puzzleLayouts: MutableList<PuzzleLayout> = ArrayList<PuzzleLayout>()
            //slant layout
            puzzleLayouts.addAll(SlantLayoutHelper.getAllThemeLayout(2))
            puzzleLayouts.addAll(SlantLayoutHelper.getAllThemeLayout(3))

            // straight layout
            puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(2))
            puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(3))
            puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(4))
            puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(5))
            puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(6))
            puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(7))
            puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(8))
            puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(9))
            return puzzleLayouts
        }

    fun getPuzzleLayouts(pieceCount: Int): List<PuzzleLayout> {
        val puzzleLayouts: MutableList<PuzzleLayout> = ArrayList<PuzzleLayout>()
        puzzleLayouts.addAll(SlantLayoutHelper.getAllThemeLayout(pieceCount))
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(pieceCount))
        return puzzleLayouts
    }

    @JvmName("getAllPuzzleLayouts1")
    fun getAllPuzzleLayouts(): List<PuzzleLayout>? {
        val puzzleLayouts: MutableList<PuzzleLayout> = ArrayList()
        //slant layout
        puzzleLayouts.addAll(SlantLayoutHelper.getAllThemeLayout(2))
        puzzleLayouts.addAll(SlantLayoutHelper.getAllThemeLayout(3))

        // straight layout
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(2))
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(3))
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(4))
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(5))
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(6))
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(7))
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(8))
        puzzleLayouts.addAll(StraightLayoutHelper.getAllThemeLayout(9))
        return puzzleLayouts
    }
}