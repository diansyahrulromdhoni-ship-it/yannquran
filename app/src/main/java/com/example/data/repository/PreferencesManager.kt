package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.Bookmark
import com.example.data.model.LastRead
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("yann_quran_prefs", Context.MODE_PRIVATE)
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val bookmarkListType = Types.newParameterizedType(List::class.java, Bookmark::class.java)
    private val bookmarkAdapter = moshi.adapter<List<Bookmark>>(bookmarkListType)

    fun getLastRead(): LastRead {
        val surahNo = prefs.getInt("last_read_surah", 1)
        val surahName = prefs.getString("last_read_surah_name", "Al-Fatihah") ?: "Al-Fatihah"
        val ayatNo = prefs.getInt("last_read_ayat", 1)
        val timestamp = prefs.getLong("last_read_time", System.currentTimeMillis())
        return LastRead(surahNo, surahName, ayatNo, timestamp)
    }

    fun saveLastRead(surahNomor: Int, surahNamaLatin: String, ayatNomor: Int) {
        prefs.edit()
            .putInt("last_read_surah", surahNomor)
            .putString("last_read_surah_name", surahNamaLatin)
            .putInt("last_read_ayat", ayatNomor)
            .putLong("last_read_time", System.currentTimeMillis())
            .apply()
    }

    fun getBookmarks(): List<Bookmark> {
        val json = prefs.getString("bookmarks_json", null) ?: return emptyList()
        return try {
            bookmarkAdapter.fromJson(json) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun toggleBookmark(bookmark: Bookmark): Boolean {
        val current = getBookmarks().toMutableList()
        val existingIndex = current.indexOfFirst { it.surahNomor == bookmark.surahNomor && it.ayatNomor == bookmark.ayatNomor }
        val isAdded: Boolean
        if (existingIndex >= 0) {
            current.removeAt(existingIndex)
            isAdded = false
        } else {
            current.add(0, bookmark)
            isAdded = true
        }
        val json = bookmarkAdapter.toJson(current)
        prefs.edit().putString("bookmarks_json", json).apply()
        return isAdded
    }

    fun isBookmarked(surahNomor: Int, ayatNomor: Int): Boolean {
        return getBookmarks().any { it.surahNomor == surahNomor && it.ayatNomor == ayatNomor }
    }

    fun getSelectedCity(): String {
        return prefs.getString("selected_city", "Jakarta") ?: "Jakarta"
    }

    fun setSelectedCity(cityName: String) {
        prefs.edit().putString("selected_city", cityName).apply()
    }

    fun getArabicFontSize(): Float {
        return prefs.getFloat("arabic_font_size", 26f)
    }

    fun setArabicFontSize(size: Float) {
        prefs.edit().putFloat("arabic_font_size", size).apply()
    }

    fun getShowLatin(): Boolean {
        return prefs.getBoolean("show_latin", true)
    }

    fun setShowLatin(show: Boolean) {
        prefs.edit().putBoolean("show_latin", show).apply()
    }

    fun getShowTranslation(): Boolean {
        return prefs.getBoolean("show_translation", true)
    }

    fun setShowTranslation(show: Boolean) {
        prefs.edit().putBoolean("show_translation", show).apply()
    }

    fun getSelectedReciterKey(): String {
        return prefs.getString("selected_reciter", "05") ?: "05" // Default Misyari Rasyid
    }

    fun setSelectedReciterKey(key: String) {
        prefs.edit().putString("selected_reciter", key).apply()
    }

    // Prayer Audio Notification Settings
    fun isPrayerAudioEnabled(): Boolean {
        return prefs.getBoolean("prayer_audio_enabled", true)
    }

    fun setPrayerAudioEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("prayer_audio_enabled", enabled).apply()
    }

    fun getPrayerReminderType(): String {
        return prefs.getString("prayer_reminder_type", "AUDIO_FULL") ?: "AUDIO_FULL"
    }

    fun setPrayerReminderType(type: String) {
        prefs.edit().putString("prayer_reminder_type", type).apply()
    }

    fun getPrayerAudioVolume(): Float {
        return prefs.getFloat("prayer_audio_volume", 0.85f)
    }

    fun setPrayerAudioVolume(volume: Float) {
        prefs.edit().putFloat("prayer_audio_volume", volume).apply()
    }

    fun isPrayerSlotEnabled(slot: String): Boolean {
        return prefs.getBoolean("prayer_slot_${slot.lowercase()}", true)
    }

    fun setPrayerSlotEnabled(slot: String, enabled: Boolean) {
        prefs.edit().putBoolean("prayer_slot_${slot.lowercase()}", enabled).apply()
    }

    fun isPrayerTriggeredToday(triggerKey: String): Boolean {
        val set = prefs.getStringSet("triggered_prayers_today", emptySet()) ?: emptySet()
        return set.contains(triggerKey)
    }

    fun markPrayerTriggered(triggerKey: String) {
        val currentSet = prefs.getStringSet("triggered_prayers_today", emptySet())?.toMutableSet() ?: mutableSetOf()
        currentSet.add(triggerKey)
        prefs.edit().putStringSet("triggered_prayers_today", currentSet).apply()
    }
}
