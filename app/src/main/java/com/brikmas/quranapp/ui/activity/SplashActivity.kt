package com.brikmas.quranapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import com.brikmas.quranapp.R
import com.brikmas.quranapp.util.Utility
import com.brikmas.quranapp.viewModel.AuthViewModel
import com.example.roadi.util.ActivityStack
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), Animation.AnimationListener {

    var TAG = "SplashActivity"
    var authViewModel: AuthViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utility.onSystemUiTransparentNoLimit(window)
        setContentView(R.layout.activity_splash)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        var anim_left_to_right = AnimationUtils.loadAnimation(this,R.anim.left_to_right)
        splash_logo.startAnimation(anim_left_to_right)
        anim_left_to_right.setAnimationListener(this)

    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {
        Handler(Looper.getMainLooper()).postDelayed({
            var session = authViewModel!!.getSession(SplashActivity@this)
            if (session != null){
                if (session.isLogin == true){
                    ActivityStack.startMainActivity(SplashActivity@this)
                }else{
                    ActivityStack.startAuthActivity(SplashActivity@this)
                }
            }else{
                ActivityStack.startAuthActivity(SplashActivity@this)
            }
        },2000)

    }

    override fun onAnimationStart(animation: Animation?) {

    }
}