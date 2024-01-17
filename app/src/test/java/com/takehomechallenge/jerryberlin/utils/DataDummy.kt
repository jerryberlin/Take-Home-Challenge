package com.takehomechallenge.jerryberlin.utils

import com.takehomechallenge.jerryberlin.core.model.Location
import com.takehomechallenge.jerryberlin.core.model.Origin
import com.takehomechallenge.jerryberlin.core.model.Response
import com.takehomechallenge.jerryberlin.core.model.ResultsItem

object DataDummy {
    fun generateDummyCharacter(): Response {
        val resultsList = ArrayList<ResultsItem?>()
        for (i in 0..10) {
            val resultsItem = ResultsItem(
                id = 1,
                name = "Rick",
                origin = Origin("Earth"),
                location = Location("Earth"),
                gender = "Male",
                species = "Human",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            )
            resultsList.add(resultsItem)
        }
        return Response(resultsList)
    }
}

