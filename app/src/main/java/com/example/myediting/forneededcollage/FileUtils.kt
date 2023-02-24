package com.example.myediting.forneededcollage

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import com.squareup.picasso.Callback
import com.xiaopo.flying.puzzle.PuzzleView
//import com.squareup.picasso.Callback
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by snowbean on 16-8-5.
 */
object FileUtils {
    private const val TAG = "FileUtils"
    fun getFolderName(name: String?): String {
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            name
        )
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return ""
            }
        }
        return mediaStorageDir.absolutePath
    }

    private val isSDAvailable: Boolean
        private get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun getNewFile(context: Context, folderName: String): File? {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
        val timeStamp = simpleDateFormat.format(Date())
        val path: String
        path = if (isSDAvailable) {
            getFolderName(folderName) + File.separator + timeStamp + ".jpg"
        } else {
            context.filesDir.path + File.separator + timeStamp + ".jpg"
        }
        return if (TextUtils.isEmpty(path)) {
            null
        } else File(path)
    }

    fun createBitmap(puzzleView: PuzzleView): Bitmap {
        puzzleView.clearHandling()
        puzzleView.invalidate()
        val bitmap = Bitmap.createBitmap(
            puzzleView.getWidth(),
            puzzleView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        puzzleView.draw(canvas)
        return bitmap
    }

    fun savePuzzle(puzzleView: PuzzleView, file: File, quality: Int, callback: Callback?) {
        var bitmap: Bitmap? = null
        var outputStream: FileOutputStream? = null
        try {
            bitmap = createBitmap(puzzleView)
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            if (!file.exists()) {
                Log.e(TAG, "notifySystemGallery: the file do not exist.")
                return
            }
            try {
                MediaStore.Images.Media.insertImage(
                    puzzleView.getContext().getContentResolver(),
                    file.absolutePath, file.name, null
                )
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            puzzleView.getContext()
                .sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
            if (callback != null) {
                callback.onSuccess()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            if (callback != null) {
                callback.onError()
            }
        } finally {
            bitmap?.recycle()
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}