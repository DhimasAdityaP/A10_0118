package com.example.a10_118.ui.viewmodel.Tanaman

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a10_118.PertanianApplication


object TanamanPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeTanamanViewModel(
                aplikasiPertanian()
                    .container.tanamanRepository
            )
        }

        initializer {
            InsertTanamanViewModel(
                aplikasiPertanian()
                    .container.tanamanRepository
            )
        }
    }

    fun CreationExtras.aplikasiPertanian(): PertanianApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplication)

}