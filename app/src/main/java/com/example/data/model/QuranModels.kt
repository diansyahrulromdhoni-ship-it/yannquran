package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Surah(
    val nomor: Int,
    val nama: String,
    val namaLatin: String,
    val jumlahAyat: Int,
    val tempatTurun: String,
    val arti: String,
    val deskripsi: String,
    val audioFull: Map<String, String> = emptyMap()
)

data class Ayat(
    val nomorAyat: Int,
    val teksArab: String,
    val teksLatin: String,
    val teksIndonesia: String,
    val audio: Map<String, String> = emptyMap()
)

data class SurahDetail(
    val nomor: Int,
    val nama: String,
    val namaLatin: String,
    val jumlahAyat: Int,
    val tempatTurun: String,
    val arti: String,
    val deskripsi: String,
    val audioFull: Map<String, String> = emptyMap(),
    val ayat: List<Ayat> = emptyList()
)

data class TafsirItem(
    val ayat: Int,
    val teks: String
)

data class SurahTafsir(
    val nomor: Int,
    val nama: String,
    val namaLatin: String,
    val tafsir: List<TafsirItem> = emptyList()
)

data class Bookmark(
    val id: String = "",
    val surahNomor: Int,
    val surahNamaLatin: String,
    val ayatNomor: Int,
    val teksArab: String,
    val teksIndonesia: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class LastRead(
    val surahNomor: Int,
    val surahNamaLatin: String,
    val ayatNomor: Int,
    val timestamp: Long = System.currentTimeMillis()
)

data class Reciter(
    val id: String,
    val name: String,
    val origin: String,
    val key: String
)

val AvailableReciters = listOf(
    Reciter("01", "Misyari Rasyid Al-Afasy", "Kuwait", "01"),
    Reciter("02", "Abdullah Al-Juhany", "Arab Saudi", "02"),
    Reciter("03", "Abdurrahman As-Sudais", "Arab Saudi", "03"),
    Reciter("04", "Ibrahim Al-Dossari", "Arab Saudi", "04"),
    Reciter("05", "Ahmad Saud", "Arab Saudi", "05")
)
