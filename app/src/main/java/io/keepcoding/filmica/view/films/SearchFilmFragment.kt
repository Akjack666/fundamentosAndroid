package io.keepcoding.filmica.view.films


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.keepcoding.filmica.R
import io.keepcoding.filmica.data.Film
import io.keepcoding.filmica.data.FilmsRepo
import io.keepcoding.filmica.view.listeners.OnFilmClickLister
import io.keepcoding.filmica.view.util.GridOffsetDecoration
import kotlinx.android.synthetic.main.fragment_search_film_fragment.*


class SearchFilmFragment : Fragment() {


    var movie: String = "matrix"
    var language: String = "en-US"
   var  sort: String = "popularity.desc"


    lateinit var listener: OnFilmClickLister

    val list: RecyclerView by lazy {
        searchListFilms.addItemDecoration(GridOffsetDecoration())
        return@lazy searchListFilms
    }

    val adapter = FilmsAdapter {
        listener.onClick(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFilmClickLister) {
            listener = context
        } else {
            throw IllegalArgumentException(
                "The attached activity isn't implementing " +
                        "${OnFilmClickLister::class.java.canonicalName}"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_film_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.adapter = adapter
        //  buttonRetry.setOnClickListener { reload() }
        btSearch.setOnClickListener {

            setMovie(view)


        }
    }

    private fun setMovie(view: View) {
        if (etSearch.text.toString().length > 3) {
            movie = etSearch.text.toString()
            reload()
        } else {
            Toast.makeText(
                view.context, "Debes introducir un minimo de 3 caracteres", Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    private fun reload() {
        showProgress()

        FilmsRepo.searchFilms(context!!,
            { films ->
                adapter.setFilms(films)
                if(films.isEmpty()){
                    showError()
                }else {
                    showList()

                }

            }, { errorRequest ->
                showError()
            } ,language,sort, movie
        )
    }

    private fun showList() {
        searchFilmsProgress.visibility = View.INVISIBLE
        errorSearch.visibility = View.INVISIBLE
        list.visibility = View.VISIBLE
    }

    private fun showError() {
        searchFilmsProgress.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
        errorSearch.visibility = View.VISIBLE
    }


    private fun showProgress() {
        searchFilmsProgress.visibility = View.VISIBLE
        errorSearch.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
    }




}
