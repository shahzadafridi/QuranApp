package com.example.roadi.util

import android.content.Context
import android.content.Intent
import com.brikmas.quranapp.model.Para
import com.brikmas.quranapp.model.Safa
import com.brikmas.quranapp.ui.activity.AuthActivity
import com.brikmas.quranapp.ui.activity.MainActivity
import com.brikmas.quranapp.ui.activity.SafaViewActivity
import com.brikmas.quranapp.ui.activity.SafasActivity

class ActivityStack {

    companion object{

        fun startSafaActivity(context: Context, para: Para){
            var intent = Intent(context, SafasActivity::class.java)
            intent.putExtra("para",para)
            context.startActivity(intent)
        }

        fun startSafaViewActivity(context: Context, safa: Safa){
            var intent = Intent(context, SafaViewActivity::class.java)
            intent.putExtra("safa",safa)
            context.startActivity(intent)
        }

        fun startMainActivity(context: Context){
            var intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }

        fun startAuthActivity(context: Context){
            var intent = Intent(context, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

}