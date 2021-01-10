package com.brikmas.quranapp.util

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.IOException

object Utility {


    fun onSystemUiTransparentNoLimit(window: Window){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun onSystemUiTransparentInScreen(window: Window){
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
    }

    fun onStatusNavigationColor(parentActivity: AppCompatActivity, color: Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            parentActivity.window.navigationBarColor = ContextCompat.getColor(
                parentActivity,
                color
            )
            parentActivity.window.statusBarColor = ContextCompat.getColor(
                parentActivity,
                color
            )
        }
    }

    fun getDeviceId(context: Context): String?{
        var android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
        return android_id
    }

    fun onCreateDialog(context: Context, layout: Int, cancelable: Boolean): Dialog? {
        val metrics = context.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        val dialog = Dialog(context, R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(cancelable)
        return dialog
    }

    fun printDebugLog(tag: String, message: String){
        Log.d(tag,message)
    }

    fun printErrorLog(tag: String, message: String){
        Log.e(tag,message)
    }

    fun printTestLog(message: String){
        Log.d("test",message)
    }

    fun printToast(context: Context, message: String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}