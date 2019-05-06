package io.keepcoding.filmica

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_films.*

class FilmsFragment: Fragment() {
    val list: RecyclerView by lazy {
        listFilms.layoutManager = LinearLayoutManager(context)
        return@lazy listFilms
    }

    val adapter = FilmsAdapter {
        launchFilmDetail(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_films, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setFilms(FilmsRepo.films)
        list.adapter = adapter
    }

    fun launchFilmDetail(film: Film) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", film.id)

        startActivity(intent)
    }
}