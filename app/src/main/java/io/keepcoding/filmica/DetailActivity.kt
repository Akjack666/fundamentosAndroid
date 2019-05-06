package io.keepcoding.filmica

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState == null) {
            val fragment = DetailFragment()
            val id = intent.getStringExtra("id")

            val args = Bundle()
            args.putString("id", id)

            fragment.arguments = args

            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()

        }
    }
}
