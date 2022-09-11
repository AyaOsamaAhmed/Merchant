package com.rbt.merchant.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rbt.merchant.R


@BindingAdapter("app:bindImgUrl", "app:bindProgressItem")
fun setGlideImageUrl(image: ImageView, url: String, progressBar: ProgressBar?) {
    Glide.with(image.context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                image.setImageResource(R.mipmap.ic_launcher)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }
        })
      //  .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(image)
}

@BindingAdapter("format", "argId")
fun setFormattedText(textView: TextView, format: String?, argId: Int) {
    if (argId == 0) return
    textView.text = String.format(format!!, textView.resources.getString(argId))
}

