package com.example.a10_118.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10_118.Model.AktivitasPertanian
import com.example.a10_118.Repository.AktivitasRepository
import kotlinx.coroutines.launch

class InsertAktivitasViewModel(private val aktivitasPertanian: AktivitasRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertAktivitasState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertAktivitas() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                aktivitasPertanian.insertAktivitas(uiState.insertUiEvent.toAktivitas())
                uiState = uiState.copy(isSuccess = true, isLoading = false) // Update state success
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false) // Handle error
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertUiEvent(
    val id_aktivitas: String ="",
    val id_tanaman: String = "",
    val id_pekerja: String="",
    val tanggal_aktivitas: String="",
    val deskripsi_aktivitas:String=""

)

fun InsertUiEvent.toAktivitas(): AktivitasPertanian = AktivitasPertanian(
    id_aktivitas =id_aktivitas,
    id_tanaman = id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas = tanggal_aktivitas,
    deskripsi_aktivitas = deskripsi_aktivitas
)

fun AktivitasPertanian.toUiStateTanaman(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun AktivitasPertanian.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_aktivitas=id_aktivitas,
    id_tanaman = id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas=tanggal_aktivitas,
)