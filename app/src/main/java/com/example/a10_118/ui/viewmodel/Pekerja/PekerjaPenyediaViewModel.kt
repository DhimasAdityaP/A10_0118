package com.example.a10_118.ui.viewmodel.Pekerja

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a10_118.PertanianApplication

object PekerjaPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomePekerjaViewModel(
                aplikasiPertanian()
                    .container.pekerjaRepository
            )
        }

        initializer {
            InsertPekerjaViewModel(
                aplikasiPertanian()
                    .container.pekerjaRepository
            )
        }
    }

    fun CreationExtras.aplikasiPertanian(): PertanianApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplication)

}
