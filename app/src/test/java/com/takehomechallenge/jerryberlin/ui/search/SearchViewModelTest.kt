package com.takehomechallenge.jerryberlin.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.takehomechallenge.jerryberlin.core.data.Resource
import com.takehomechallenge.jerryberlin.core.data.local.CharacterRepository
import com.takehomechallenge.jerryberlin.core.model.Response
import com.takehomechallenge.jerryberlin.utils.DataDummy
import com.takehomechallenge.jerryberlin.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var characterRepository: CharacterRepository
    private lateinit var searchViewModel: SearchViewModel
    private val dummyCharacters = DataDummy.generateDummyCharacter()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        searchViewModel = SearchViewModel(characterRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when Search Characters Should Not Null and Return Success`() {
        val expectedChars = flow {
            emit(Resource.Success(dummyCharacters))
        }
        `when`(characterRepository.searchCharacter("Rick")).thenReturn(expectedChars)
        val actualChars = searchViewModel.searchCharacter("Rick").getOrAwaitValue()

        Mockito.verify(characterRepository).searchCharacter("Rick")
        Assert.assertNotNull(actualChars)
        Assert.assertTrue(actualChars is Resource.Success)
        Assert.assertEquals(
            dummyCharacters.results?.size,
            (actualChars as Resource.Success).data?.results?.size
        )
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val characters = flow {
            emit(Resource.Error("Error", null))
        } as Flow<Resource<Response>>
        `when`(characterRepository.searchCharacter("Rick")).thenReturn(characters)
        val actualChars = searchViewModel.searchCharacter("Rick").getOrAwaitValue()
        Mockito.verify(characterRepository).searchCharacter("Rick")
        Assert.assertNotNull(actualChars)
        Assert.assertTrue(actualChars is Resource.Error)
    }

    @Test
    fun `when Loading Should Return Loading`() {
        val charactersLoading = flow {
            emit(Resource.Loading(null))
        } as Flow<Resource<Response>>

        `when`(characterRepository.searchCharacter("Rick"))
            .thenReturn(charactersLoading) // Return loading state

        // Expect loading state
        val actualCharsLoading = searchViewModel.searchCharacter("Rick").getOrAwaitValue()
        Mockito.verify(characterRepository).searchCharacter("Rick")
        Assert.assertNotNull(actualCharsLoading)
        Assert.assertTrue(actualCharsLoading is Resource.Loading)
    }

    @Test
    fun `when Empty Search Result Should Return Empty Success`() {
        val charactersEmpty = flow {
            emit(Resource.Success(Response(results = emptyList())))
        } as Flow<Resource<Response>>

        `when`(characterRepository.searchCharacter("NonExistentCharacter"))
            .thenReturn(charactersEmpty) // Return empty success state

        // Expect empty success state
        val actualCharsEmpty = searchViewModel.searchCharacter("NonExistentCharacter").getOrAwaitValue()
        Mockito.verify(characterRepository).searchCharacter("NonExistentCharacter")
        Assert.assertNotNull(actualCharsEmpty)
        Assert.assertTrue(actualCharsEmpty is Resource.Success)
        Assert.assertTrue((actualCharsEmpty as Resource.Success).data?.results.isNullOrEmpty())
    }
}