package com.example.a10_118.ui.view.Aktivitas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_118.Model.AktivitasPertanian
import com.example.a10_118.ui.navigation.DestinasiNavigasi
import com.example.a10_118.ui.viewmodel.Aktivitas.AktivitasPenyediaViewModel
import com.example.a10_118.ui.viewmodel.Aktivitas.HomeAktivitasViewModel
import com.example.a10_118.ui.viewmodel.Aktivitas.HomeUiState

object DestinasiAktifitas : DestinasiNavigasi {
    override val route = "catatan_home"
    override val titleRes = "home_catatan"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAktivitas(
    navigateToAktivitasEntry: () -> Unit,
    navigateToSplash: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (AktivitasPertanian) -> Unit = {},
    onRefresh: () -> Unit = {},
    viewModel: HomeAktivitasViewModel = viewModel(factory = AktivitasPenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getAktivitas()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = { Text("Daftar Aktivitas") },
                navigationIcon = {
                    IconButton(onClick = navigateToSplash) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getAktivitas() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAktivitasEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Aktivitas")
            }
        }
    )  { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.aktivitasUiState,
            retryAction = { viewModel.getAktivitas() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,// Mengirimkan Tanaman
            onDeleteClick = { AktivitasPertanian -> viewModel.deleteAktivitas(AktivitasPertanian. id_aktivitas)}
        )
    }
}
@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (AktivitasPertanian) -> Unit, // Mengubah menjadi Tanaman
    onDeleteClick: (AktivitasPertanian) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Success -> {
            if (homeUiState.aktivitas.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tidak ada data aktivitas pertanian")
                }
            } else {
                AktivitasLayout(
                    aktivitas = homeUiState.aktivitas,
                    modifier = modifier,
                    onDetailClick = onDetailClick, // Mengirimkan Tanaman
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomeUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error loading data")
                    Button(onClick = retryAction) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}




// Gunakan TanamanLayout untuk menampilkan data tanaman
@Composable
fun AktivitasLayout(
    aktivitas: List<AktivitasPertanian>,
    modifier: Modifier = Modifier,
    onDetailClick: (AktivitasPertanian) -> Unit, // Mengubah parameter menjadi Tanaman
    onDeleteClick: (AktivitasPertanian) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(aktivitas) { aktivitas ->
            AktivitasCard(
                aktivitas=aktivitas,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(aktivitas) } // Mengirim seluruh objek tanaman
                    .padding(8.dp),
                onDeleteClick = { onDeleteClick(aktivitas) }
            )
        }
    }
}

@Composable
fun AktivitasCard(
    aktivitas: AktivitasPertanian,
    modifier: Modifier = Modifier,
    onDeleteClick: (AktivitasPertanian) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Catatan Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = aktivitas.id_aktivitas,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(aktivitas) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Catatan Panen",
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            // Baris kedua: Semua data ditampilkan horizontal
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ID Aktifitas:", style = MaterialTheme.typography.bodyMedium)
                Text(aktivitas.id_aktivitas, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ID Tanaman:", style = MaterialTheme.typography.bodyMedium)
                Text(aktivitas.id_tanaman, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ID Pekerja:", style = MaterialTheme.typography.bodyMedium)
                Text(aktivitas.id_pekerja, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tanggal Aktivitas:", style = MaterialTheme.typography.bodyMedium)
                Text(aktivitas.tanggal_aktivitas, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Deskripsi Aktivitas:", style = MaterialTheme.typography.bodyMedium)
                Text(aktivitas.deskripsi_aktivitas, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}