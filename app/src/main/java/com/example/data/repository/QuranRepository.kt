package com.example.data.repository

import com.example.data.api.EQuranApiClient
import com.example.data.model.Surah
import com.example.data.model.SurahDetail
import com.example.data.model.SurahTafsir
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuranRepository {
    private val api = EQuranApiClient.apiService

    // In-memory cache for fast retrieval
    private var cachedSurahs: List<Surah>? = null
    private val cachedDetails = mutableMapOf<Int, SurahDetail>()
    private val cachedTafsirs = mutableMapOf<Int, SurahTafsir>()

    suspend fun getSurahs(): List<Surah> = withContext(Dispatchers.IO) {
        cachedSurahs?.let { return@withContext it }
        try {
            val response = api.getAllSurahs()
            if (response.code == 200 && response.data.isNotEmpty()) {
                val list = response.data.map { it.toSurah() }
                cachedSurahs = list
                return@withContext list
            }
        } catch (e: Exception) {
            // Fall back to complete offline 114 surah directory
        }
        val fallback = OfflineQuranData.all114Surahs
        cachedSurahs = fallback
        fallback
    }

    suspend fun getSurahDetail(nomor: Int): Result<SurahDetail> = withContext(Dispatchers.IO) {
        cachedDetails[nomor]?.let { return@withContext Result.success(it) }

        try {
            val response = api.getSurahDetail(nomor)
            if (response.code == 200) {
                val detail = response.data.toSurahDetail()
                cachedDetails[nomor] = detail
                return@withContext Result.success(detail)
            }
        } catch (e: Exception) {
            // Check offline built-in surahs
            val fallback = when (nomor) {
                1 -> OfflineQuranData.surahAlFatihah
                112 -> OfflineQuranData.surahAlIkhlas
                113 -> OfflineQuranData.surahAlFalaq
                114 -> OfflineQuranData.surahAnNas
                36 -> SurahDetail(
                    nomor = 36,
                    nama = "يس",
                    namaLatin = "Yasin",
                    jumlahAyat = 83,
                    tempatTurun = "Mekah",
                    arti = "Yasin",
                    deskripsi = "Surat Yasin adalah jantung Al-Qur'an (Qalb al-Qur'an).",
                    audioFull = mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-full/Misyari-Rasyid-Al-Afasi/036.mp3"),
                    ayat = TahlilData.yasinVerses
                )
                else -> null
            }
            if (fallback != null) {
                cachedDetails[nomor] = fallback
                return@withContext Result.success(fallback)
            }
            return@withContext Result.failure(e)
        }

        Result.failure(Exception("Gagal memuat detail surat"))
    }

    suspend fun getSurahTafsir(nomor: Int): Result<SurahTafsir> = withContext(Dispatchers.IO) {
        cachedTafsirs[nomor]?.let { return@withContext Result.success(it) }

        try {
            val response = api.getSurahTafsir(nomor)
            if (response.code == 200) {
                val tafsir = response.data.toSurahTafsir()
                cachedTafsirs[nomor] = tafsir
                return@withContext Result.success(tafsir)
            }
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }

        Result.failure(Exception("Gagal memuat tafsir surat"))
    }
}
