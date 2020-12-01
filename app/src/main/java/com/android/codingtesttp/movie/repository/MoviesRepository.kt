package com.android.codingtesttp.movie.repository

import android.content.Context
import com.android.codingtesttp.movie.model.Movie
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.IOException
import java.io.InputStream


/**
 * Read in a flat file json list of movies from Assets folder and deserializes to list of movies
 *
 */
internal interface MoviesRepository {
    fun getMovies(pageNo: Int): Movie?
}

internal object MoshiFactory {

    private val moshi : Moshi =  Moshi.Builder()
        .build()

    fun getInstance() =
        moshi
}

internal open class MoviesRepositoryImpl(
    private val context: Context,
    private val moshi: Moshi = MoshiFactory.getInstance()
) : MoviesRepository {

    override fun getMovies(pageNo: Int) : Movie? {

        val fileName="content_listing_page_$pageNo.json"
        val moviesJson = getMovieFromJSON(fileName)
        val adapter: JsonAdapter<Movie> = moshi.adapter(
            Movie::class.java)
        return adapter.fromJson(moviesJson)
    }


    private fun getMovieFromJSON(fileName : String): String {
        val inputStream = getInputStreamForJsonFile(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }

    @Throws(IOException::class)
    internal open fun getInputStreamForJsonFile(fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}