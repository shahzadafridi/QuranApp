package com.brikmas.quranapp.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.brikmas.quranapp.model.Session
import com.brikmas.quranapp.util.Constants
import com.google.gson.Gson

class SharedPrefRepository(context: Context) {

    var mContext: Context
    var sharedPreferences: SharedPreferences
    var gson: Gson

    init {
        mContext = context
        gson = Gson()
        sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE)
    }

    companion object {
        fun provideSharedRepository(context: Context): SharedPrefRepository {
            return SharedPrefRepository(context)
        }
    }

    fun updateSession(session: Session) {
        var str_session = gson.toJson(session)

        sharedPreferences.edit().
        putString(Constants.SESSION_KEY, str_session).
        apply()
    }

    fun getSession(): Session? {
        var str_session = sharedPreferences.getString(Constants.SESSION_KEY,null)
        if (str_session != null){
            var session = gson.fromJson<Session>(str_session,Session::class.java)
            return session
        }
        return null
    }

    fun isVersionDialogShow(isShow: Boolean){
        sharedPreferences.edit().
        putBoolean(Constants.VERSION_DIALOG_KEY, isShow).
        apply()
    }

    fun getVersionDialogShow(): Boolean {
        var versionDialogShow = sharedPreferences.getBoolean(Constants.VERSION_DIALOG_KEY,true)
        return versionDialogShow
    }
}