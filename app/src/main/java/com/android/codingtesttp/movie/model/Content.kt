package com.android.codingtesttp.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Content(
    @Json(name = "content") val content: MutableList<ContentRV>
)