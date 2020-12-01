package com.android.codingtesttp.movie.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.codingtesttp.movie.viewmodel.ASSET_BASE_PATH
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.function.Executable
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.FileInputStream
import java.io.InputStream

@RunWith(MockitoJUnitRunner::class)
class PopularPopularMoviesRepositoryTest: TestCase() {


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


    @org.junit.Test
    fun `when flat json file one is parsed, then deserialized list of movies created`() {
        runBlocking {
            val moviesRepository: MoviesRepository = object : MoviesRepositoryImpl(mockk()) {

                override fun getInputStreamForJsonFile(fileName: String): InputStream {
                    return FileInputStream(ASSET_BASE_PATH + fileName)
                }
            }

            val movies = moviesRepository.getMovies(1)

            assertAll(
                Executable { Assertions.assertEquals(20, movies?.page?.pageSize) }
            )
        }

    }

}









