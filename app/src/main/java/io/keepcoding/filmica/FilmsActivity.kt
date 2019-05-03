package io.keepcoding.filmica

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View


class FilmsActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)

    }

    fun launchFilmDetail(v: View) {
        val intent: Intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }
}