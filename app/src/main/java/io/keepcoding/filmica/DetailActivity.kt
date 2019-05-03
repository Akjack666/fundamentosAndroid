package io.keepcoding.filmica

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val button: Button = findViewById(R.id.button_add)

        button.setOnClickListener {
            Toast.makeText(this@DetailActivity, "Añadido al watchlist", Toast.LENGTH_LONG).show()
        }

    }
}
