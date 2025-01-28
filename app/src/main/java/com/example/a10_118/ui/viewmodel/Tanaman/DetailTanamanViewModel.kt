package com.example.a10_118.ui.viewmodel.Tanaman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_118.Model.Tanaman
import com.example.a10_118.Repository.TanamanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed class TanamanDetailUiState {
    data class Success(val tanaman: Tanaman):TanamanDetailUiState()
    object Error : TanamanDetailUiState()
    object Loading : TanamanDetailUiState()
}

class DetailTanamanViewModel(private val repository: TanamanRepository) : ViewModel() {
    private val _detailUiState = MutableStateFlow<TanamanDetailUiState>(TanamanDetailUiState.Loading)
    val detailUiState: StateFlow<TanamanDetailUiState> = _detailUiState

    fun getTanamanById(idtanaman: String) {
        viewModelScope.launch {
            _detailUiState.value = TanamanDetailUiState.Loading
            try {
                val tanaman = repository.getTanamanById(idtanaman)
                _detailUiState.value = TanamanDetailUiState.Success(tanaman)
            } catch (e: IOException) {
                e.printStackTrace()
                _detailUiState.value = TanamanDetailUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _detailUiState.value = TanamanDetailUiState.Error
            }
        }
    }
}