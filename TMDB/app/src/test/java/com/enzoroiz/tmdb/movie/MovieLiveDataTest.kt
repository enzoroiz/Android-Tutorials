package com.enzoroiz.tmdb.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.enzoroiz.tmdb.data.domain.repository.MovieRepository
import com.enzoroiz.tmdb.data.domain.usecase.GetMoviesUseCase
import com.enzoroiz.tmdb.data.domain.usecase.UpdateMoviesUseCase
import com.enzoroiz.tmdb.data.model.movie.Movie
import com.enzoroiz.tmdb.extensions.getData
import com.enzoroiz.tmdb.presentation.movie.MovieViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieLiveDataTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieViewModel

    private val getMovieList = listOf(
        Movie(
            id = 1,
            overview = "Overview 1",
            poster_path = "/posterPath-1",
            release_date = "10/10/2010",
            title = "Title 1"
        ),
        Movie(
            id = 2,
            overview = "Overview 2",
            poster_path = "/posterPath-2",
            release_date = "11/11/2011",
            title = "Title 2"
        )
    )

    private val updateMovieList = listOf(
        Movie(
            id = 3,
            overview = "Overview 3",
            poster_path = "/posterPath-3",
            release_date = "03/03/2003",
            title = "Title 1"
        ),
        Movie(
            id = 4,
            overview = "Overview 4",
            poster_path = "/posterPath-4",
            release_date = "04/04/2004",
            title = "Title 4"
        )
    )

    inner class FakeRepository : MovieRepository {
        override suspend fun getMovies(): List<Movie> {
            return getMovieList
        }

        override suspend fun updateMovies(): List<Movie> {
            return updateMovieList
        }
    }

    @Before
    @ExperimentalCoroutinesApi
    fun setup() {
//        Dispatchers.setMain(Dispatchers.IO)
//        mockedGetUseCase = Mockito.mock(GetMoviesUseCase::class.java)
//        mockedUpdateUseCase = Mockito.mock(UpdateMoviesUseCase::class.java)
        val fakeRepository = FakeRepository()
        val getUseCase = GetMoviesUseCase(fakeRepository)
        val updateUseCase = UpdateMoviesUseCase(fakeRepository)
        viewModel = MovieViewModel(getUseCase, updateUseCase)
    }

    @After

    @Test
    fun `should get movies list`() {
        val movies = viewModel.getMovies().getData()
        assertEquals(getMovieList.size, movies.size)
        assertEquals(getMovieList, movies)
    }

    @Test
    fun `should update movies list`() {
        val movies = viewModel.updateMovies().getData()
        assertEquals(updateMovieList.size, movies.size)
        assertEquals(updateMovieList, movies)
    }
}