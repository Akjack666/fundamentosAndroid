package io.keepcoding.filmica.view.films

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import io.keepcoding.filmica.R
import io.keepcoding.filmica.data.Film
import io.keepcoding.filmica.view.detail.DetailActivity
import io.keepcoding.filmica.view.detail.DetailFragment
import io.keepcoding.filmica.view.detail.PlaceholderFragment
import io.keepcoding.filmica.view.listeners.OnFilmClickLister
import io.keepcoding.filmica.view.watchlist.WatchlistFragment
import kotlinx.android.synthetic.main.activity_films.*

const val TAG_FILM = "films"
const val TAG_WATCHLIST = "watchlist"
const val TAG_TRENDINGFILMS = "trendingFilms"
const val TAG_SEARCHFILM = "searchFilm"

class FilmsActivity : AppCompatActivity(),
     OnFilmClickLister {


    private lateinit var filmsFragment: FilmsFragment
    private lateinit var watchlistFragment: WatchlistFragment
    private lateinit var trendingFilmsFragment: TrendingFilmsFragment
    private lateinit var searchFilmFragment: SearchFilmFragment
    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)

        if (savedInstanceState == null) {
            setupFragments()
        } else {
            val tag = savedInstanceState.getString("active", TAG_FILM)
            restoreFragments(tag)
        }

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_discover -> showMainFragment(filmsFragment)
                R.id.action_watchlist -> showMainFragment(watchlistFragment)
                R.id.action_trending_movies -> showMainFragment(trendingFilmsFragment)
                R.id.action_search_movies -> showMainFragment(searchFilmFragment)

            }

            true
        }

        if (isDetailDetailViewAvailable()) {
            setPlaceHolder()
        }


    }



    private fun setPlaceHolder() {
        if (!isDetailDetailViewAvailable()) {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container_detail,
                    PlaceholderFragment()
                )
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("active", activeFragment.tag)
    }

    private fun setupFragments() {
        filmsFragment = FilmsFragment()
        watchlistFragment = WatchlistFragment()
        trendingFilmsFragment = TrendingFilmsFragment()
        searchFilmFragment = SearchFilmFragment()
        activeFragment = filmsFragment

        supportFragmentManager.beginTransaction()
            .add(R.id.container, filmsFragment, TAG_FILM)
            .add(R.id.container, watchlistFragment, TAG_WATCHLIST)
            .add(R.id.container, trendingFilmsFragment, TAG_TRENDINGFILMS)
            .add(R.id.container, searchFilmFragment, TAG_SEARCHFILM)

            .hide(watchlistFragment)
            .hide(trendingFilmsFragment)
            .hide(searchFilmFragment)
            .commit()
    }

    private fun restoreFragments(tag: String) {
        filmsFragment = supportFragmentManager.findFragmentByTag(TAG_FILM) as FilmsFragment
        watchlistFragment =
            supportFragmentManager.findFragmentByTag(TAG_WATCHLIST) as WatchlistFragment
        trendingFilmsFragment =
            supportFragmentManager.findFragmentByTag(TAG_TRENDINGFILMS) as TrendingFilmsFragment

        searchFilmFragment =
            supportFragmentManager.findFragmentByTag(TAG_SEARCHFILM) as SearchFilmFragment

        activeFragment =
            if (tag == TAG_WATCHLIST) {
                watchlistFragment
            } else if (tag == TAG_TRENDINGFILMS) {

                trendingFilmsFragment
            } else if (tag == TAG_SEARCHFILM) {

                searchFilmFragment
            } else {
                filmsFragment
            }


    }

    private fun showMainFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()

        activeFragment = fragment
    }



    override fun onClick(film: Film) {

        if (!isDetailDetailViewAvailable()) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", film.id)
            intent.putExtra("fType",activeFragment.tag)
            startActivity(intent)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container_detail,
                    DetailFragment.newInstance(film.id,activeFragment.tag!!)
                )
                .commit()
        }

    }

    private fun isDetailDetailViewAvailable() =
        findViewById<FrameLayout>(R.id.container_detail) != null

}