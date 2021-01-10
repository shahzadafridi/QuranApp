package com.brikmas.quranapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Para(
    val id: String,
    val name: String,
    val image: Int
): Parcelable