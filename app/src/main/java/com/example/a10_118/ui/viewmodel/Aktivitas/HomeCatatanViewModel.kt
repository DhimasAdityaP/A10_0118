package com.example.a10_118.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_118.Model.AktivitasPertanian
import com.example.a10_118.Repository.AktivitasRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val aktivitas: List<AktivitasPertanian>) : HomeUiState()
    data class Error(val message: String) : HomeUiState() // Add error message
    object Loading : HomeUiState()
}

class HomeAktivitasViewModel(private val aktivitasRepository: AktivitasRepository) : ViewModel() {

    var aktivitasUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAktivitas()
    }

    // Function to get plant data
    fun getAktivitas() {
        viewModelScope.launch {
            safeApiCall(
                call = {
                    val aktivitasList = aktivitasRepository.getAktivitas()
                    aktivitasUiState = HomeUiState.Success(aktivitasList)
                },
                onError = { errorMessage ->
                    aktivitasUiState = HomeUiState.Error(errorMessage)
                }
            )
        }
    }

    // Function to delete plant by ID
    fun deleteAktivitas(idAktifitas: String) {
        viewModelScope.launch {
            safeApiCall(
                call = {
                    aktivitasRepository.deleteAktivitas(idAktifitas)
                    getAktivitas() // Refresh data after deletion
                },
                onError = { errorMessage ->
                    aktivitasUiState = HomeUiState.Error(errorMessage)
                }
            )
        }
    }

    // Function to handle errors consistently
    private suspend fun safeApiCall(call: suspend () -> Unit, onError: (String) -> Unit) {
        try {
            call()
        } catch (e: IOException) {
            onError("Network error: ${e.message}")
        } catch (e: HttpException) {
            onError("Server error: ${e.message}")
        } catch (e: Exception) {
            onError("Unexpected error: ${e.message}")
        }
    }
}
