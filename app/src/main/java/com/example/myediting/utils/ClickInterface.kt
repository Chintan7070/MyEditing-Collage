package com.example.myediting.utils

import java.util.ArrayList

interface ClickInterface {
    fun folderClick(position: Int, imagePath: ArrayList<String>)
    fun imageClick(folderPosition: Int, position: Int)

}