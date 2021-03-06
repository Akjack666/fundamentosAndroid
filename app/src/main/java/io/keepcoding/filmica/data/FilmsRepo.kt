package io.keepcoding.filmica.data

import android.arch.persistence.room.Room
import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object FilmsRepo {

    private val films: MutableList<Film> = mutableListOf()
    private val filmsTrends: MutableList<Film> = mutableListOf()
    private val filmsSearch: MutableList<Film> = mutableListOf()

    @Volatile
    private var db: FilmDatabase? = null

    private fun getDbInstance(context: Context): FilmDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                FilmDatabase::class.java,
                "filmica-db"
            ).build()
        }

        return db as FilmDatabase
    }

    fun findFilmById(id: String): Film? {
        return films.find {
            return@find it.id == id
        }

    }

    fun findFilmTrendsById(id: String): Film? {
        return filmsTrends.find {
            return@find it.id == id
        }

    }

    fun findFilmSearchById(id: String): Film? {
        return filmsSearch.find {
            return@find it.id == id
        }

    }

    fun saveFilm(
        context: Context,
        film: Film,
        callback: (Film) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val async = async(Dispatchers.IO) {
            val db = getDbInstance(context)
            db.filmDao().insertFilm(film)
        }

            async.await()
            callback.invoke(film)
        }
    }

    fun getFilms(
        context: Context,
        callback: (List<Film>) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {

            val async = async(Dispatchers.IO) {
                val db = getDbInstance(context)
                db.filmDao().getFilms()
            }

            val films = async.await()
            callback.invoke(films)
        }
    }

    fun deleteFilm(
        context: Context,
        film: Film,
        callback: (Film) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val async = async(Dispatchers.IO) {
                val db = getDbInstance(context)
                db.filmDao().deleteFilm(film)
            }

            async.await()
            callback.invoke(film)
        }
    }

    fun discoverFilms(
        context: Context,
        onResponse: (List<Film>) -> Unit,
        onError: (VolleyError) -> Unit,
        language: String = "en-US",
        sort: String = "popularity.desc",
        page: String
    ) {
        val url = ApiRoutes.discoverMoviesUrl(language,sort,page)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val films =
                    Film.parseFilms(response.getJSONArray("results"))
                FilmsRepo.films.clear()
                FilmsRepo.films.addAll(films)
                onResponse.invoke(FilmsRepo.films)
            },
            { error ->
                error.printStackTrace()
                onError.invoke(error)
            }
        )

        Volley.newRequestQueue(context)
            .add(request)
    }



    fun trendingFilms(
        context: Context,
        onResponse: (List<Film>) -> Unit,
        onError: (VolleyError) -> Unit,
        language: String = "en-US",
        sort: String = "popularity.desc",
        page: String
    ) {
        val url = ApiRoutes.trendingMoviesUrl(language,sort,page)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val filmsTrends =
                    Film.parseFilms(response.getJSONArray("results"))
                FilmsRepo.filmsTrends.clear()
                FilmsRepo.filmsTrends.addAll(filmsTrends)
                onResponse.invoke(FilmsRepo.filmsTrends)
            },
            { error ->
                error.printStackTrace()
                onError.invoke(error)
            }
        )

        Volley.newRequestQueue(context)
            .add(request)
    }

    fun searchFilms(
        context: Context,
        onResponse: (List<Film>) -> Unit,
        onError: (VolleyError) -> Unit,
        language: String = "en-US",
        sort: String = "popularity.desc",
        movie: String   ) {
        val url = ApiRoutes.searchMoviesUrl(language,sort,movie)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val filmsSearch =
                    Film.parseFilms(response.getJSONArray("results"))
                FilmsRepo.filmsSearch.clear()
                FilmsRepo.filmsSearch.addAll(filmsSearch)
                onResponse.invoke(FilmsRepo.filmsSearch)
            },
            { error ->
                error.printStackTrace()
                onError.invoke(error)
            }
        )

        Volley.newRequestQueue(context)
            .add(request)
    }


    private fun dummyFilms(): MutableList<Film> {
        return (1..10).map { i: Int ->
            return@map Film(
                id = "${i}",
                title = "Film ${i}",
                overview = "Overview ${i}",
                genre = "Genre ${i}",
                rating = i.toFloat(),
                date = "2019-05-${i}"
            )
        }.toMutableList()
    }
}