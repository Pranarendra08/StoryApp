package com.example.storyapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.api.response.ListStoryItem
import com.example.storyapp.databinding.StoryAdapterBinding
import com.example.storyapp.helper.dateFormat

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) { //private val listStory: List<ListStoryItem>
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemCLicked(data: ListStoryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StoryAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }

        holder.itemView.setOnClickListener {
            data?.let { it1 -> onItemClickCallback.onItemCLicked(it1) }
        }
    }

    class ViewHolder(private val binding: StoryAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: ListStoryItem) {
                binding.tvItemName.text = data.name
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .into(binding.ivItemPhoto)
                binding.tvItemDate.text = dateFormat(data.createdAt)
            }
        }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}