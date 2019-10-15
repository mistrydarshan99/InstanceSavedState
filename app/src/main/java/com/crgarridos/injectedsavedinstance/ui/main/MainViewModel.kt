package com.crgarridos.injectedsavedinstance.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.crgarridos.injectedsavedinstance.domain.Song
import com.crgarridos.injectedsavedinstance.domain.SongRepository
import com.crgarridos.injectedsavedinstance.extensions.savedState
import com.crgarridos.injectedsavedinstance.injection.viewmodel.ViewModelAssistedFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject


class MainViewModel @AssistedInject constructor(
    private val repository: SongRepository,
    @Assisted private val handle: SavedStateHandle
) : ViewModel() {

    private var name by savedState<String>("name", handle)

    private val _songResults = MutableLiveData<List<Song>>()
    val songResults: LiveData<List<Song>>
        get() = _songResults

    init { name?.let(::search) }

    fun search(name: String) {
        this.name = name
        _songResults.value = repository.getSongsByName(name)
    }

    @AssistedInject.Factory
    interface AssistedFactory: ViewModelAssistedFactory<MainViewModel>
}