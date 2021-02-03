package com.brikmas.quranapp.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.brikmas.quranapp.R
import com.brikmas.quranapp.ui.adapter.AuthFragmentAdapter
import com.brikmas.quranapp.util.Constants
import com.brikmas.quranapp.viewModel.AuthViewModel
import com.example.roadi.util.ActivityStack
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.auth_activity.*

class AuthActivity : AppCompatActivity() , TabLayout.OnTabSelectedListener {

    val TAG: String = "AuthActivity"
    var authViewModel: AuthViewModel? = null
    var authFragmentAdapter: AuthFragmentAdapter? = null
    var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
//        clearLightStatusBar(ContextCompat.getColor(this, android.R.color.white))

        auth_activity_tabLayout.addOnTabSelectedListener(this)
        authFragmentAdapter = AuthFragmentAdapter(MainActivity@this)
        auth_activity_viewPager2.adapter = authFragmentAdapter
        tabLayoutMediator = TabLayoutMediator(
            auth_activity_tabLayout,
            auth_activity_viewPager2,
            { tab, position ->
                var view = LayoutInflater.from(this).inflate(R.layout.tab_item_layout, null)
                var textView = view.findViewById<TextView>(R.id.tab_item_title_tv)
                when (position) {
                    0 -> {
                        tab.customView = view
                        textView.text = Constants.authTabTitles.get(0)
                    }
                    1 -> {
                        tab.customView = view
                        textView.text = Constants.authTabTitles.get(1)
                    }
                }
            })

        tabLayoutMediator!!.attach()

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        (tab!!.customView as TextView).typeface = ResourcesCompat.getFont(
            this,
            R.font.poppins_semibold
        )
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        (tab!!.customView as TextView).typeface = ResourcesCompat.getFont(
            this,
            R.font.poppins_regular
        )
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    fun onSkip(view: View){
        authViewModel!!.updateSession(this,"")
        ActivityStack.startMainActivity(this@AuthActivity)
    }

    fun clearLightStatusBar(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor =  color
        }
    }

    override fun onStart() {
        super.onStart()

    }

}