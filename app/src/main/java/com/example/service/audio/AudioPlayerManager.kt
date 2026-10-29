package com.example.service.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class AudioPlayerState(
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val currentUrl: String = "",
    val title: String = "",
    val subtitle: String = "",
    val currentPositionMs: Int = 0,
    val durationMs: Int = 0,
    val error: String? = null
)

class AudioPlayerManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var progressJob: Job? = null

    private val _playerState = MutableStateFlow(AudioPlayerState())
    val playerState: StateFlow<AudioPlayerState> = _playerState.asStateFlow()

    fun playAudio(url: String, title: String, subtitle: String) {
        if (url.isBlank()) return

        // If clicking the same URL currently playing, toggle pause
        if (_playerState.value.currentUrl == url && mediaPlayer != null) {
            togglePlayPause()
            return
        }

        stop()

        _playerState.value = AudioPlayerState(
            isLoading = true,
            currentUrl = url,
            title = title,
            subtitle = subtitle
        )

        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                setOnPreparedListener { mp ->
                    mp.start()
                    val dur = mp.duration
                    _playerState.value = _playerState.value.copy(
                        isPlaying = true,
                        isLoading = false,
                        durationMs = if (dur > 0) dur else 0
                    )
                    startProgressTracker()
                }
                setOnCompletionListener {
                    _playerState.value = _playerState.value.copy(
                        isPlaying = false,
                        currentPositionMs = _playerState.value.durationMs
                    )
                    stopProgressTracker()
                }
                setOnErrorListener { _, what, extra ->
                    _playerState.value = _playerState.value.copy(
                        isPlaying = false,
                        isLoading = false,
                        error = "Gagal memutar audio (Error $what, $extra)"
                    )
                    stopProgressTracker()
                    true
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            _playerState.value = _playerState.value.copy(
                isLoading = false,
                isPlaying = false,
                error = "Koneksi audio gagal: ${e.localizedMessage}"
            )
        }
    }

    fun togglePlayPause() {
        val mp = mediaPlayer ?: return
        try {
            if (mp.isPlaying) {
                mp.pause()
                _playerState.value = _playerState.value.copy(isPlaying = false)
                stopProgressTracker()
            } else {
                mp.start()
                _playerState.value = _playerState.value.copy(isPlaying = true)
                startProgressTracker()
            }
        } catch (e: Exception) {
            // Player state error
        }
    }

    fun seekTo(positionMs: Int) {
        val mp = mediaPlayer ?: return
        try {
            mp.seekTo(positionMs)
            _playerState.value = _playerState.value.copy(currentPositionMs = positionMs)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun stop() {
        stopProgressTracker()
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
        _playerState.value = AudioPlayerState()
    }

    private fun startProgressTracker() {
        stopProgressTracker()
        progressJob = scope.launch {
            while (isActive) {
                val mp = mediaPlayer
                if (mp != null && mp.isPlaying) {
                    val pos = mp.currentPosition
                    val dur = mp.duration
                    _playerState.value = _playerState.value.copy(
                        currentPositionMs = pos,
                        durationMs = if (dur > 0) dur else _playerState.value.durationMs
                    )
                }
                delay(500)
            }
        }
    }

    private fun stopProgressTracker() {
        progressJob?.cancel()
        progressJob = null
    }

    fun release() {
        stop()
    }
}
