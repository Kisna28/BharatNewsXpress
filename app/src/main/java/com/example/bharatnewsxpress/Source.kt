package com.example.bharatnewsxpress

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String?,// Make id nullable
    val name: String?
):Parcelable