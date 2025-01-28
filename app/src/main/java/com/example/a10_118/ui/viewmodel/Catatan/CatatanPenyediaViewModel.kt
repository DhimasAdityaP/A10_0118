package com.example.a10_118.ui.viewmodel.Catatan

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a10_118.PertanianApplication

object CatatanPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeCatatanViewModel(
                aplikasiPertanian()
                    .container.catatanRepository
            )
        }

        initializer {
            InsertCatatanViewModel(
                aplikasiPertanian()
                    .container.catatanRepository
            )
        }
    }

    fun CreationExtras.aplikasiPertanian(): PertanianApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplication)

}