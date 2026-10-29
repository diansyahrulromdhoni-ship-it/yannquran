package com.example.data.api

import com.example.data.model.Ayat
import com.example.data.model.Surah
import com.example.data.model.SurahDetail
import com.example.data.model.SurahTafsir
import com.example.data.model.TafsirItem
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

// Response wrappers
data class EQuranBaseResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)

data class SurahDto(
    val nomor: Int,
    val nama: String,
    val namaLatin: String,
    val jumlahAyat: Int,
    val tempatTurun: String,
    val arti: String,
    val deskripsi: String,
    val audioFull: Map<String, String>? = null
) {
    fun toSurah(): Surah = Surah(
        nomor = nomor,
        nama = nama,
        namaLatin = namaLatin,
        jumlahAyat = jumlahAyat,
        tempatTurun = tempatTurun,
        arti = arti,
        deskripsi = deskripsi,
        audioFull = audioFull ?: emptyMap()
    )
}

data class AyatDto(
    val nomorAyat: Int,
    val teksArab: String,
    val teksLatin: String,
    val teksIndonesia: String,
    val audio: Map<String, String>? = null
) {
    fun toAyat(): Ayat = Ayat(
        nomorAyat = nomorAyat,
        teksArab = teksArab,
        teksLatin = teksLatin,
        teksIndonesia = teksIndonesia,
        audio = audio ?: emptyMap()
    )
}

data class SurahDetailDto(
    val nomor: Int,
    val nama: String,
    val namaLatin: String,
    val jumlahAyat: Int,
    val tempatTurun: String,
    val arti: String,
    val deskripsi: String,
    val audioFull: Map<String, String>? = null,
    val ayat: List<AyatDto>? = null
) {
    fun toSurahDetail(): SurahDetail = SurahDetail(
        nomor = nomor,
        nama = nama,
        namaLatin = namaLatin,
        jumlahAyat = jumlahAyat,
        tempatTurun = tempatTurun,
        arti = arti,
        deskripsi = deskripsi,
        audioFull = audioFull ?: emptyMap(),
        ayat = ayat?.map { it.toAyat() } ?: emptyList()
    )
}

data class TafsirItemDto(
    val ayat: Int,
    val teks: String
) {
    fun toTafsirItem(): TafsirItem = TafsirItem(ayat = ayat, teks = teks)
}

data class SurahTafsirDto(
    val nomor: Int,
    val nama: String,
    val namaLatin: String,
    val tafsir: List<TafsirItemDto>? = null
) {
    fun toSurahTafsir(): SurahTafsir = SurahTafsir(
        nomor = nomor,
        nama = nama,
        namaLatin = namaLatin,
        tafsir = tafsir?.map { it.toTafsirItem() } ?: emptyList()
    )
}

interface EQuranApiService {
    @GET("api/v2/surat")
    suspend fun getAllSurahs(): EQuranBaseResponse<List<SurahDto>>

    @GET("api/v2/surat/{nomor}")
    suspend fun getSurahDetail(@Path("nomor") nomor: Int): EQuranBaseResponse<SurahDetailDto>

    @GET("api/v2/tafsir/{nomor}")
    suspend fun getSurahTafsir(@Path("nomor") nomor: Int): EQuranBaseResponse<SurahTafsirDto>
}

object EQuranApiClient {
    private const val BASE_URL = "https://equran.id/"

    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .build()

    val apiService: EQuranApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EQuranApiService::class.java)
    }
}
