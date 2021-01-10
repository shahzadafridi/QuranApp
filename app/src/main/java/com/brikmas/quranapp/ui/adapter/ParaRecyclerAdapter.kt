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
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


class ParaRecyclerAdapter(context: Context, paraSelector: IParaSelector) : RecyclerView.Adapter<ParaRecyclerAdapter.ViewHolder>() {

    private val TAG = "ParaRecyclerAdapter"
    private var mList: List<Para> = listOf()
    private var mContext: Context
    private val mParaSelector: IParaSelector

    init {
        mContext = context
        mParaSelector = paraSelector
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ParaRecyclerAdapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.quran_para_item_layout, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ParaRecyclerAdapter.ViewHolder, i: Int) {
        var item = mList.get(i)
        viewHolder.img.setImageResource(item.image)
        viewHolder.title.text = item.name
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setList(list: List<Para>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var img: ImageView
        var title: TextView
        var isClick = false

        init {
            title = itemView.findViewById(R.id.para_item_layout_title)
            img = itemView.findViewById(R.id.para_item_layout_img)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if(!isClick){
                mParaSelector.onParaSelected(mList.get(adapterPosition))
                isClick = true
            }
            android.os.Handler(Looper.myLooper()!!).postDelayed({
                isClick = false
            },1500)
        }

        
    }

    interface IParaSelector {
        fun onParaSelected(data: Para)
    }
}