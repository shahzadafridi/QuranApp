package com.brikmas.quranapp.ui.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.brikmas.quranapp.R
import com.brikmas.quranapp.model.Safa
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.safa_item_layout.*


class SafaViewActivity : AppCompatActivity() {

    val TAG = "SafaViewActivity"
    var imageView: PhotoView? = null
    var title: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safa_view)

        imageView = findViewById(R.id.safa_view_iv)
        title = findViewById(R.id.safa_view_title)

        var safa = intent.getParcelableExtra<Safa>("safa")
        Log.e(TAG,safa!!.image)
        title!!.text = safa.name
        Glide.with(this)
            .asBitmap()
            .load(safa.image)
            .apply(
                RequestOptions.centerCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.quran_logo_150)
                    .error(R.drawable.quran_logo_150)
                    .priority(Priority.HIGH)
            )
            .into(object : CustomTarget<Bitmap?>() {

                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                    imageView!!.setImageBitmap(resource)
                }
            })

    }

    fun onBack(view: View) {
        onBackPressed()
    }
}