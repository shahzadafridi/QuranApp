package com.brikmas.quranapp.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Safa(
    val id: String = "",
    val name: String="",
    val image: String="",
    @ServerTimestamp
    val created_at: Date? = null
): Parcelable
