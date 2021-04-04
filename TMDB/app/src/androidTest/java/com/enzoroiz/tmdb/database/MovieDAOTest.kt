package com.enzoroiz.tmdb.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.enzoroiz.tmdb.data.database.AppDatabase
import com.enzoroiz.tmdb.data.database.dao.MovieDAO
import com.enzoroiz.tmdb.data.model.movie.Movie
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDAOTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var movieDAO: MovieDAO
    private val moviesList = listOf(
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
        ),
        Movie(
            id = 3,
            overview = "Overview 3",
            poster_path = "/posterPath-3",
            release_date = "12/12/2012",
            title = "Title 2"
        )
    )

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()

        movieDAO = appDatabase.getMovieDAO()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun shouldSaveMoviesToDatabase() {
        runBlocking {
            movieDAO.saveMovies(moviesList)
            assertEquals(moviesList.size, movieDAO.getMovies().size)
            assertEquals(moviesList[0], movieDAO.getMovies()[0])
        }
    }

    @Test
    fun shouldDeleteMoviesFromDatabase() {
        runBlocking {
            movieDAO.saveMovies(moviesList)
            movieDAO.deleteAllMovies()
            assertTrue(movieDAO.getMovies().isEmpty())
        }
    }
}