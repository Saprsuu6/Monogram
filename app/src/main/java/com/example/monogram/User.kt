package com.example.monogram

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

data class User(
    val photoLink: String,
    var photoBitmapLocal: Bitmap?,
    val name: String,
    val phone: String,
    val onlineStatus: Boolean
) : MutableLiveData<User>(), Serializable {
    constructor() : this("", null, "", "", false)

    fun update() {
        value = this
    }
}