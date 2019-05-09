package io.keepcoding.filmica.view.films

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import io.keepcoding.filmica.R
import io.keepcoding.filmica.data.Film
import io.keepcoding.filmica.view.detail.DetailActivity
import io.keepcoding.filmica.view.detail.DetailFragment


class FilmsActivity : AppCompatActivity(),
    FilmsFragment.OnFilmClickLister {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)

        if (savedInstanceState == null) {
            val fragment = FilmsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
        }

    }

    override fun onClick(film: Film) {
        if (!isDetailDetailViewAvailable()) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", film.id)
            startActivity(intent)
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container_detail,
                        DetailFragment.newInstance(film.id)
                    )
                    .commit()
        }
    }

    private fun isDetailDetailViewAvailable() =
            findViewById<FrameLayout>(R.id.container_detail) != null
}