package com.example.ui

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Bookmark
import com.example.data.model.LastRead
import com.example.data.model.Surah
import com.example.data.model.SurahDetail
import com.example.data.model.SurahTafsir
import com.example.data.repository.CityLocation
import com.example.data.repository.PrayerSchedule
import com.example.data.repository.PrayerTimesCalculator
import com.example.data.repository.PreferencesManager
import com.example.data.repository.QuranRepository
import com.example.service.audio.AudioPlayerManager
import com.example.service.audio.AudioPlayerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

sealed class AppScreen {
    object Home : AppScreen()
    object SurahList : AppScreen()
    data class SurahDetailScreen(val surahNomor: Int, val surahName: String) : AppScreen()
    data class TafsirScreen(val surahNomor: Int, val surahName: String) : AppScreen()
    object PrayerTimes : AppScreen()
    object QiblaCompass : AppScreen()
    object YasinTahlil : AppScreen()
    object DoaDzikir : AppScreen()
    object Bookmarks : AppScreen()
}

class YannQuranViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val repository = QuranRepository()
    private val prefs = PreferencesManager(application)
    val audioPlayer = AudioPlayerManager(application)
    val prayerAudio = com.example.service.audio.PrayerAudioManager(application, prefs, audioPlayer)
    val prayerAlertState = prayerAudio.alertState
    val gpsManager = com.example.service.location.GpsLocationManager(application)
    val gpsState = gpsManager.gpsState

    // Prayer Audio Notification Settings States
    private val _isPrayerAudioEnabled = MutableStateFlow(prefs.isPrayerAudioEnabled())
    val isPrayerAudioEnabled: StateFlow<Boolean> = _isPrayerAudioEnabled.asStateFlow()

    private val _prayerReminderType = MutableStateFlow(prefs.getPrayerReminderType())
    val prayerReminderType: StateFlow<String> = _prayerReminderType.asStateFlow()

    private val _prayerAudioVolume = MutableStateFlow(prefs.getPrayerAudioVolume())
    val prayerAudioVolume: StateFlow<Float> = _prayerAudioVolume.asStateFlow()

    // Desktop / Tablet Mode State
    private val _isDesktopMode = MutableStateFlow(false)
    val isDesktopMode: StateFlow<Boolean> = _isDesktopMode.asStateFlow()

    // Navigation Stack
    private val _screenStack = MutableStateFlow<List<AppScreen>>(listOf(AppScreen.Home))
    val currentScreen: StateFlow<AppScreen> = MutableStateFlow(AppScreen.Home)
    private val _currentScreenState = MutableStateFlow<AppScreen>(AppScreen.Home)
    val screen: StateFlow<AppScreen> = _currentScreenState.asStateFlow()

    // Surahs & Reader States
    private val _surahs = MutableStateFlow<List<Surah>>(emptyList())
    val surahs: StateFlow<List<Surah>> = _surahs.asStateFlow()

    private val _surahsLoading = MutableStateFlow(true)
    val surahsLoading: StateFlow<Boolean> = _surahsLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _surahFilter = MutableStateFlow("Semua") // "Semua", "Makkiyyah", "Madaniyyah"
    val surahFilter: StateFlow<String> = _surahFilter.asStateFlow()

    private val _currentDetail = MutableStateFlow<SurahDetail?>(null)
    val currentDetail: StateFlow<SurahDetail?> = _currentDetail.asStateFlow()

    private val _detailLoading = MutableStateFlow(false)
    val detailLoading: StateFlow<Boolean> = _detailLoading.asStateFlow()

    private val _currentTafsir = MutableStateFlow<SurahTafsir?>(null)
    val currentTafsir: StateFlow<SurahTafsir?> = _currentTafsir.asStateFlow()

    private val _tafsirLoading = MutableStateFlow(false)
    val tafsirLoading: StateFlow<Boolean> = _tafsirLoading.asStateFlow()

    // Preferences & Persistence States
    private val _lastRead = MutableStateFlow(prefs.getLastRead())
    val lastRead: StateFlow<LastRead> = _lastRead.asStateFlow()

    private val _bookmarks = MutableStateFlow(prefs.getBookmarks())
    val bookmarks: StateFlow<List<Bookmark>> = _bookmarks.asStateFlow()

    private val _arabicFontSize = MutableStateFlow(prefs.getArabicFontSize())
    val arabicFontSize: StateFlow<Float> = _arabicFontSize.asStateFlow()

    private val _showLatin = MutableStateFlow(prefs.getShowLatin())
    val showLatin: StateFlow<Boolean> = _showLatin.asStateFlow()

    private val _showTranslation = MutableStateFlow(prefs.getShowTranslation())
    val showTranslation: StateFlow<Boolean> = _showTranslation.asStateFlow()

    private val _selectedReciterKey = MutableStateFlow(prefs.getSelectedReciterKey())
    val selectedReciterKey: StateFlow<String> = _selectedReciterKey.asStateFlow()

    // Prayer Times & Qibla
    private val _selectedCity = MutableStateFlow(
        PrayerTimesCalculator.cities.find { it.name == prefs.getSelectedCity() }
            ?: PrayerTimesCalculator.cities[0]
    )
    val selectedCity: StateFlow<CityLocation> = _selectedCity.asStateFlow()

    private val _prayerSchedule = MutableStateFlow(
        PrayerTimesCalculator.calculateSchedule(_selectedCity.value)
    )
    val prayerSchedule: StateFlow<PrayerSchedule> = _prayerSchedule.asStateFlow()

    // Compass Sensor State
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val _compassAzimuth = MutableStateFlow(0f)
    val compassAzimuth: StateFlow<Float> = _compassAzimuth.asStateFlow()

    private val _isQiblaAligned = MutableStateFlow(false)
    val isQiblaAligned: StateFlow<Boolean> = _isQiblaAligned.asStateFlow()

    // Tasbih State
    private val _tasbihCount = MutableStateFlow(0)
    val tasbihCount: StateFlow<Int> = _tasbihCount.asStateFlow()

    private val _tasbihTarget = MutableStateFlow(33)
    val tasbihTarget: StateFlow<Int> = _tasbihTarget.asStateFlow()

    // Periodic Prayer Countdown Job
    private var countdownJob: Job? = null

    init {
        loadSurahs()
        startPrayerTicker()
        registerCompassSensors()
    }

    fun navigateTo(newScreen: AppScreen) {
        val list = _screenStack.value.toMutableList()
        list.add(newScreen)
        _screenStack.value = list
        _currentScreenState.value = newScreen
    }

    fun navigateBack(): Boolean {
        val list = _screenStack.value.toMutableList()
        if (list.size > 1) {
            list.removeAt(list.size - 1)
            _screenStack.value = list
            _currentScreenState.value = list.last()
            return true
        }
        return false
    }

    fun navigateToTab(tabScreen: AppScreen) {
        _screenStack.value = listOf(tabScreen)
        _currentScreenState.value = tabScreen
    }

    private fun loadSurahs() {
        viewModelScope.launch {
            _surahsLoading.value = true
            val list = repository.getSurahs()
            _surahs.value = list
            _surahsLoading.value = false
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSurahFilter(filter: String) {
        _surahFilter.value = filter
    }

    fun openSurahDetail(nomor: Int, name: String) {
        navigateTo(AppScreen.SurahDetailScreen(nomor, name))
        viewModelScope.launch {
            _detailLoading.value = true
            val result = repository.getSurahDetail(nomor)
            result.onSuccess {
                _currentDetail.value = it
            }.onFailure {
                // Keep existing or fallback
            }
            _detailLoading.value = false
        }
    }

    fun openTafsir(nomor: Int, name: String) {
        navigateTo(AppScreen.TafsirScreen(nomor, name))
        viewModelScope.launch {
            _tafsirLoading.value = true
            val result = repository.getSurahTafsir(nomor)
            result.onSuccess {
                _currentTafsir.value = it
            }
            _tafsirLoading.value = false
        }
    }

    fun markLastRead(surahNomor: Int, surahNamaLatin: String, ayatNomor: Int) {
        prefs.saveLastRead(surahNomor, surahNamaLatin, ayatNomor)
        _lastRead.value = prefs.getLastRead()
        vibratePhone(40)
    }

    fun toggleBookmark(bookmark: Bookmark) {
        prefs.toggleBookmark(bookmark)
        _bookmarks.value = prefs.getBookmarks()
        vibratePhone(50)
    }

    fun isBookmarked(surahNomor: Int, ayatNomor: Int): Boolean {
        return _bookmarks.value.any { it.surahNomor == surahNomor && it.ayatNomor == ayatNomor }
    }

    fun selectCity(city: CityLocation) {
        _selectedCity.value = city
        prefs.setSelectedCity(city.name)
        _prayerSchedule.value = PrayerTimesCalculator.calculateSchedule(city)
    }

    fun setArabicFontSize(size: Float) {
        _arabicFontSize.value = size
        prefs.setArabicFontSize(size)
    }

    fun setShowLatin(show: Boolean) {
        _showLatin.value = show
        prefs.setShowLatin(show)
    }

    fun setShowTranslation(show: Boolean) {
        _showTranslation.value = show
        prefs.setShowTranslation(show)
    }

    fun setSelectedReciterKey(key: String) {
        _selectedReciterKey.value = key
        prefs.setSelectedReciterKey(key)
    }

    // GPS Location Detection
    fun requestGpsLocation() {
        gpsManager.requestCurrentLocation { city ->
            selectCity(city)
            vibratePhone(60)
        }
    }

    // Desktop / Large Screen Mode
    fun toggleDesktopMode() {
        _isDesktopMode.value = !_isDesktopMode.value
        vibratePhone(40)
    }

    fun setDesktopMode(enabled: Boolean) {
        _isDesktopMode.value = enabled
    }

    // Audio Playback Helpers
    fun playSurahAudio(surah: Surah) {
        val reciterKey = _selectedReciterKey.value
        val audioUrl = surah.audioFull[reciterKey]
            ?: surah.audioFull["05"]
            ?: surah.audioFull.values.firstOrNull()
            ?: "https://equran.nos.wjv-1.neo.id/audio-full/Misyari-Rasyid-Al-Afasi/${String.format("%03d", surah.nomor)}.mp3"

        audioPlayer.playAudio(
            url = audioUrl,
            title = "Surat ${surah.namaLatin}",
            subtitle = "${surah.arti} • Full Murottal"
        )
    }

    fun playAyatAudio(surahName: String, ayatNomor: Int, audioMap: Map<String, String>) {
        val reciterKey = _selectedReciterKey.value
        val audioUrl = audioMap[reciterKey]
            ?: audioMap["05"]
            ?: audioMap.values.firstOrNull()
            ?: ""

        if (audioUrl.isNotBlank()) {
            audioPlayer.playAudio(
                url = audioUrl,
                title = "$surahName : Ayat $ayatNomor",
                subtitle = "Tilawah Murottal Per Ayat"
            )
        }
    }

    // Tasbih Functions
    fun incrementTasbih() {
        val current = _tasbihCount.value + 1
        _tasbihCount.value = current
        if (current % _tasbihTarget.value == 0) {
            vibratePhone(120)
        } else {
            vibratePhone(30)
        }
    }

    fun resetTasbih() {
        _tasbihCount.value = 0
        vibratePhone(60)
    }

    fun setTasbihTarget(target: Int) {
        _tasbihTarget.value = target
        _tasbihCount.value = 0
    }

    // Prayer Audio Settings & Control Methods
    fun setPrayerAudioEnabled(enabled: Boolean) {
        _isPrayerAudioEnabled.value = enabled
        prefs.setPrayerAudioEnabled(enabled)
    }

    fun setPrayerReminderType(type: String) {
        _prayerReminderType.value = type
        prefs.setPrayerReminderType(type)
    }

    fun setPrayerAudioVolume(volume: Float) {
        _prayerAudioVolume.value = volume
        prefs.setPrayerAudioVolume(volume)
    }

    fun isPrayerSlotEnabled(slot: String): Boolean {
        return prefs.isPrayerSlotEnabled(slot)
    }

    fun setPrayerSlotEnabled(slot: String, enabled: Boolean) {
        prefs.setPrayerSlotEnabled(slot, enabled)
    }

    fun testPrayerAudio(prayerType: String) {
        prayerAudio.testAudio(prayerType)
    }

    fun dismissPrayerAlert() {
        prayerAudio.dismissAlert()
    }

    private fun startPrayerTicker() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                val schedule = PrayerTimesCalculator.calculateSchedule(_selectedCity.value)
                _prayerSchedule.value = schedule

                // Evaluate real-time prayer schedule trigger
                val currentTimeStr = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
                val todayStr = schedule.dateFormatted

                if (currentTimeStr == schedule.imsak) {
                    prayerAudio.checkAndTriggerPrayerAudio("Imsak", schedule.imsak, todayStr)
                } else if (currentTimeStr == schedule.subuh) {
                    prayerAudio.checkAndTriggerPrayerAudio("Subuh", schedule.subuh, todayStr)
                } else if (currentTimeStr == schedule.terbit) {
                    prayerAudio.checkAndTriggerPrayerAudio("Terbit", schedule.terbit, todayStr)
                } else if (currentTimeStr == schedule.dzuhur) {
                    prayerAudio.checkAndTriggerPrayerAudio("Dzuhur", schedule.dzuhur, todayStr)
                } else if (currentTimeStr == schedule.ashar) {
                    prayerAudio.checkAndTriggerPrayerAudio("Ashar", schedule.ashar, todayStr)
                } else if (currentTimeStr == schedule.maghrib) {
                    prayerAudio.checkAndTriggerPrayerAudio("Maghrib", schedule.maghrib, todayStr)
                } else if (currentTimeStr == schedule.isya) {
                    prayerAudio.checkAndTriggerPrayerAudio("Isya", schedule.isya, todayStr)
                }

                delay(15000) // update and check every 15 seconds
            }
        }
    }

    private fun registerCompassSensors() {
        val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        if (rotationVectorSensor != null) {
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            val orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
            if (orientationSensor != null) {
                sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_UI)
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        var degree = 0f
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            val orientation = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientation)
            degree = Math.toDegrees(orientation[0].toDouble()).toFloat()
            degree = (degree + 360) % 360
        } else if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
            degree = event.values[0]
        }

        // Low-pass smoothing filter
        val oldAzimuth = _compassAzimuth.value
        val smoothed = oldAzimuth + 0.15f * (degree - oldAzimuth)
        _compassAzimuth.value = smoothed

        val targetQibla = _prayerSchedule.value.qiblaAngle
        val diff = abs(smoothed - targetQibla)
        val aligned = diff <= 4f || diff >= 356f
        if (aligned != _isQiblaAligned.value) {
            _isQiblaAligned.value = aligned
            if (aligned) {
                vibratePhone(80)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun vibratePhone(durationMs: Long) {
        try {
            val context = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(durationMs)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
        sensorManager.unregisterListener(this)
        audioPlayer.release()
        prayerAudio.release()
    }
}
