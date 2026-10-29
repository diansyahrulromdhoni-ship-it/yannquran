package com.example.service.audio

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import com.example.data.repository.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PrayerAudioEndpoints {
    const val SUBUH = "https://wflq2j4mi1smqyku.public.blob.vercel-storage.com/adzan_subuh.mp3"
    const val REGULER = "https://wflq2j4mi1smqyku.public.blob.vercel-storage.com/adzan.mp3"
    const val IMSAK = "https://wflq2j4mi1smqyku.public.blob.vercel-storage.com/imsak.mp3"
    const val TERBIT = "https://wflq2j4mi1smqyku.public.blob.vercel-storage.com/ayam.mp3"

    fun getUrlForPrayer(prayerName: String): String {
        return when (prayerName.lowercase().trim()) {
            "subuh" -> SUBUH
            "imsak" -> IMSAK
            "terbit", "syuruq" -> TERBIT
            "dzuhur", "ashar", "maghrib", "isya" -> REGULER
            else -> REGULER
        }
    }

    fun getLabelForPrayer(prayerName: String): String {
        return when (prayerName.lowercase().trim()) {
            "subuh" -> "Azan Subuh (Assalatu Khairum Minan Naum)"
            "imsak" -> "Peringatan Waktu Imsak (Waktu Berhenti Sahur)"
            "terbit", "syuruq" -> "Waktu Terbit Matahari (Syuruq)"
            else -> "Kumandang Azan $prayerName"
        }
    }
}

data class PrayerAlertState(
    val isAlertActive: Boolean = false,
    val prayerName: String = "",
    val title: String = "",
    val time: String = "",
    val audioUrl: String = "",
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val isTestMode: Boolean = false,
    val activeTestType: String? = null,
    val errorMessage: String? = null
)

class PrayerAudioManager(
    private val context: Context,
    private val prefs: PreferencesManager,
    private val murottalPlayer: AudioPlayerManager
) {
    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    private val _alertState = MutableStateFlow(PrayerAlertState())
    val alertState: StateFlow<PrayerAlertState> = _alertState.asStateFlow()

    init {
        createNotificationChannel()
    }

    fun checkAndTriggerPrayerAudio(
        prayerName: String,
        scheduledTime: String,
        dateFormatted: String
    ) {
        if (!prefs.isPrayerAudioEnabled()) return

        val reminderType = prefs.getPrayerReminderType()
        if (reminderType == "OFF") return

        if (!prefs.isPrayerSlotEnabled(prayerName)) return

        // Deduplication key per date and prayer
        val triggerKey = "${dateFormatted}_$prayerName"
        if (prefs.isPrayerTriggeredToday(triggerKey)) {
            // Already triggered for this prayer time today
            return
        }

        // Mark as triggered so it never triggers duplicate within this day
        prefs.markPrayerTriggered(triggerKey)

        // Stop any active murottal so audio streams do not overlap
        murottalPlayer.stop()

        // Vibrate phone
        vibrateForPrayer(prayerName)

        // Send local status notification if allowed
        showPrayerNotification(prayerName, scheduledTime)

        if (reminderType == "AUDIO_FULL") {
            val audioUrl = PrayerAudioEndpoints.getUrlForPrayer(prayerName)
            val title = PrayerAudioEndpoints.getLabelForPrayer(prayerName)

            playPrayerAudio(
                url = audioUrl,
                prayerName = prayerName,
                title = title,
                time = scheduledTime,
                isTest = false
            )
        } else {
            // VIBRATE_ONLY: Still show dismissable alert UI
            _alertState.value = PrayerAlertState(
                isAlertActive = true,
                prayerName = prayerName,
                title = "Waktu $prayerName Telah Tiba ($scheduledTime)",
                time = scheduledTime,
                isPlaying = false,
                isTestMode = false
            )
        }
    }

    fun testAudio(prayerType: String) {
        val (url, name, title) = when (prayerType.uppercase()) {
            "SUBUH" -> Triple(PrayerAudioEndpoints.SUBUH, "Subuh", "Uji Azan Subuh")
            "IMSAK" -> Triple(PrayerAudioEndpoints.IMSAK, "Imsak", "Uji Audio Imsak")
            "TERBIT" -> Triple(PrayerAudioEndpoints.TERBIT, "Terbit", "Uji Audio Terbit (Ayam Berkokok)")
            else -> Triple(PrayerAudioEndpoints.REGULER, "Dzuhur/Ashar/Maghrib/Isya", "Uji Azan Reguler")
        }

        // If currently testing this same audio and playing, stop it
        if (_alertState.value.isTestMode && _alertState.value.activeTestType == prayerType && _alertState.value.isPlaying) {
            stopAudio()
            return
        }

        // Stop any running murottal
        murottalPlayer.stop()

        val timeStr = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        playPrayerAudio(
            url = url,
            prayerName = name,
            title = title,
            time = timeStr,
            isTest = true,
            testType = prayerType
        )
    }

    private fun playPrayerAudio(
        url: String,
        prayerName: String,
        title: String,
        time: String,
        isTest: Boolean,
        testType: String? = null
    ) {
        stopAudioInternal()

        _alertState.value = PrayerAlertState(
            isAlertActive = true,
            prayerName = prayerName,
            title = title,
            time = time,
            audioUrl = url,
            isPlaying = false,
            isLoading = true,
            isTestMode = isTest,
            activeTestType = testType,
            errorMessage = null
        )

        try {
            val volume = prefs.getPrayerAudioVolume().coerceIn(0f, 1f)
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                )
                setDataSource(url)
                setVolume(volume, volume)
                setOnPreparedListener { mp ->
                    mp.start()
                    _alertState.value = _alertState.value.copy(
                        isPlaying = true,
                        isLoading = false
                    )
                }
                setOnCompletionListener {
                    _alertState.value = _alertState.value.copy(
                        isPlaying = false,
                        isAlertActive = false,
                        activeTestType = null
                    )
                    stopAudioInternal()
                }
                setOnErrorListener { _, what, extra ->
                    _alertState.value = _alertState.value.copy(
                        isPlaying = false,
                        isLoading = false,
                        errorMessage = "Gagal memuat audio: Error $what, $extra"
                    )
                    stopAudioInternal()
                    true
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            _alertState.value = _alertState.value.copy(
                isPlaying = false,
                isLoading = false,
                errorMessage = "Gagal memutar audio: ${e.localizedMessage}"
            )
            stopAudioInternal()
        }
    }

    fun dismissAlert() {
        stopAudio()
    }

    fun stopAudio() {
        stopAudioInternal()
        _alertState.value = PrayerAlertState()
    }

    private fun stopAudioInternal() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.reset()
                it.release()
            }
        } catch (e: Exception) {
            // Ignore
        }
        mediaPlayer = null
    }

    private fun vibrateForPrayer(prayerName: String) {
        try {
            val pattern = longArrayOf(0, 500, 300, 500, 300, 800)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vm?.defaultVibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                val v = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                v?.vibrate(pattern, -1)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "prayer_notifications_channel",
                "Pengingat Waktu Salat",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifikasi kumandang azan, imsak, dan waktu salat Yann Quran"
            }
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            nm?.createNotificationChannel(channel)
        }
    }

    private fun showPrayerNotification(prayerName: String, time: String) {
        try {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            val notification = NotificationCompat.Builder(context, "prayer_notifications_channel")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Waktu $prayerName Telah Tiba ($time)")
                .setContentText("Mari tunaikan ibadah salat tepat waktu. Yann Quran")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()
            nm?.notify(1001, notification)
        } catch (e: Exception) {
            // Notification dispatch optional
        }
    }

    fun release() {
        stopAudio()
    }
}
