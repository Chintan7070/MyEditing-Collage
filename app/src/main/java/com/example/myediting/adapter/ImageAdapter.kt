package com.example.myediting.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myediting.R
import com.example.myediting.RearImagesModel
import com.example.myediting.utils.ClickInterface
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter(
    var activity: Activity,
    var folderPosition: Int,
    var imagePath: ArrayList<RearImagesModel>,
    var imageClick: ClickInterface
) : RecyclerView.Adapter<ImageAdapter.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        val view = LayoutInflater.from(activity).inflate(R.layout.item_image, parent, false)
        return ViewData(view)
    }

    override fun getItemCount(): Int {
        return imagePath[folderPosition].imagePath.size
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        Log.e("TAG", "onBindViewHolder: all image is loaded :===== "+imagePath )
        holder.txtFieldName.visibility = View.GONE
        Glide.with(activity)
            .load("file://" + imagePath[folderPosition].imagePath[position])
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            imageClick.imageClick(folderPosition,position)
        }
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.imgView
        val txtFieldName = itemView.txtFieldName

    }
}