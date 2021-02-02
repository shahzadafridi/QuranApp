package com.brikmas.quranapp.util

object Constants {


    val FIRESTORE_COLLECTION_VERSION: String = "version"

    /*
        * MainActivity - Authentication Tabs
        */
    var authTabTitles: List<String> = arrayListOf("Sign Up", "Sign In")
    val AUTH_GOOGLE = "google"; val AUTH_FACEBOOK = "facebook";  val AUTH_APP= "app";

    /*
    * SharedPrefernces
    */
    val SHARED_PREF_FILE = "app"
    val SESSION_KEY = "user_session"
    val VERSION_DIALOG_KEY = "version_dialog"



    /*
    * @Google Maps
    */

    val UPDATE_INTERVAL: Long = 60 * 1000 /* 60 secs */
    val FASTEST_INTERVAL: Long = 60 * 1000 /* 60 sec */

    /*
    * @Firebase
    */

    val PARK_STATUS_OCCUPIED = "occupied"
    val PARK_STATUS_VACANT = "vacant"

    val USER_TYPE_ANONYMOUS = "anonymous"
    val USER_TYPE_AUTHENTIC = "authentic"

    // query for searching location under radius value/km e-g 1km
    val QUERY_RADIUS = 0.5

    //Collections
    val COLLECTION_USER = "user"
    var FIREBASE_STORAGE = "quran"
    var FIRESTORE_COLLECTION = "quran"
    var FIRESTORE_DOCUMENT = "paras"

}