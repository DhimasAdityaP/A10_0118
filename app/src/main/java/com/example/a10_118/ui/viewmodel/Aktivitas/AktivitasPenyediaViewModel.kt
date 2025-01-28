package com.example.a10_118.ui.viewmodel.Aktivitas

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a10_118.PertanianApplication

object AktivitasPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeAktivitasViewModel(
                aplikasiPertanian()
                    .container.aktivitasRepository
            )
        }

        initializer {
            InsertAktivitasViewModel(
                aplikasiPertanian()
                    .container.aktivitasRepository
            )
        }
    }

    fun CreationExtras.aplikasiPertanian():PertanianApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplication)

}