package com.android.codingtesttp.movie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.codingtesttp.movie.model.ContentRV
import com.android.codingtesttp.movie.model.Page
import com.android.codingtesttp.movie.repository.MoviesRepository
import com.android.codingtesttp.movie.repository.MoviesRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


open class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val popularMoviesRepository: MoviesRepository = getRepository()

    private val _moviesList = MutableLiveData<MutableList<ContentRV>>()

    private lateinit var _pageInfo : Page

      val pageInfo: Page
          get() = _pageInfo

    val moviesList: LiveData<MutableList<ContentRV>>
        get() = _moviesList

    init {
        _moviesList.value= mutableListOf()
    }
    /**
     * Fetches the movies list
     */
    fun getMovies(pageNo:Int)  {
        coroutineScope.launch {
            val movieListData= popularMoviesRepository.getMovies(pageNo)
            movieListData?.let {
                val data=movieListData.page.contentContent.content
                val fullData= moviesList.value
                fullData?.addAll(data)
                _pageInfo=movieListData.page
                _moviesList.postValue(fullData)
            }
        }

    }
    internal open fun getRepository() : MoviesRepository {
        return MoviesRepositoryImpl(
            getApplication()
        )
    }
}