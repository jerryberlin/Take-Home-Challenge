package com.takehomechallenge.jerryberlin.core.data.remote.network

import com.takehomechallenge.jerryberlin.core.model.Response
import com.takehomechallenge.jerryberlin.core.model.ResultsItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response

    @GET("character/{id}")
    suspend fun getDetailCharacter(
        @Path("id") id: Int
    ): ResultsItem

    @GET("character")
    suspend fun searchCharacter(
        @Query("name") name: String
    ): Response
}