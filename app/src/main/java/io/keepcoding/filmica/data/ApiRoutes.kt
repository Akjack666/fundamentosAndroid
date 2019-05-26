package io.keepcoding.filmica.data

import android.net.Uri
import io.keepcoding.filmica.BuildConfig

object ApiRoutes {

    fun discoverMoviesUrl(
        language: String = "en-US",
        sort: String = "popularity.desc",
        page: String = "page"
    ): String {
        return getUriBuilder()
            .appendPath("discover")
            .appendPath("movie")
            .appendQueryParameter("language", language)
            .appendQueryParameter("sort_by", sort)
            .appendQueryParameter("page", page)
            .appendQueryParameter("include_adult", "false")
            .appendQueryParameter("include_video", "false")
            .build()
            .toString()
    }

    fun trendingMoviesUrl(
        language: String = "en-US",
        sort: String = "popularity.desc",
        page: String = "page"
    ): String {
        return getUriBuilder()
            .appendPath("trending")
            .appendPath("movie")
            .appendPath("day")
            .appendQueryParameter("language", language)
            .appendQueryParameter("page", page)
            .appendQueryParameter("sort_by", sort)
            .appendQueryParameter("include_adult", "false")
            .appendQueryParameter("include_video", "false")
            .build()
            .toString()
    }

    fun searchMoviesUrl(
        language: String = "en-US",
        sort: String = "popularity.desc",
        movie: String = ""

    ): String {
        return getUriBuilder()
            .appendPath("search")
            .appendPath("movie")
            .appendQueryParameter("query", movie )
            .appendQueryParameter("language", language)
            .appendQueryParameter("sort_by", sort)
            .appendQueryParameter("include_adult", "false")
            .appendQueryParameter("include_video", "false")
            .build()
            .toString()
    }

    private fun getUriBuilder(): Uri.Builder =
        Uri.Builder()
            .scheme("https")
            .authority("api.themoviedb.org")
            .appendPath("3")
            .appendQueryParameter("api_key", BuildConfig.MovieDbApiKey)
}