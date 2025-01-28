package com.example.a10_118.Model

import kotlinx.serialization.Serializable

@Serializable
data class AktivitasPertanian(
    val id_aktivitas: String,
    val id_tanaman: String,
    val id_pekerja: String,
    val tanggal_aktivitas: String,
    val deskripsi_aktivitas: String,
)

