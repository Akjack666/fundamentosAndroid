package io.keepcoding.filmica.view.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.keepcoding.filmica.R


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState == null) {
            val fragment = DetailFragment()
            val placeholder = PlaceholderFragment()
            val id = intent.getStringExtra("id")
            val type = intent.getStringExtra("type")


            val args = Bundle()
            args.putString("id", id)

            fragment.arguments = args

            if(type == "placeholder") {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, placeholder)
                    .commit()

            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            }


        }
    }
}
