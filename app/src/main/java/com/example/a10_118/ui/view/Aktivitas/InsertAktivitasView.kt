package com.example.a10_118.ui.view.Aktivitas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_118.ui.navigation.DestinasiNavigasi
import com.example.a10_118.ui.viewmodel.Aktivitas.AktivitasPenyediaViewModel
import com.example.a10_118.ui.viewmodel.Aktivitas.InsertAktivitasViewModel
import com.example.a10_118.ui.viewmodel.Aktivitas.InsertUiEvent
import com.example.a10_118.ui.viewmodel.Aktivitas.InsertUiState
import kotlinx.coroutines.launch

object DestinasiInsertAktivitas : DestinasiNavigasi {
    override val route = "aktivitas_entry"
    override val titleRes = "Tambah Aktivitas"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAktivitasScreen(
    navigateBack: () -> Unit, // Navigasi kembali ke Home tanaman
    modifier: Modifier = Modifier,
    viewModel: InsertAktivitasViewModel = viewModel(factory = AktivitasPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text("Masukan Data AKtivitas Pertanian") },
                navigationIcon = { // Tambahkan ikon panah untuk kembali
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Ikon panah kiri
                            contentDescription = "Kembali ke Home"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryBodyAktivitas(
            insertUiState = viewModel.uiState,
            onTanamanValueChange = viewModel::updateInsertAktivitasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAktivitas() // Menyimpan data tanaman
                    navigateBack() // Navigasi kembali ke Home tanaman
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyAktivitas(
    insertUiState: InsertUiState,
    onTanamanValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAktivitas(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onTanamanValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.insertUiEvent.id_aktivitas.isNotEmpty() && insertUiState.insertUiEvent.tanggal_aktivitas.isNotEmpty() // Validasi form
        ) {
            if (insertUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp)) // Loading indicator
            } else {
                Text(text = "Simpan")
            }
        }
    }
}

@Composable
fun FormInputAktivitas(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(id_aktivitas = it)) },
            label = { Text("ID Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.id_tanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(id_tanaman = it)) },
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.id_pekerja,
            onValueChange = { onValueChange(insertUiEvent.copy(id_pekerja = it)) },
            label = { Text("ID Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggal_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal_aktivitas = it)) },
            label = { Text("Tanggal Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsi_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_aktivitas = it)) },
            label = { Text("Deskripsi Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}