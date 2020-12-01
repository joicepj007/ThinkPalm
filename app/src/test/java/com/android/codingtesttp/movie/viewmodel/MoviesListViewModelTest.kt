package com.android.codingtesttp.movie.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.codingtesttp.movie.repository.MoviesRepository
import com.android.codingtesttp.movie.repository.MoviesRepositoryImpl
import io.mockk.*
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.FileInputStream
import java.io.InputStream


const val ASSET_BASE_PATH = "../app/src/main/assets/"

@RunWith(MockitoJUnitRunner::class)
class MoviesListViewModelTest: TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `given movie list is called, when flat json file one is parsed, then movie list created`(){
        runBlocking {
            val moviesListViewModel: MoviesListViewModel = object : MoviesListViewModel(mockk()) {

                override fun getRepository() : MoviesRepository = object :
                    MoviesRepositoryImpl(mockk()) {

                    override fun getInputStreamForJsonFile(fileName: String): InputStream {
                        return FileInputStream(ASSET_BASE_PATH + fileName)
                    }
                }
            }
            moviesListViewModel.getMovies(1)

            Assertions.assertAll(
                Executable { Assertions.assertEquals(20, moviesListViewModel.moviesList.value?.size) }
            )
        }

    }

    @Test
    fun `given movie list is called, when flat json file one and two is parsed, then movie list created`(){
        runBlocking {
            val moviesListViewModel: MoviesListViewModel = object : MoviesListViewModel(mockk()) {

                override fun getRepository() : MoviesRepository = object :
                    MoviesRepositoryImpl(mockk()) {

                    override fun getInputStreamForJsonFile(fileName: String): InputStream {
                        return FileInputStream(ASSET_BASE_PATH + fileName)
                    }
                }
            }
            moviesListViewModel.getMovies(1)
            moviesListViewModel.getMovies(2)

            Assertions.assertAll(
                Executable { Assertions.assertEquals(40, moviesListViewModel.moviesList.value?.size) }
            )
        }

    }
    @Test
    fun `given movie list is called, when flat json file one,two and three is parsed, then movie list created`(){
        runBlocking {
            val moviesListViewModel: MoviesListViewModel = object : MoviesListViewModel(mockk()) {

                override fun getRepository() : MoviesRepository = object :
                    MoviesRepositoryImpl(mockk()) {

                    override fun getInputStreamForJsonFile(fileName: String): InputStream {
                        return FileInputStream(ASSET_BASE_PATH + fileName)
                    }
                }
            }
            moviesListViewModel.getMovies(1)
            moviesListViewModel.getMovies(2)
            moviesListViewModel.getMovies(3)

            Assertions.assertAll(
                Executable { Assertions.assertEquals(54, moviesListViewModel.moviesList.value?.size) }
            )
        }

    }



}