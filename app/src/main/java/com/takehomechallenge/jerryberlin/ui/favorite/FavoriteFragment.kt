package com.takehomechallenge.jerryberlin.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.takehomechallenge.jerryberlin.core.adapter.FavoriteListAdapter
import com.takehomechallenge.jerryberlin.core.data.local.entity.FavoriteEntity
import com.takehomechallenge.jerryberlin.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCharacterFavorite.layoutManager = layoutManager
        binding.rvCharacterFavorite.setHasFixedSize(true)

        favoriteViewModel.character.observe(requireActivity()) {
            if (it.isEmpty()) {
                binding.emptyTV.visibility = View.VISIBLE
                binding.rvCharacterFavorite.visibility = View.GONE
            } else {
                binding.emptyTV.visibility = View.GONE
                binding.rvCharacterFavorite.visibility = View.VISIBLE
                setData(it)
            }
        }
    }

    private fun setData(it: List<FavoriteEntity>?) {
        val adapter = FavoriteListAdapter(it)
        binding.rvCharacterFavorite.adapter = adapter
    }
}