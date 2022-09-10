package com.elearning.rekamiacademy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elearning.rekamiacademy.data.local.entity.ArticleEntity
import com.elearning.rekamiacademy.databinding.ItemForYouBinding
import com.elearning.rekamiacademy.databinding.ItemTodayNewsBinding
import com.elearning.rekamiacademy.util.ImageLoader.loadImage
import com.elearning.rekamiacademy.util.TextLoader.loadData

class NewsForYouAdapter(private val callback : NewsForYouCallback) : ListAdapter<ArticleEntity, NewsForYouAdapter.MainViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleEntity>(){
            override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: ArticleEntity,
                newItem: ArticleEntity,
            ): Boolean =
                when{
                    oldItem.content != newItem.content -> false
                    oldItem.released != newItem.released -> false
                    oldItem.title != newItem.title -> false
                    oldItem.urlImage != newItem.urlImage -> false
                    else -> true
                }
        }

    }


    interface NewsForYouCallback{
        fun onClickForYou(article : ArticleEntity)
    }

    inner class MainViewHolder(private val binding : ItemForYouBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article : ArticleEntity){
            binding.apply {
                itemForYou.setOnClickListener { callback.onClickForYou(article) }
                tvItemTitle.loadData(article.title)
                tvItemDesc.loadData(article.content)
                tvItemHours.loadData(article.released)
                imgItemImageUrl.loadImage(itemView.context,article.urlImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemForYouBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}