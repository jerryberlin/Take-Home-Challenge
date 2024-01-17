package com.takehomechallenge.jerryberlin.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.takehomechallenge.jerryberlin.core.data.local.entity.FavoriteEntity
import com.takehomechallenge.jerryberlin.databinding.RvCharacterBinding
import com.takehomechallenge.jerryberlin.ui.favorite.FavoriteFragmentDirections

class FavoriteListAdapter(private val listCharacter: List<FavoriteEntity>?) :
    RecyclerView.Adapter<FavoriteListAdapter.MyViewHolder>() {
    class MyViewHolder(var binding: RvCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listCharacter?.get(position)
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(data?.image)
                .into(holder.binding.ivAvatarRv)
            tvNameRv.text = data?.name
            tvSpeciesRv.text = data?.species
        }
        holder.itemView.setOnClickListener {
            val action = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity(
                data?.characterId!!
            )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int =
        listCharacter!!.size
}