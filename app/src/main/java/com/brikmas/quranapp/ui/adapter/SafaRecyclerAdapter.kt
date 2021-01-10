package com.brikmas.quranapp.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.brikmas.quranapp.R
import com.brikmas.quranapp.model.Para
import com.brikmas.quranapp.model.Safa
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson


class SafaRecyclerAdapter(context: Context, safaSelector: ISafaSelector) : RecyclerView.Adapter<SafaRecyclerAdapter.ViewHolder>() {

    private val TAG = "SafaRecyclerAdapter"
    private var mList: List<Safa> = listOf()
    private var mContext: Context
    private val mSafaSelector: ISafaSelector

    init {
        mContext = context
        mSafaSelector = safaSelector
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SafaRecyclerAdapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.safa_item_layout, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SafaRecyclerAdapter.ViewHolder, i: Int) {
        var item = mList.get(i)
        viewHolder.title.text = item.name
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setList(list: List<Safa>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var img: ImageView
        var title: TextView
        var isClick = false

        init {
            title = itemView.findViewById(R.id.safa_item_title)
            img = itemView.findViewById(R.id.safa_item_image)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if(!isClick){
                mSafaSelector.onSafaSelected(mList.get(adapterPosition))
                isClick = true
            }
            android.os.Handler(Looper.myLooper()!!).postDelayed({
                isClick = false
            },1500)
        }

        
    }

    interface ISafaSelector {
        fun onSafaSelected(data: Safa)
    }
}