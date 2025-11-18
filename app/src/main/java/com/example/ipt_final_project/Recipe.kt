package com.example.ipt_final_project

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Recipe(
    var id: String? = null,
    var title: String = "",
    var ingredients: String = "",
    var instructions: String = "",
    var imageUrl: String? = null,
    @ServerTimestamp
    val timestamp: Date? = null
) : Parcelable