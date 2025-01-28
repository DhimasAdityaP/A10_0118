package com.example.a10_118.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_118.Model.Pekerja
import com.example.a10_118.Repository.PekerjaRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomePekerjaUiState {
    data class Success(val pekerja: List<Pekerja>) : HomePekerjaUiState()
    data class Error(val message: String) : HomePekerjaUiState() // Add error message
    object Loading : HomePekerjaUiState()
}

class HomePekerjaViewModel(private val pekerjaRepository: PekerjaRepository) : ViewModel() {

    var pekerjaUiState: HomePekerjaUiState by mutableStateOf(HomePekerjaUiState.Loading)
        private set

    init {
        getPekerja()
    }

    // Function to get plant data
    fun getPekerja() {
        viewModelScope.launch {
            safeApiCall(
                call = {
                    val pekerjaList = pekerjaRepository.getPekerja()
                    pekerjaUiState = HomePekerjaUiState.Success(pekerjaList)
                },
                onError = { errorMessage ->
                    pekerjaUiState = HomePekerjaUiState.Error(errorMessage)
                }
            )
        }
    }

    // Function to delete plant by ID
    fun deletepekerja(idpekerja: String) {
        viewModelScope.launch {
            safeApiCall(
                call = {
                    pekerjaRepository.deletePekerja(idpekerja)
                    getPekerja() // Refresh data after deletion
                },
                onError = { errorMessage ->
                    pekerjaUiState = HomePekerjaUiState.Error(errorMessage)
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
