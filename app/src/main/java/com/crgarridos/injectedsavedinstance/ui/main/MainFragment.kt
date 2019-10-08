package com.crgarridos.injectedsavedinstance.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.crgarridos.injectedsavedinstance.R
import com.crgarridos.injectedsavedinstance.domain.Song
import com.crgarridos.injectedsavedinstance.domain.SongRepository
import com.crgarridos.injectedsavedinstance.extensions.observeNotNull
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var factory: MainViewModel.Factory
    private val viewModel: MainViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        factory = MainViewModel.Factory(SongRepository(), this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d("Fragment", "onCreateView: $savedInstanceState")
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Fragment", "onViewCreated: $savedInstanceState")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.songResults.observeNotNull(viewLifecycleOwner) {
           bindSongs(it)
        }
        Log.d("Fragment", "onActivityCreated: $savedInstanceState")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("Fragment", "onSaveInstanceState: $outState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        searchButton.setOnClickListener {
            viewModel.search(searchField.text.toString())
        }
        Log.d("Fragment", "onViewStateRestored: $savedInstanceState")

    }

    private fun bindSongs(songs: List<Song>) {
        message.text = songs.joinToString("\n")
    }
}