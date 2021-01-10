package com.brikmas.quranapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.brikmas.quranapp.R
import com.brikmas.quranapp.model.Para
import com.brikmas.quranapp.ui.adapter.ParaRecyclerAdapter
import com.brikmas.quranapp.util.ResourceState
import com.brikmas.quranapp.util.Utility
import com.brikmas.quranapp.viewModel.AuthViewModel
import com.brikmas.quranapp.viewModel.MainViewModel
import com.example.roadi.util.ActivityStack
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_in.*


class MainActivity : AppCompatActivity(), ParaRecyclerAdapter.IParaSelector {

    val TAG = "MainActivity"
    var adapter: ParaRecyclerAdapter? = null
    var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        main_para_rv.layoutManager = GridLayoutManager(this,2)
        adapter = ParaRecyclerAdapter(this,this)
        main_para_rv.adapter = adapter

        mainViewModel!!.getParas().observe(this, Observer {
            when (it.status) {
                ResourceState.PARAS -> {
                    adapter!!.setList(it.data!!)
                    Log.e(TAG,"Load paras")
                }
                ResourceState.ERROR -> {
                    Toast.makeText(this@MainActivity,it.message, Toast.LENGTH_SHORT).show()
                    Utility.printErrorLog(TAG, "ERROR : ${it.message}")
                }
            }
        })
    }



    override fun onParaSelected(data: Para) {
        ActivityStack.startSafaActivity(this@MainActivity,data)
    }
}