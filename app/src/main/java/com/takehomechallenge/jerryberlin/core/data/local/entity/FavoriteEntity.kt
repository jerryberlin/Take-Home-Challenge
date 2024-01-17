package com.takehomechallenge.jerryberlin.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite",
)
data class FavoriteEntity(

    @PrimaryKey
    val characterId: Int,

    val name: String,

    val species: String,

    val gender: String,

    val origin: String,

    val location: String,

    val image: String,

    val isFavorite: Boolean = false
)