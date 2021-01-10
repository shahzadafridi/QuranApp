package com.brikmas.quranapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class User(
    var id: String = "",
    var deviceId: String = "",
    var status: String = "",
    var email: String = "",
    @ServerTimestamp
    var timestamp: Date? = null
)