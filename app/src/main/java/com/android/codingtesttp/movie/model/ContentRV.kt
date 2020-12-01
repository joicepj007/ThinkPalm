package com.android.codingtesttp.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContentRV(
    val name: String,
    @Json(name = "poster-image") val posterImage: String
)