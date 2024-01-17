package com.takehomechallenge.jerryberlin.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.takehomechallenge.jerryberlin.R
import com.takehomechallenge.jerryberlin.core.data.Resource
import com.takehomechallenge.jerryberlin.core.data.local.entity.FavoriteEntity
import com.takehomechallenge.jerryberlin.core.model.Location
import com.takehomechallenge.jerryberlin.core.model.Origin
import com.takehomechallenge.jerryberlin.core.model.ResultsItem
import com.takehomechallenge.jerryberlin.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailActivityArgs by navArgs()
    private var statusFavorite = false
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = args.id

        detailViewModel.getCharacterByIdFromFavorite(id).observe(this) {
            if (it != null) {
                setData(
                    ResultsItem(
                        id = it.characterId,
                        species = it.species,
                        origin = Origin(it.origin),
                        location = Location(it.location),
                        gender = it.gender,
                        image = it.image,
                        name = it.name
                    )
                )
                isLoading = true
            } else {
                detailViewModel.getDetailCharacter(id).observe(this) { character ->
                    when (character) {
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.layoutDetail.visibility = View.VISIBLE
                            binding.tvError.visibility = View.GONE
                            setData(character.data)
                        }
                        is Resource.Loading -> {
                            if (!isLoading) {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.layoutDetail.visibility = View.GONE
                                binding.tvError.visibility = View.GONE
                            }
                        }
                        is Resource.Error -> {
                            binding.tvError.text = character.message
                            binding.tvError.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            binding.layoutDetail.visibility = View.GONE
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setData(data: ResultsItem?) {
        data?.let {
            Glide.with(this)
                .load(data.image)
                .into(binding.ivAvatarDetail)
            binding.apply {
                tvNameDetail.text = data.name
                tvGenderDetail.text = data.gender
                tvLocationDetail.text = data.location?.name
                tvSpesiesDetail.text = data.species
                tvOriginDetail.text = data.origin?.name
            }
            lifecycleScope.launch {
                detailViewModel.checkId(data.id!!).observe(this@DetailActivity) {
                    statusFavorite = it != null
                    if (statusFavorite) {
                        binding.fabFavoriteDetail.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailActivity,
                                R.drawable.ic_favorite_black_24
                            )
                        )
                    } else {
                        binding.fabFavoriteDetail.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailActivity,
                                R.drawable.ic_unfavorite_black_24
                            )
                        )
                    }
                }
            }
            binding.fabFavoriteDetail.setOnClickListener {
                if (!statusFavorite) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        detailViewModel.insertCharacterToFavorite(
                            FavoriteEntity(
                                characterId = data.id!!,
                                name = data.name!!,
                                image = data.image!!,
                                gender = data.gender!!,
                                location = data.location!!.name!!,
                                origin = data.origin!!.name!!,
                                species = data.species!!,
                                isFavorite = true
                            )
                        )
                    }
                } else {
                    lifecycleScope.launch {
                        detailViewModel.deleteCharacterFromFavorite(
                            FavoriteEntity(
                                characterId = data.id!!,
                                name = data.name!!,
                                image = data.image!!,
                                gender = data.gender!!,
                                location = data.location!!.name!!,
                                origin = data.origin!!.name!!,
                                species = data.species!!,
                            )
                        )
                    }
                }
            }
        }
    }
}
