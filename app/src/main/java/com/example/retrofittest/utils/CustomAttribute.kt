package com.example.retrofittest.utils

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.retrofittest.R
import com.squareup.picasso.Picasso

@BindingAdapter("vog")
fun View.visibleOrGone(state: States){
    when(state){
        States.LOADING->{
            if (this is RecyclerView) visibility=View.GONE
            if (this is ProgressBar) visibility=View.VISIBLE
        }
        else->{
            if (this is RecyclerView) visibility=View.VISIBLE
            if (this is ProgressBar) visibility=View.GONE
        }
    }
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?){
    Picasso.get().load(Uri.parse(url?:""))
            .placeholder(CircularProgressDrawable(this.context))
            .error(R.drawable.news)
            .into(this)
}
