package com.example.myediting.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myediting.R
import com.example.myediting.RearImagesModel
import com.example.myediting.utils.ClickInterface
import kotlinx.android.synthetic.main.item_image.view.*

class GridAdapter(
    var activity: Activity,
    var allImage: ArrayList<RearImagesModel>,
    var i: Int,
    var clickInterface : ClickInterface
) : RecyclerView.Adapter<GridAdapter.ViewData>() {
    val TAG = "GridAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {

        val view = LayoutInflater.from(activity).inflate(R.layout.item_image, parent, false)
        return ViewData(view)

    }

    override fun getItemCount(): Int {
        return allImage.size
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.txtFieldName.text = allImage[position].folder.toString()+"("+allImage[position].imagePath.size+")"
        Glide.with(activity)
            .load("file://" + allImage[position].imagePath[i])
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.imgView)

        holder.itemView.setOnClickListener {
            clickInterface.folderClick(position, allImage[i].imagePath)
        }
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgView = itemView.imgView
        val txtFieldName = itemView.txtFieldName
    }
}