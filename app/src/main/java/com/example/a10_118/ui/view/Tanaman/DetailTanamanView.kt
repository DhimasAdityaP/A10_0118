package com.example.a10_118.ui.view.Tanaman

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_118.Model.Tanaman
import com.example.a10_118.ui.navigation.DestinasiNavigasi
import com.example.a10_118.ui.viewmodel.Tanaman.DetailTanamanViewModel
import com.example.a10_118.ui.viewmodel.Tanaman.TanamanDetailUiState
import com.example.a10_118.ui.viewmodel.Tanaman.TanamanPenyediaViewModel

object DestinasiTanamDetail : DestinasiNavigasi {
    override val route = "tanaman detail"
    override val titleRes = "Detail tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTanamanView(
    idtanaman: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    viewModel: DetailTanamanViewModel = viewModel(factory = TanamanPenyediaViewModel.Factory)
) {
    val uiState by viewModel.detailUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Pemanggilan getTanamanById dipindahkan ke dalam LaunchedEffect
    LaunchedEffect(idtanaman) {
        viewModel.getTanamanById(idtanaman)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = { Text("Detail Tanaman") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getTanamanById(idtanaman) }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Tanaman")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is TanamanDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is TanamanDetailUiState.Success -> {
                val tanaman = (uiState as TanamanDetailUiState.Success).tanaman
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailTanaman(
                        tanaman = tanaman,
                        onCardClick = { /* Tambahkan logika klik jika diperlukan */ }
                    )
                }
            }
            is TanamanDetailUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Gagal memuat data",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun ItemDetailTanaman(
    modifier: Modifier = Modifier,
    tanaman: Tanaman,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() }, // Modifier.clickable untuk aksi klik
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            ComponentDetailTanaman(judul = "ID Tanaman", isinya = tanaman.idTanaman)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTanaman(judul = "Nama Tanaman", isinya = tanaman.namaTanaman)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTanaman(judul = "Periode Tanaman", isinya = tanaman.periodeTanaman)
            Spacer(modifier = Modifier.height(6.dp))

            ComponentDetailTanaman(judul = "Deskripsi Tanaman", isinya = tanaman.deskripsiTanaman)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun ComponentDetailTanaman(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
    }
}