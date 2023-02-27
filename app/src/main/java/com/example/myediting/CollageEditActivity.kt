package com.example.myediting

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.bumptech.glide.Glide
import com.example.myediting.adapter.CollageTheamAdapter
import com.example.myediting.forneededcollage.DegreeSeekBar
import com.example.myediting.forneededcollage.FileUtils
import com.example.myediting.forneededcollage.PuzzleUtils
import com.example.myediting.utils.ClickInterface
import com.example.myediting.utils.Constants
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import com.xiaopo.flying.puzzle.PuzzleLayout
import com.xiaopo.flying.puzzle.PuzzleView
import com.xiaopo.flying.puzzle.PuzzleView.OnPieceSelectedListener
import kotlinx.android.synthetic.main.activity_collage_edit.*
import java.io.File


open class CollageEditActivity : AppCompatActivity(), View.OnClickListener {
    private var deviceWidth: Int = 0
    private lateinit var puzzleLayout: PuzzleLayout
    private lateinit var imageListForEdit: ArrayList<String>
    val TAG = "CollageEditActivity"
    private lateinit var bitmapPaint: List<String>

    private var puzzleView: PuzzleView? = null
    private var controlFlag = 0

    var FLAG_CONTROL_LINE_SIZE = 1
    var FLAG_CONTROL_CORNER = 1 shl 1
    val targets : List<Target> = ArrayList<Target>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collage_edit)
        imageListForEdit = ArrayList<String>()
        val intent = intent
        val args = intent.getBundleExtra("BUNDLE")
        imageListForEdit = args!!.getSerializable("ARRAYLIST") as ArrayList<String>

         for (imagepath : String in imageListForEdit){
             Log.e(TAG, "onCreate: image links:=="+imagepath )
         }

        bitmapPaint = args.getSerializable("ARRAYLIST") as ArrayList<String>
        deviceWidth = resources.displayMetrics.widthPixels


//        val type = 0
        val type = getIntent().getIntExtra("type", 0)
        val pieceSize: Int = bitmapPaint.size
        val themeId = 1
//        val themeId = getIntent().getIntExtra("theme_id", 0)
        //bitmapPaint = getIntent().getStringArrayListExtra("photo_path");
        //bitmapPaint = getIntent().getStringArrayListExtra("photo_path");
        Log.e(TAG, "onCreate: Type : = $type---- Picee_size:=$pieceSize ------theme:= $themeId")

        puzzleLayout = PuzzleUtils.getPuzzleLayout(type, pieceSize, themeId)

        initView()

        puzzleView!!.post { loadPhoto() }

        loadAdapterTheam(bitmapPaint as ArrayList<String>,type, pieceSize, themeId)

    }

    private fun loadAdapterTheam(
        bitmapPaint: ArrayList<String>,
        type: Int,
        pieceSize: Int,
        themeId: Int
    ) {
        val adapter = CollageTheamAdapter(this,bitmapPaint,type, pieceSize, themeId,object : ClickInterface{
            override fun folderClick(position: Int, imagePath: java.util.ArrayList<String>) {
            }

            override fun imageClick(folderPosition: Int, position: Int) {
                Log.e(TAG, "imageClick: Them number =: $position")
                puzzleLayout = PuzzleUtils.getPuzzleLayout(type, pieceSize, position)
                initView()
                puzzleView!!.post { loadPhoto() }

            }

        })
        val lm = LinearLayoutManager(this, HORIZONTAL, false)
        rvView.layoutManager = lm
        rvView.adapter = adapter;
    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadPhoto() {

        /*if (bitmapPaint == null) {
            loadPhotoFromRes()
            return
        }
        val pieces: MutableList<Bitmap> = java.util.ArrayList()
        val count = if (bitmapPaint.size > puzzleLayout.areaCount) puzzleLayout.areaCount else bitmapPaint.size

        for (i in 0 until count) {
            val target: Target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    pieces.add(bitmap)
                    if (pieces.size == count) {
                        if (bitmapPaint.size < puzzleLayout.areaCount) {
                            for (i in 0 until puzzleLayout.areaCount) {
                                puzzleView!!.addPiece(pieces[i % count])
                            }
                        } else {
                            puzzleView!!.addPieces(pieces)
                        }
                    }

                    targets.remove(this@CollageEditActivity)
                }

                override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {}
                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            }
            Picasso.with(this)
                .load("file:///" + bitmapPaint[i])
                .resize(deviceWidth, deviceWidth)
                .centerInside()
                .config(Bitmap.Config.RGB_565)
                .into(target)

            targets.add(target)
        }*/

        if (bitmapPaint == null) {
            loadPhotoFromRes()
            return
        }

        val pieces: MutableList<Bitmap> = java.util.ArrayList()

        val count =
            if (bitmapPaint.size > puzzleLayout.areaCount) puzzleLayout.areaCount else bitmapPaint.size

        for (i in 0 until count) {
            val target: Target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    pieces.add(bitmap)
                    if (pieces.size == count) {
                        if (bitmapPaint.size < puzzleLayout.areaCount) {
                            for (i in 0 until puzzleLayout.areaCount) {
                                puzzleView!!.addPiece(pieces[i % count])
                            }
                        } else {
                            puzzleView!!.addPieces(pieces)
                        }
                    }
                    //targets.remove(this)
                }

                override fun onBitmapFailed(errorDrawable: Drawable?) {}

                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            }
            Picasso.with(this)
                .load("file:///" + bitmapPaint[i])
                .resize(deviceWidth, deviceWidth)
                .centerInside()
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.piclist_icon_default)
                .into(target)


           // targets.add(target)
        }



    }

    private fun loadPhotoFromRes() {
        val pieces: MutableList<Bitmap> = java.util.ArrayList()
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
        val count =
            if (resIds.size > puzzleLayout.areaCount) puzzleLayout.areaCount else resIds.size
        for (i in 0 until count) {
            val target: Target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    pieces.add(bitmap)
                    if (pieces.size == count) {
                        if (resIds.size < puzzleLayout.areaCount) {
                            for (i in 0 until puzzleLayout.areaCount) {
                                puzzleView!!.addPiece(pieces[i % count])
                            }
                        } else {
                            puzzleView!!.addPieces(pieces)
                        }
                    }
                    //targets.remove(this)
                }

                override fun onBitmapFailed(errorDrawable: Drawable?) {}

                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            }
            Picasso.with(this).load(resIds[i]).config(Bitmap.Config.RGB_565).into(target)
            //targets.add(target)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initView() {
        val btnBack = findViewById<View>(R.id.btn_back) as ImageView
        btnBack.setOnClickListener { onBackPressed() }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override public void onClick(View view) {
                 share();
             }
         });*/puzzleView = findViewById<View>(R.id.puzzleView) as PuzzleView

        //degree_seek_bar = findViewById<View>(R.id.degree_seek_bar) as degree_seek_bar

        //TODO the method we can use to change the puzzle view's properties
        puzzleView!!.puzzleLayout = puzzleLayout
        puzzleView!!.isTouchEnable = true
        puzzleView!!.isNeedDrawLine = false
        puzzleView!!.isNeedDrawOuterLine = false
        puzzleView!!.lineSize = 10
        puzzleView!!.lineColor = Color.BLACK
        puzzleView!!.selectedLineColor = Color.BLACK
        puzzleView!!.handleBarColor = Color.BLACK
        puzzleView!!.setAnimateDuration(300)
        puzzleView!!.setOnPieceSelectedListener(OnPieceSelectedListener { piece, position ->
            Snackbar.make(
                puzzleView!!,
                "Piece $position selected",
                Snackbar.LENGTH_SHORT
            ).show()
        })

        this.puzzleView!!.setOnPieceUnSelectedListener {
            Snackbar.make(
                puzzleView!!,
                "Piece  Unselected",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        // currently the SlantPuzzleLayout do not support padding
        puzzleView!!.piecePadding = 10f

        val btnReplace = findViewById<View>(R.id.btn_replace) as ImageView
        val btnRotate = findViewById<View>(R.id.btn_rotate) as ImageView
        val btnFlipHorizontal = findViewById<View>(R.id.btn_flip_horizontal) as ImageView
        val btnFlipVertical = findViewById<View>(R.id.btn_flip_vertical) as ImageView
        val btnBorder = findViewById<View>(R.id.btn_border) as ImageView
        val btnCorner = findViewById<View>(R.id.btn_corner) as ImageView
        btnReplace.setOnClickListener(this@CollageEditActivity)
        btnRotate.setOnClickListener(this@CollageEditActivity as View.OnClickListener?)
        btnFlipHorizontal.setOnClickListener(this@CollageEditActivity as View.OnClickListener?)
        btnFlipVertical.setOnClickListener(this@CollageEditActivity as View.OnClickListener?)
        btnBorder.setOnClickListener(this@CollageEditActivity as View.OnClickListener?)
        btnCorner.setOnClickListener(this@CollageEditActivity as View.OnClickListener?)
        val btnSave = findViewById<View>(R.id.btn_save) as TextView
        btnSave.setOnClickListener { view ->
            val file: File =
                FileUtils.getNewFile(this@CollageEditActivity, "Puzzle")!!
                FileUtils.savePuzzle(puzzleView!!,file,100,object : Callback {
                    override fun onSuccess() {
                        Snackbar.make(view, R.string.prompt_save_success, Snackbar.LENGTH_SHORT)
                            .show()
                    }

                    override fun onError() {
                        Snackbar.make(view, R.string.prompt_save_failed, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                })
        }
        degreeSeekBar.setCurrentDegrees(puzzleView!!.lineSize)
        degreeSeekBar.setDegreeRange(0, 30)
        degreeSeekBar.setScrollingListener(object : DegreeSeekBar.ScrollingListener {
            override fun onScrollStart() {}
            override fun onScroll(currentDegrees: Int) {
                when (controlFlag) {
                   FLAG_CONTROL_LINE_SIZE -> puzzleView!!.lineSize = currentDegrees
                    FLAG_CONTROL_CORNER -> puzzleView!!.pieceRadian = currentDegrees.toFloat()
                }
            }
            override fun onScrollEnd() {}
        })
    }


    private fun share() {
        val file: File = FileUtils.getNewFile(this, "Puzzle")!!
        puzzleView?.let {
            FileUtils.savePuzzle(
                it,
                file,
                100,
                object : Callback {
                    override fun onSuccess() {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        //Uri uri = Uri.fromFile(file);
                        val uri = FileProvider.getUriForFile(
                            this@CollageEditActivity,
                            "com.xiaopo.flying.photolayout.fileprovider", file
                        )
                        if (uri != null) {
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                            shareIntent.type = "image/*"
                            startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    getString(R.string.prompt_share)
                                )
                            )
                        }
                    }

                    override fun onError() {
                        Snackbar.make(puzzleView!!, R.string.prompt_share_failed, Snackbar.LENGTH_SHORT)
                            .show()
                    }

                })
        }
    }

    //private void showSelectedPhotoDialog() {PhotoPicker.newInstance().setMaxCount(1).pick(this);}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.DEFAULT_REQUEST_CODE && resultCode == RESULT_OK) {
            val paths: List<String>? = data!!.getStringArrayListExtra(Constants.PATHS)
            val path = paths!![0]
            val target: Target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    puzzleView!!.replace(bitmap, "")
                    Snackbar.make(puzzleView!!, "Replace Failed!", Snackbar.LENGTH_SHORT).show()

                }

                override fun onBitmapFailed(errorDrawable: Drawable?) {
                    Snackbar.make(puzzleView!!, "Replace Failed!", Snackbar.LENGTH_SHORT).show()
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            }
            Glide.with(this)
                .load("file:///$path") //                    .resize(deviceWidth, deviceWidth)
                .centerInside() //                    .config(Bitmap.Config.RGB_565)
                .into((target as ImageView))
        }

    }






    override fun onClick(view: View?) {

        when (view!!.getId()) {
            R.id.btn_replace -> {


            }
            R.id.btn_rotate -> puzzleView!!.rotate(90f)
            R.id.btn_flip_horizontal -> puzzleView!!.flipHorizontally()
            R.id.btn_flip_vertical -> puzzleView!!.flipVertically()
            R.id.btn_border -> {
                controlFlag = FLAG_CONTROL_LINE_SIZE
                puzzleView!!.isNeedDrawLine = !puzzleView!!.isNeedDrawLine
                if (puzzleView!!.isNeedDrawLine) {
                    degreeSeekBar!!.visibility = View.VISIBLE
                    degreeSeekBar.setCurrentDegrees(puzzleView!!.lineSize)
                    degreeSeekBar.setDegreeRange(0, 30)
                } else {
                    degreeSeekBar!!.visibility = View.INVISIBLE
                }
            }
            R.id.btn_corner -> {
                if (controlFlag == FLAG_CONTROL_CORNER && degreeSeekBar!!.visibility === View.VISIBLE) {
                    degreeSeekBar!!.visibility = View.INVISIBLE
                    return
                }
                degreeSeekBar!!.setCurrentDegrees(puzzleView!!.pieceRadian.toInt())
                controlFlag = FLAG_CONTROL_CORNER
                degreeSeekBar.visibility = View.VISIBLE
                degreeSeekBar.setDegreeRange(0, 100)
            }
        }


    }
}