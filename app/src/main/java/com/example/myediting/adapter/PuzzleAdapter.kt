package com.example.myediting.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myediting.R
import com.example.myediting.layout.slant.NumberSlantLayout
import com.example.myediting.layout.straight.NumberStraightLayout
import com.xiaopo.flying.puzzle.PuzzleLayout
import com.xiaopo.flying.puzzle.SquarePuzzleView

/**
 * @author wupanjie
 */
class PuzzleAdapter : RecyclerView.Adapter<PuzzleAdapter.PuzzleViewHolder>() {
    private var layoutData: List<PuzzleLayout>? = ArrayList()
    private var bitmapData: List<Bitmap>? = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuzzleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_puzzle, parent, false)
        return PuzzleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PuzzleViewHolder, position: Int) {
        val puzzleLayout = layoutData!![position]
        holder.puzzleView.isNeedDrawLine = true
        holder.puzzleView.isNeedDrawOuterLine = true
        holder.puzzleView.isTouchEnable = false
        holder.puzzleView.puzzleLayout = puzzleLayout
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                var theme = 0
                if (puzzleLayout is NumberSlantLayout) {
                    theme = puzzleLayout.theme
                } else if (puzzleLayout is NumberStraightLayout) {
                    theme = puzzleLayout.theme
                }
                onItemClickListener!!.onItemClick(puzzleLayout, theme)
            }
        }
        if (bitmapData == null) return
        val bitmapSize = bitmapData!!.size
        if (puzzleLayout.areaCount > bitmapSize) {
            for (i in 0 until puzzleLayout.areaCount) {
                holder.puzzleView.addPiece(bitmapData!![i % bitmapSize])
            }
        } else {
            holder.puzzleView.addPieces(bitmapData)
        }
    }

    override fun getItemCount(): Int {
        return if (layoutData == null) 0 else layoutData!!.size
    }

    fun refreshData(layoutData: List<PuzzleLayout>?, bitmapData: List<Bitmap>?) {
        this.layoutData = layoutData
        this.bitmapData = bitmapData
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    class PuzzleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var puzzleView: SquarePuzzleView

        init {
            puzzleView = itemView.findViewById<View>(R.id.puzzle) as SquarePuzzleView
        }
    }

    interface OnItemClickListener {
        fun onItemClick(puzzleLayout: PuzzleLayout?, themeId: Int)
    }
}