package com.example.marvel.comic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marvel.R

class ComicFragment : Fragment() {


    companion object {
        val TAG = "ComicFragment"

        private val KEY_COMIC_ID = "comic_id"

        fun newInstance(id: String): ComicFragment {
            val fragment = ComicFragment()
            val args = Bundle()
            args.putString(KEY_COMIC_ID, id)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.comic_fragment, container, false)
        return view
    }

}