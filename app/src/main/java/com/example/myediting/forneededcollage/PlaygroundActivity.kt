package com.example.myediting.forneededcollage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myediting.CollageEditActivity
import com.example.myediting.R
import com.example.myediting.adapter.PuzzleAdapter
import com.xiaopo.flying.puzzle.PuzzleLayout
import com.xiaopo.flying.puzzle.slant.SlantPuzzleLayout


class PlaygroundActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playground)
        initView()
        prefetchResPhoto()
    }

    private fun prefetchResPhoto() {
        val resIds = intArrayOf(
            R.drawable.demo1,
            R.drawable.demo2,
            R.drawable.demo3,
            R.drawable.demo4,
            R.drawable.demo5,
            R.drawable.demo6,
            R.drawable.demo7,
            R.drawable.demo8,
            R.drawable.demo9
        )

        /*for (resId in resIds) {
            Picasso.with(this)
                .load(resId)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .config(Bitmap.Config.RGB_565)
                .fetch()
        }*/
    }

    private fun initView() {
        val puzzleList: RecyclerView = findViewById<View>(R.id.puzzle_list) as RecyclerView
        puzzleList.setLayoutManager(GridLayoutManager(this, 2))
        val puzzleAdapter = PuzzleAdapter()
        puzzleList.adapter = puzzleAdapter
        puzzleAdapter.refreshData(PuzzleUtils.getAllPuzzleLayouts(), null)
        puzzleAdapter.setOnItemClickListener(object : PuzzleAdapter.OnItemClickListener {

            override fun onItemClick(puzzleLayout: PuzzleLayout?, themeId: Int) {
                val intent: Intent = Intent(this@PlaygroundActivity, CollageEditActivity::class.java)
                if (puzzleLayout is SlantPuzzleLayout) {
                    intent.putExtra("type", 0)
                } else {
                    intent.putExtra("type", 1)
                }
                intent.putExtra("piece_size", puzzleLayout!!.getAreaCount())
                intent.putExtra("theme_id", themeId)
                startActivity(intent)

            }
        })
        val btnClose = findViewById<View>(R.id.btn_cancel) as ImageView
        btnClose.setOnClickListener { onBackPressed() }
    }
}