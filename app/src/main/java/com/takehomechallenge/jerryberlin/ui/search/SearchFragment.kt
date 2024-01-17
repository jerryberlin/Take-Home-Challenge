package com.takehomechallenge.jerryberlin.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.takehomechallenge.jerryberlin.core.adapter.SearchListAdapter
import com.takehomechallenge.jerryberlin.core.data.Resource
import com.takehomechallenge.jerryberlin.core.model.ResultsItem
import com.takehomechallenge.jerryberlin.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCharacterSearch.layoutManager = layoutManager
        binding.rvCharacterSearch.setHasFixedSize(true)

        binding.emptyTV.visibility = View.VISIBLE

        binding.svHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchViewModel.searchCharacter(p0!!).observe(requireActivity()) {
                    when (it) {
                        is Resource.Success -> {
                            if (p0.isEmpty()) {
                                binding.progressBar.visibility = View.GONE
                                binding.rvCharacterSearch.visibility = View.GONE
                                binding.emptyTV.visibility = View.VISIBLE
                            } else {
                                binding.progressBar.visibility = View.GONE
                                binding.rvCharacterSearch.visibility = View.VISIBLE
                                binding.emptyTV.visibility = View.GONE
                                setData(it.data?.results)
                            }
                        }
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvCharacterSearch.visibility = View.GONE
                            binding.emptyTV.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            binding.emptyTV.text = it.message
                            binding.progressBar.visibility = View.GONE
                            binding.rvCharacterSearch.visibility = View.GONE
                            binding.emptyTV.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
                return false
            }
        })
    }

    private fun setData(results: List<ResultsItem?>?) {
        val searchAdapter = SearchListAdapter(results)
        binding.rvCharacterSearch.adapter = searchAdapter
    }

}