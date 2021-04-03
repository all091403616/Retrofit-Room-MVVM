package com.example.retrofittest.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofittest.R
import com.example.retrofittest.databinding.NewsItemLayoutBinding
import com.example.retrofittest.model.datamodel.News
import com.example.retrofittest.view.ListFragmentDirections

class NewsListAdapter(var newsList:ArrayList<News>?) :RecyclerView.Adapter<NewsListAdapter.VH>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val binding:NewsItemLayoutBinding=DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.news_item_layout,
                parent,
                false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.newsImg.loadImage(newsList?.get(position)?.image?:"")
        holder.binding.newsTitle.text= newsList?.get(position)?.title
        holder.binding.newsDescription.text= newsList?.get(position)?.description
        holder.binding.newsDate.text= newsList?.get(position)?.published?.replace(" +0000","")

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(newsList?.get(position)?.rowId!!))
        }
    }

    override fun getItemCount(): Int {
        return newsList?.size?:0
    }

    fun refresh(news:ArrayList<News>){
        newsList=news
        notifyDataSetChanged()
    }

    class VH(var binding:NewsItemLayoutBinding):RecyclerView.ViewHolder(binding.root)
}