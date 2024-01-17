package com.takehomechallenge.jerryberlin.core.model

import com.google.gson.annotations.SerializedName

data class Response(
    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null,
)


data class ResultsItem(
    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("species")
    val species: String? = null,

    @field:SerializedName("origin")
    val origin: Origin? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,
)

data class Location(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class Origin(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)