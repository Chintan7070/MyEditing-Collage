package com.example.myediting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.bumptech.glide.Glide
import com.example.myediting.adapter.GridAdapter
import com.example.myediting.adapter.ImageAdapter
import com.example.myediting.utils.ClickInterface
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.selected_images.view.*
import java.io.Serializable

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {
    private var int_position: Int = 0
    private lateinit var absolutePathOfImage: String
    val TAG = "MainActivity"
    var al_images: ArrayList<RearImagesModel> = java.util.ArrayList<RearImagesModel>()
    var boolean_folder = false
    var selectedImageList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                && ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {

                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
            }
        } else {
            Log.e(TAG, "onCreate: Permition already Granted in start")
            readAllImageFromDevice()
            //val asynkcTaskRunner = AsyncTaskRunner()
        }

        gallery_next.setOnClickListener {
            if (selectedImageList.size >= 2) {

                    val intent = Intent(this, CollageEditActivity::class.java)
                    val args = Bundle()
                    if (selectedImageList.size <= 3){
                        intent.putExtra("type",0)
                    }else{
                        intent.putExtra("type",1)
                    }
                    args.putSerializable("ARRAYLIST", selectedImageList as Serializable)
                    intent.putExtra("BUNDLE", args)
                    startActivity(intent)

            }else{
                Toast.makeText(this@MainActivity,"Please select minimum 2 images",Toast.LENGTH_SHORT).show()
            }


        }
    }

    //    companion object {
    fun readAllImageFromDevice() {

        val cursor: Cursor
        var column_index_data: Int
        var column_index_folder_name: Int
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf<String>(
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        val orderBy = MediaStore.Images.Media.DATE_TAKEN

        cursor = contentResolver.query(uri, projection, null, null, orderBy + " DESC")!!

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        column_index_folder_name =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        Log.e(
            TAG,
            "readAllImageFromDevice: ColumnIndecData==" + column_index_data + "Curor value =-- " + cursor
        )
        Log.e(
            TAG,
            "readAllImageFromDevice: ColumnIndec folder name ==" + column_index_folder_name
        )


        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            //Log.e("Column", absolutePathOfImage);
            //Log.e("Folder", cursor.getString(column_index_folder_name));
            // Log.e(TAG, "fn_imagespath: ALlImages Size:== " + al_images.size());
            for (i in al_images.indices) {

                //Log.e(TAG, "fn_imagespath: ALlImages Size:=FFF " + al_images.get(i).getStr_folder());
                //Log.e(TAG, "fn_imagespath: ALlImages Size:=CCC " + cursor.getString(column_index_folder_name));
                if (al_images[i].folder != null && cursor.getString(column_index_folder_name) != null
                ) {
                    // Toast.makeText(this,"data:= "+al_images.get(i).getStr_folder(),Toast.LENGTH_LONG).show();
                    Log.e(TAG, "fn_imagespath: folder name: ++ " + al_images.get(i).folder);
                    //.e(TAG, "fn_imagespath: ALlImages Size:=CCC++ " + cursor.getString(column_index_folder_name));
                    if (al_images[i].folder.equals(cursor.getString(column_index_folder_name))) {

                        boolean_folder = true
                        int_position = i
                        break
                    } else {
                        boolean_folder = false
                    }
                } else {
                    boolean_folder = false
                    //Log.e(TAG, "fn_imagespath: ALlImages Size:=FFF-- " + al_images.get(i).getStr_folder());
                    //Log.e(TAG, "fn_imagespath: ALlImages Size:=CCC-- " + cursor.getString(column_index_folder_name));
                    //Toast.makeText(MainActivity.this, "null", Toast.LENGTH_LONG).show();
                }
            }
            if (boolean_folder) {
                val al_path = ArrayList<String>()
                al_path.addAll(al_images.get(int_position).imagePath)
                al_path.add(absolutePathOfImage)
                al_images.get(int_position).imagePath = al_path
            } else {
                val al_path = ArrayList<String>()
                al_path.add(absolutePathOfImage)
                if (cursor.getString(column_index_folder_name) != null) {
//                    Log.e(TAG, "readAllImageFromDevice: crash Line ::====" + cursor.getString(column_index_folder_name) + " pathes ;" + al_path)
                    val obj_model: RearImagesModel =
                        RearImagesModel(cursor.getString(column_index_folder_name), al_path)
                    al_images.add(obj_model)
                }
            }
        }

        imageBack.setOnClickListener {
            if (gridViewForImages.visibility.equals(View.VISIBLE)) {
                gridViewForImages.visibility = View.GONE
                rvViewForFolder.visibility = View.VISIBLE
                textView_header.text = "Select Album"
            } else {
                finish();
            }
        }

        val adapter = GridAdapter(this@MainActivity, al_images, 0, object : ClickInterface {
            override fun folderClick(position: Int, imagePath: java.util.ArrayList<String>) {

                gridViewForImages.visibility = View.VISIBLE
                textView_header.text = al_images[position].folder.toString()
                rvViewForFolder.visibility = View.GONE

                val imageAdapter =
                    ImageAdapter(this@MainActivity, position, al_images, object : ClickInterface {
                        override fun folderClick(
                            position: Int, imagePath: java.util.ArrayList<String>
                        ) {
                        }

                        override fun imageClick(folderPosition: Int, position: Int) {
                            val selectedImagePath =
                                al_images[folderPosition].imagePath[position].toString()
                            Log.e(
                                TAG,
                                "imageClick: Cliced Image path to selected" + selectedImagePath
                            )
                            setSelectesImageList(al_images, folderPosition, position)
                        }

                    })
                val lm = LinearLayoutManager(this@MainActivity, VERTICAL, false)
                gridViewForImages.layoutManager = lm
                gridViewForImages.layoutManager = GridLayoutManager(this@MainActivity, 3)
                gridViewForImages.adapter = imageAdapter

            }

            override fun imageClick(folderPosition: Int, position: Int) {
                TODO("Not yet implemented")
            }
        })
        val lm = LinearLayoutManager(this@MainActivity, VERTICAL, false)
        rvViewForFolder.layoutManager = lm
        rvViewForFolder.layoutManager = GridLayoutManager(this, 3)
        rvViewForFolder.adapter = adapter


//        }
    }

    private fun setSelectesImageList(

        alImages: java.util.ArrayList<RearImagesModel>,
        folderPosition: Int,
        position: Int
    ) {
        if (selectedImageList.size <= 8){

            selectedImageList.add(alImages[folderPosition].imagePath[position])
            val inflate = LayoutInflater.from(this).inflate(R.layout.selected_images, null, false)
            val imageView = inflate.selectedImages
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            inflate.imageView_delete.setOnClickListener {
                selected_image_linear.removeView(inflate)
                selectedImageList.remove(alImages[folderPosition].imagePath[position])
                updateTxtTotalImage()
            }
            Glide.with(this).load(alImages[folderPosition].imagePath[position])
                .placeholder(R.drawable.piclist_icon_default).into(imageView)
            selected_image_linear.addView(inflate)
            updateTxtTotalImage()
            sendScroll()
        }else{
            Toast.makeText(this@MainActivity,"Limit 9 images",Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateTxtTotalImage() {
        gallery_max.setText(
            String.format(
                resources.getString(R.string.text_images), *arrayOf<Any>(
                    Integer.valueOf(selectedImageList.size)
                )
            )
        )
        if (selectedImageList.size == 0) {
            horizontalScrollView.visibility = View.GONE
        } else {
            horizontalScrollView.visibility = View.VISIBLE
        }
    }

    private fun sendScroll() {
        val handler = Handler()
        Thread { handler.post { horizontalScrollView.fullScroll(66) } }.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) === PackageManager.PERMISSION_GRANTED)
                        && ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) === PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onBackPressed() {
        if (gridViewForImages.visibility.equals(View.VISIBLE)) {
            gridViewForImages.visibility = View.GONE
            rvViewForFolder.visibility = View.VISIBLE
            textView_header.text = "Select Album"
        } else {
            super.onBackPressed()
        }
    }
}


/*class AsyncTaskRunner : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg params: Void?): Void? {

        runOnUiThread(Runnable { MainActivity.readAllImageFromDevice() })
        return null
    }

}*/

