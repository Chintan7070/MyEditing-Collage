package com.example.myediting.utils

import android.graphics.Color

class Constants {
    var PHOTOS = "photos"
    var CONFIGURE = "configure"
    var DEFAULT_ALBUM_TITLE = "请选择一个相册"
    var DEFAULT_PHOTO_TITLE = "请选择相片"
    var DEFAULT_MAX_NOTICE = "主人，包包装不下了～"

    companion object {
        const val KEY_DATA_RESULT = "selectedImageList"
        const val PATHS = "paths"
        const val DEFAULT_REQUEST_CODE = 94
        const val DEFAULT_REQUEST_PERMISSION_CODE = 77
        const val DEFAULT_TOOLBAR_COLOR = Color.BLACK
        const val DEFAULT_TITLE_COLOR = Color.WHITE
        const val DEFAULT_STATUS_BAR_COLOR = Color.BLACK
        const val DEFAULT_MAX_COUNT = 10
        const val DEFAULT_DIVIDER_LINE_SIZE = 1
        const val DEFAULT_DIVIDER_LINE_COLOR = Color.BLACK
    }
}