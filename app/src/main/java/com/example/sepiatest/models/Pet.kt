package com.example.sepiatest.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//@Parcelize
data class Pet(
    val content_url: String,
    val date_added: String,
    val image_url: String,
    val title: String
)/*: Parcelable*/