package com.example.myediting.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myediting.R
import com.example.myediting.forneededcollage.PuzzleUtils
import com.example.myediting.layout.slant.ThreeSlantLayout
import com.example.myediting.layout.slant.TwoSlantLayout
import com.example.myediting.layout.straight.*
import com.example.myediting.utils.ClickInterface
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.xiaopo.flying.puzzle.PuzzleLayout
import com.xiaopo.flying.puzzle.PuzzleView
import kotlinx.android.synthetic.main.item_theam.view.*

class CollageTheamAdapter(
    var activity: Activity,
    var bitmapPaint: ArrayList<String>,
    var type: Int,
    var pieceSize: Int,
    var themeId: Int,
    var clickInterface: ClickInterface
) : RecyclerView.Adapter<CollageTheamAdapter.ViewData>() {


    private var deviceWidth: Int = 0
    private var iPuzzleView: PuzzleView? = null
    private lateinit var puzzleLayout: PuzzleLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_theam, parent, false)
        return ViewData(view)

    }

    override fun getItemCount(): Int {
        //works
        when (pieceSize) {
            1 -> OneStraightLayout(themeId)
            2 -> return TwoSlantLayout.themeCount
            3 -> return ThreeSlantLayout.themeCount
            4 -> return ThreeSlantLayout.themeCount
            5 -> return FiveStraightLayout.themeCount
            6 -> return SixStraightLayout.themeCount
            7 -> return SevenStraightLayout.themeCount
            8 -> return EightStraightLayout.themeCount
            9 -> return NineStraightLayout.themeCount
            else -> OneStraightLayout(themeId)
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

            iPuzzleView = holder.iPuzzleView
            deviceWidth = activity.resources.displayMetrics.widthPixels

            puzzleLayout = PuzzleUtils.getPuzzleLayout(type, pieceSize, position)

            iPuzzleView!!.puzzleLayout = puzzleLayout
            iPuzzleView!!.isTouchEnable = false
            iPuzzleView!!.isNeedDrawLine = false
            iPuzzleView!!.isNeedDrawOuterLine = false
            iPuzzleView!!.lineSize = 4
            iPuzzleView!!.lineColor = Color.BLACK
            iPuzzleView!!.selectedLineColor = Color.BLACK
            iPuzzleView!!.handleBarColor = Color.BLACK
//            iPuzzleView!!.setAnimateDuration(3000)


            // currently the SlantPuzzleLayout do not support padding
            iPuzzleView!!.piecePadding = 3f

            iPuzzleView!!.post { loadPhoto() }
//        loadPhoto()

        holder.itemView.setOnClickListener {
            clickInterface.imageClick(position,position)
        }

       /* when (pieceSize) {
            1 -> Log.e("TAG", "onBindViewHolder: 1 :"+OneStraightLayout.themeCount)
            2 -> Log.e("TAG", "onBindViewHolder: 2 : "+ TwoSlantLayout.themeCount);
            3 -> Log.e("TAG", "onBindViewHolder: 3 : "+ ThreeSlantLayout.themeCount);
            4 -> Log.e("TAG", "onBindViewHolder: 4 : "+ ThreeSlantLayout.themeCount);
            5 -> Log.e("TAG", "onBindViewHolder: 5 : "+ FiveStraightLayout.themeCount);
            6 -> Log.e("TAG", "onBindViewHolder: 6 : "+ SixStraightLayout.themeCount);
            7 -> Log.e("TAG", "onBindViewHolder: 7 : "+ SevenStraightLayout.themeCount);
            8 -> Log.e("TAG", "onBindViewHolder: 8 : "+ EightStraightLayout.themeCount);
            9 -> Log.e("TAG", "onBindViewHolder: 9 : "+ NineStraightLayout.themeCount,);
//            1 -> OneStraightLayout(themeId)
//            2 -> Toast.makeText(activity, TwoSlantLayout.themeCount,
//            3 -> Toast.makeText(activity, ThreeSlantLayout.themeCount,
//            4 -> Toast.makeText(activity, ThreeSlantLayout.themeCount,
//            5 -> Toast.makeText(activity, FiveStraightLayout.themeCount,
//            6 -> Toast.makeText(activity, SixStraightLayout.themeCount,
//            7 -> Toast.makeText(activity, SevenStraightLayout.themeCount,
//            8 -> Toast.makeText(activity, EightStraightLayout.themeCount,
//            9 -> Toast.makeText(activity, NineStraightLayout.themeCount,
            else -> OneStraightLayout(themeId)
        }*/

    }

    private fun loadPhoto() {

        val pieces: MutableList<Bitmap> = java.util.ArrayList()

        val count =
            if (bitmapPaint.size > puzzleLayout.areaCount) puzzleLayout.areaCount else bitmapPaint.size

        for (i in 0 until count) {
            val target: Target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    pieces.add(bitmap)
                    if (pieces.size == count) {
                        if (bitmapPaint.size < puzzleLayout.areaCount) {
                            for (i in 0 until puzzleLayout.areaCount) {

                                iPuzzleView!!.addPiece(pieces[i % count])
                            }
                        } else {
                            iPuzzleView!!.addPieces(pieces)
                        }
                    }
                    //targets.remove(this)
                }

                override fun onBitmapFailed(errorDrawable: Drawable?) {}

                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            }
            Picasso.with(activity)
                .load("file:///" + bitmapPaint[i])
                .resize(deviceWidth, deviceWidth)
                .centerInside()
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.demo1)
                .into(target)
            // targets.add(target)
        }
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iPuzzleView = itemView.iPuzzleView
    }
}