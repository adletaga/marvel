package com.example.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvel.comic_list.ComicListFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment = ComicListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, ComicListFragment.TAG).commit()
        }
    }
}
