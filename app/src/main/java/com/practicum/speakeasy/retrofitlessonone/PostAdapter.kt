package com.practicum.speakeasy.retrofitlessonone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practicum.speakeasy.retrofitlessonone.databinding.PostItemBinding
import com.practicum.speakeasy.retrofitlessonone.retrofit.post.Post

class PostAdapter: ListAdapter<Post, PostAdapter.Holder>(Comparator()) {

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = PostItemBinding.bind(view)
        fun bind(item: Post) = with(binding) {
            tvTitle.text = item.title
            tvBody.text = item.body

            val numLikes = item.reactions.likes.toString()
            val numDislikes = item.reactions.dislikes.toString()
            tvNumLikes.text = numLikes
            tvNumDislikes.text = numDislikes
        }
    }

    class Comparator: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}