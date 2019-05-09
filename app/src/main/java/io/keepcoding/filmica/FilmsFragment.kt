package io.keepcoding.filmica

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_films.*
import java.lang.IllegalArgumentException

class FilmsFragment : Fragment() {

    lateinit var listener: OnFilmClickLister

    val list: RecyclerView by lazy {
        listFilms.addItemDecoration(GridOffsetDecoration())
        return@lazy listFilms
    }

    val adapter = FilmsAdapter {
        listener.onClick(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFilmClickLister) {
            listener = context
        } else {
            throw IllegalArgumentException("The attached activity isn't implementing ${OnFilmClickLister::class.java.canonicalName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_films, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        FilmsRepo.discoverFilms(context!!,
            { films ->
                adapter.setFilms(films)
                progress.visibility = View.INVISIBLE
                error.visibility = View.INVISIBLE
                list.visibility = View.VISIBLE

            }, { errorRequest ->
                progress.visibility = View.INVISIBLE
                list.visibility = View.INVISIBLE
                error.visibility = View.VISIBLE
            })
    }


    interface OnFilmClickLister {
        fun onClick(film: Film)
    }

}