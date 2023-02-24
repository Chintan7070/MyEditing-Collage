package com.example.myediting.layout.slant

import com.xiaopo.flying.puzzle.PuzzleLayout

/**
 * @author wupanjie
 */
object SlantLayoutHelper {
    fun getAllThemeLayout(pieceCount: Int): List<PuzzleLayout> {
        val puzzleLayouts: MutableList<PuzzleLayout> = ArrayList()
        when (pieceCount) {
            1 -> {
                var i = 0
                while (i < 4) {
                    puzzleLayouts.add(OneSlantLayout(i))
                    i++
                }
            }
            2 -> {
                var i = 0
                while (i < 2) {
                    puzzleLayouts.add(TwoSlantLayout(i))
                    i++
                }
            }
            3 -> {
                var i = 0
                while (i < 6) {
                    puzzleLayouts.add(ThreeSlantLayout(i))
                    i++
                }
            }
        }
        return puzzleLayouts
    }
}