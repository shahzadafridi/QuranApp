package com.brikmas.quranapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brikmas.quranapp.R
import com.brikmas.quranapp.model.Para
import com.brikmas.quranapp.model.Safa
import com.brikmas.quranapp.ui.adapter.SafaRecyclerAdapter
import com.brikmas.quranapp.util.ResourceState
import com.brikmas.quranapp.util.Utility
import com.brikmas.quranapp.viewModel.MainViewModel
import com.example.roadi.util.ActivityStack
import kotlinx.android.synthetic.main.activity_safas.*
import java.util.*
import kotlin.Comparator

class SafasActivity : AppCompatActivity(), SafaRecyclerAdapter.ISafaSelector {

    val TAG = "MainActivity"
    var adapter: SafaRecyclerAdapter? = null
    var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safas)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        safa_para_rv.layoutManager = LinearLayoutManager(this)
        adapter = SafaRecyclerAdapter(this, this)
        safa_para_rv.adapter = adapter
        var para = intent.getParcelableExtra<Para>("para")
        safa_title.text = para!!.id+" کے صفحات"
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

        adapter!!.setList(data)
    }

    override fun onSafaSelected(data: Safa) {
        ActivityStack.startSafaViewActivity(this@SafasActivity,data)
    }

    fun onBack(view: View) {
        onBackPressed()
    }
}