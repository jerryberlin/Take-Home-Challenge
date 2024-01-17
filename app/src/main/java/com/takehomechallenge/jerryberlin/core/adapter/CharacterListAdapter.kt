package com.takehomechallenge.jerryberlin.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.takehomechallenge.jerryberlin.core.data.local.entity.CharacterEntity
import com.takehomechallenge.jerryberlin.databinding.RvCharacterBinding
import com.takehomechallenge.jerryberlin.ui.home.HomeFragmentDirections

class CharacterListAdapter
    : PagingDataAdapter<CharacterEntity, CharacterListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: RvCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CharacterEntity) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(binding.ivAvatarRv)
                tvNameRv.text = data.name
                tvSpeciesRv.text = data.species
            }
            itemView.setOnClickListener {
                val action = HomeFragmentDirections.actionNavigationHomeToDetailActivity(
                    data.id!!
                )
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(
                oldItem: CharacterEntity,
                newItem: CharacterEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CharacterEntity,
                newItem: CharacterEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}