package com.crgarridos.sample.savedstate.ui.savedstateviewmodel

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.crgarridos.sample.savedstate.R
import com.crgarridos.sample.savedstate.domain.Song
import com.crgarridos.sample.savedstate.extensions.observeNotNull
import com.crgarridos.sample.savedstate.injection.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class SavedStateHandleFragment : DaggerFragment(R.layout.main_fragment) {

    companion object {
        fun newInstance(): SavedStateHandleFragment =
            SavedStateHandleFragment()
    }

    @Inject lateinit var factory: ViewModelFactory<SavedStateHandleViewModel, SavedStateHandleViewModel.AssistedFactory>
    private val viewModel: SavedStateHandleViewModel by viewModels { factory }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.songResults.observeNotNull(viewLifecycleOwner) {
            bindSongs(it)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        searchButton.setOnClickListener {
            viewModel.search(searchField.text.toString())
        }
    }

    private fun bindSongs(songs: List<Song>) {
        message.text = songs.joinToString("\n")
    }
}