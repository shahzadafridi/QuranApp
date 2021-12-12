package com.brikmas.quranapp.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.brikmas.quranapp.R
import com.brikmas.quranapp.model.Para
import com.brikmas.quranapp.model.Safa
import com.brikmas.quranapp.ui.adapter.SafaRecyclerAdapter
import com.brikmas.quranapp.util.ResourceState
import com.brikmas.quranapp.util.Utility
import com.brikmas.quranapp.viewModel.MainViewModel
import com.example.roadi.util.ActivityStack
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_safas.*
import java.util.*
import kotlin.Comparator

class SafasActivity : AppCompatActivity(), SafaRecyclerAdapter.ISafaSelector {

    val TAG = "MainActivity"
    var adapter: SafaRecyclerAdapter? = null
    var mainViewModel: MainViewModel? = null
    var loadingProgressBar: KProgressHUD? = null
    var viewPager2: ViewPager2? = null
    var list: List<Safa> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safas)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewPager2 = findViewById(R.id.safa_para_viewpager) as ViewPager2
        viewPager2!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            viewPager2!!.layoutDirection = ViewCompat.LAYOUT_DIRECTION_RTL
        }
        adapter = SafaRecyclerAdapter(this, this)
        viewPager2!!.adapter = adapter
        viewPager2!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                safa_title.text = list.get(position).name
            }
        })

        var para = intent.getParcelableExtra<Para>("para")
        mainViewModel!!.getSafas(para!!.id)

        mainViewModel!!.safaLiveData.observe(this, Observer {
            when (it.status) {
                ResourceState.SAFAS -> {
                    sortList(it.data!!.toMutableList())
                    Log.e(TAG, "Load Safas")
                }
                ResourceState.ERROR -> {
                    Toast.makeText(this@SafasActivity, it.message, Toast.LENGTH_SHORT).show()
                    Utility.printErrorLog(TAG, "ERROR : ${it.message}")
                }
            }
        })

        loadingProgressBar = KProgressHUD.create(this@SafasActivity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.1f)

        loadingProgressBar!!.setDetailsLabel("loading data...")
        loadingProgressBar!!.show()

    }

    fun sortList(data: MutableList<Safa>) {

        Collections.sort(data, Comparator<Safa> { obj1, obj2 ->
            // ## Ascending order
            var id1 = obj1.id.toInt()
            var id2 = obj2.id.toInt()
            id1.compareTo(id2)
            //Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

            // ## Descending order
            // obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            // Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
        })
        list = data
        loadingProgressBar!!.dismiss()
        adapter!!.setList(data)
    }

    override fun onSafaSelected(data: Safa) {
        Utility.printDebugLog(TAG, data.name + " selected.")
//        ActivityStack.startSafaViewActivity(this@SafasActivity,data)
    }

    fun onBack(view: View) {
        onBackPressed()
    }
}