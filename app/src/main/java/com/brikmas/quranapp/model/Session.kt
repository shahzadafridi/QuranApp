package com.brikmas.quranapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Session(
    var id: String = "",
    var deviceId: String = "",
    var userType:String = "",
    var email: String = "",
    var status: String = "",
    var login_type: String = "",
    var isLogin: Boolean = false
)