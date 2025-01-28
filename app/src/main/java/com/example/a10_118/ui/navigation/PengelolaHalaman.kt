package com.example.a10_118.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a10_118.ui.view.HomeView.DestinasiSplash
import com.example.a10_118.ui.view.Pekerja.DestinasiHomePekerja
import com.example.a10_118.ui.view.Pekerja.DestinasiInsertPekerja
import com.example.a10_118.ui.view.Pekerja.EntryPekerjaScreen
import com.example.a10_118.ui.view.Pekerja.HomeScreenPekerja
import com.example.a10_118.ui.view.Tanaman.DestinasiInsert
import com.example.a10_118.ui.view.Tanaman.DestinasiTanamanHome
import com.example.a10_118.ui.view.Tanaman.DetailTanamanView
import com.example.a10_118.ui.view.Tanaman.EntryTanamanScreen
import com.example.a10_118.ui.view.Tanaman.HomeScreenTanaman

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = modifier
    ) {
        // Splash Screen
        composable(DestinasiSplash.route) {
            Splash(
                onTanamanClick = {
                    navController.navigate(DestinasiTanamanHome.route)
                },
                onPekerjaClick = {
                    // Tambahkan rute untuk Pekerja
                    navController.navigate(DestinasiHomePekerja.route)
                },
                onCatatanPanenClick = {
                    // Tambahkan rute untuk Catatan Panen
                    navController.navigate(DestinasiCatatan.route)
                },
                onAktivitasPertanianClick = {
                    // Tambahkan rute untuk Aktivitas Pertanian
                    navController.navigate(DestinasiAktifitas.route)
                }
            )
        }

        // Halaman Home Tanaman
        composable(DestinasiTanamanHome.route) {
            HomeScreenTanaman(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsert.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }

        // Halaman Entry Tanaman
        composable(DestinasiInsert.route) {
            EntryTanamanScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }
        // Detail Tanaman
        composable("detail/{id}") { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString("id")
            idTanaman?.let {
                DetailTanamanView(
                    idtanaman = it,
                    navigateBack = { navController.popBackStack() },
                    onClick = { /* Tambahkan logika klik edit */ }
                )
            }
        }


        // Halaman Home Pekerja
        composable(DestinasiHomePekerja.route) {
            HomeScreenPekerja(
                navigateToPekerjaEntry = {
                    navController.navigate(DestinasiInsertPekerja.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }

        // Halaman Entry Pekerja
        composable(DestinasiInsertPekerja.route) {
            EntryPekerjaScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }
        // HALAMAN HOME CATATAN
        composable(DestinasiCatatan.route) {
            HomeScreenCatatan(
                navigateToCatatanEntry = {
                    navController.navigate(DestinasiInsertCatatan.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }
        // Halaman Entry Pekerja
        composable(DestinasiInsertCatatan.route) {
            EntryCatatanScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }
        // HALAMAN HOME CATATAN
        composable(DestinasiAktifitas.route) {
            HomeScreenAktivitas(
                navigateToAktivitasEntry = {
                    navController.navigate(DestinasiInsertAktivitas.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }
        // Halaman Entry Pekerja
        composable(DestinasiInsertPekerja.route) {
            EntryPekerjaScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }
    }
}