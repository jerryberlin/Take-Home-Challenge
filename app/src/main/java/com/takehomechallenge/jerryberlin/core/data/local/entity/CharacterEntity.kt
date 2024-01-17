package com.takehomechallenge.jerryberlin.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "character")
data class CharacterEntity(

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("species")
    val species: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("origin")
    val origin: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("image")
    val image: String? = null,
) : Parcelable