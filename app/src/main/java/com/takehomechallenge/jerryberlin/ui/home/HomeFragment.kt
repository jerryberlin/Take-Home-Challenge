package com.takehomechallenge.jerryberlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.takehomechallenge.jerryberlin.core.adapter.CharacterListAdapter
import com.takehomechallenge.jerryberlin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: CharacterListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCharacterHome.layoutManager = layoutManager
        binding.rvCharacterHome.setHasFixedSize(true)

        adapter = CharacterListAdapter()

        homeViewModel.characters.observe(requireActivity()) {
            adapter.submitData(lifecycle, it)
            checkEmpty()
        }

        binding.rvCharacterHome.adapter = adapter
    }

    private fun checkEmpty() {
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.emptyTV.visibility = View.GONE
                binding.rvCharacterHome.visibility = View.GONE
            } else {
                val isEmpty = adapter.itemCount == 0
                if (isEmpty) {
                    binding.emptyTV.visibility = View.VISIBLE
                    binding.rvCharacterHome.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.emptyTV.visibility = View.GONE
                    binding.rvCharacterHome.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}