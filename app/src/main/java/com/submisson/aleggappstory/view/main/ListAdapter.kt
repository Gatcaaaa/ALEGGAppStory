package com.submisson.aleggappstory.view.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submisson.aleggappstory.data.response.ListStoryItem
import com.submisson.aleggappstory.databinding.ItemLayoutBinding
import com.submisson.aleggappstory.view.detail.DetailActivity

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

                    binding.itemLayout.setOnClickListener{
                        val intent = Intent(itemView.context, DetailActivity::class.java)
                        intent.putExtra("storyItem", listStoryItem)

                        val optionCompat: ActivityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                Pair(binding.ivStoryImage, "story_image"),
                                Pair(binding.tvName, "name"),
                                Pair(binding.tvDescription, "description")
                            )
                        itemView.context.startActivity(intent, optionCompat.toBundle())
                    }
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