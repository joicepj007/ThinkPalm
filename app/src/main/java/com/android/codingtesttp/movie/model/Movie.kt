package com.android.codingtesttp.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val page: Page
)
@JsonClass(generateAdapter = true)
data class Page(
    @Json(name = "total-content-items") val totalCount: Int,
    @Json(name = "page-num") val pageNumber: Int,
    @Json(name = "page-size") val pageSize: Int,
    val title: String,
    @Json(name = "content-items") val contentContent: Content
)
