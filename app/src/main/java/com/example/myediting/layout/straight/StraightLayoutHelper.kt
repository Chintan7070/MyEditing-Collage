package com.example.myediting.layout.straight

import com.xiaopo.flying.puzzle.PuzzleLayout

/**
 * @author wupanjie
 */
object StraightLayoutHelper {
    fun getAllThemeLayout(pieceCount: Int): List<PuzzleLayout> {
        val puzzleLayouts: MutableList<PuzzleLayout> = ArrayList()
        when (pieceCount) {
            1 -> {
                var i = 0
                while (i < 6) {
                    puzzleLayouts.add(OneStraightLayout(i))
                    i++
                }
            }
            2 -> {
                var i = 0
                while (i < 6) {
                    puzzleLayouts.add(TwoStraightLayout(i))
                    i++
                }
            }
            3 -> {
                var i = 0
                while (i < 6) {
                    puzzleLayouts.add(ThreeStraightLayout(i))
                    i++
                }
            }
            4 -> {
                var i = 0
                while (i < 8) {
                    puzzleLayouts.add(FourStraightLayout(i))
                    i++
                }
            }
            5 -> {
                var i = 0
                while (i < 17) {
                    puzzleLayouts.add(FiveStraightLayout(i))
                    i++
                }
            }
            6 -> {
                var i = 0
                while (i < 12) {
                    puzzleLayouts.add(SixStraightLayout(i))
                    i++
                }
            }
            7 -> {
                var i = 0
                while (i < 9) {
                    puzzleLayouts.add(SevenStraightLayout(i))
                    i++
                }
            }
            8 -> {
                var i = 0
                while (i < 11) {
                    puzzleLayouts.add(EightStraightLayout(i))
                    i++
                }
            }
            9 -> {
                var i = 0
                while (i < 8) {
                    puzzleLayouts.add(NineStraightLayout(i))
                    i++
                }
            }
        }
        return puzzleLayouts
    }
}