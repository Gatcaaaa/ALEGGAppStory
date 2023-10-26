package com.submisson.aleggappstory.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submisson.aleggappstory.data.response.ListStoryItem
import com.submisson.aleggappstory.databinding.ItemLayoutBinding

class ListAdapter(private val listStory: List<ListStoryItem>
):
    RecyclerView.Adapter<ListAdapter.ListViewHolder>(){
        inner class ListViewHolder(private val binding: ItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(listStoryItem: ListStoryItem){
                    binding.tvName.text = listStoryItem.name
                    binding.tvDescription.text = listStoryItem.description

                    Glide
                        .with(itemView.context)
                        .load(listStoryItem.photoUrl)
                        .fitCenter()
                        .into(binding.ivStoryImage)

//                    binding.itemLayout.setOnClickListener{
//
//                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listStory[position]
        holder.bind(item)
    }
}