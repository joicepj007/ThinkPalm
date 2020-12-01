package com.android.codingtesttp.movie.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.codingtesttp.R
import com.android.codingtesttp.movie.view.fragments.MovieFragment
import kotlinx.android.synthetic.main.movie_activity.*

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.title=getString(R.string.str_title)
        if (savedInstanceState == null) {
            val fragment =
                MovieFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                .commit()
        }
    }

}
