package com.elearning.rekamiacademy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.databinding.ItemTodayNewsBinding
import com.elearning.rekamiacademy.util.ImageLoader.loadImage
import com.elearning.rekamiacademy.util.TextLoader.loadData

class NewsHeadlineAdapter(private val callback : NewsHeadlineCallback) : ListAdapter<ArticleEntity, NewsHeadlineAdapter.MainViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleEntity>(){
            override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: ArticleEntity,
                newItem: ArticleEntity,
            ): Boolean =
                when{
                    oldItem.title != newItem.title -> false
                    oldItem.urlImage != newItem.urlImage -> false
                    else -> true
                }
        }

    }


    interface NewsHeadlineCallback{
        fun onClickTopHeadline(article : ArticleEntity)
    }

    inner class MainViewHolder(private val binding : ItemTodayNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article : ArticleEntity){
            binding.apply {
                cardView.setOnClickListener { callback.onClickTopHeadline(article) }
                tvItemHorizonTitle.loadData(article.title)
                imgItemHorizonImageUrl.loadImage(itemView.context,article.urlImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemTodayNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}