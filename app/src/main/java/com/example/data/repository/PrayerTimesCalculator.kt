package com.example.data.repository

import java.util.Calendar
import java.util.Locale
import kotlin.math.*

data class CityLocation(
    val name: String,
    val province: String,
    val latitude: Double,
    val longitude: Double,
    val timezoneOffsetHours: Double
)

data class PrayerSchedule(
    val cityName: String,
    val dateFormatted: String,
    val hijriFormatted: String,
    val imsak: String,
    val subuh: String,
    val terbit: String,
    val dhuha: String,
    val dzuhur: String,
    val ashar: String,
    val maghrib: String,
    val isya: String,
    val nextPrayerName: String,
    val nextPrayerTime: String,
    val countdownText: String,
    val qiblaAngle: Float
)

object PrayerTimesCalculator {

    val cities: List<CityLocation> = listOf(
        CityLocation("Jakarta", "DKI Jakarta", -6.2088, 106.8456, 7.0),
        CityLocation("Bandung", "Jawa Barat", -6.9175, 107.6191, 7.0),
        CityLocation("Surabaya", "Jawa Timur", -7.2575, 112.7521, 7.0),
        CityLocation("Semarang", "Jawa Tengah", -6.9667, 110.4167, 7.0),
        CityLocation("Yogyakarta", "D.I. Yogyakarta", -7.7956, 110.3695, 7.0),
        CityLocation("Banda Aceh", "Aceh", 5.5483, 95.3238, 7.0),
        CityLocation("Medan", "Sumatera Utara", 3.5952, 98.6722, 7.0),
        CityLocation("Padang", "Sumatera Barat", -0.9471, 100.4172, 7.0),
        CityLocation("Palembang", "Sumatera Selatan", -2.9761, 104.7754, 7.0),
        CityLocation("Denpasar", "Bali", -8.6705, 115.2126, 8.0),
        CityLocation("Makassar", "Sulawesi Selatan", -5.1477, 119.4327, 8.0),
        CityLocation("Banjarmasin", "Kalimantan Selatan", -3.3194, 114.5908, 8.0),
        CityLocation("Balikpapan", "Kalimantan Timur", -1.2654, 116.8312, 8.0),
        CityLocation("Samarinda", "Kalimantan Timur", -0.5022, 117.1537, 8.0),
        CityLocation("Pontianak", "Kalimantan Barat", -0.0263, 109.3425, 7.0),
        CityLocation("Manado", "Sulawesi Utara", 1.4748, 124.8421, 8.0),
        CityLocation("Mataram", "Nusa Tenggara Barat", -8.5833, 116.1167, 8.0),
        CityLocation("Ambon", "Maluku", -3.6547, 128.1906, 9.0),
        CityLocation("Jayapura", "Papua", -2.5916, 140.6690, 9.0),
        CityLocation("Makkah", "Arab Saudi", 21.4225, 39.8262, 3.0),
        CityLocation("Madinah", "Arab Saudi", 24.5247, 39.5692, 3.0)
    )

    fun calculateQiblaBearing(lat: Double, lng: Double): Float {
        val kaabaLat = Math.toRadians(21.422487)
        val kaabaLng = Math.toRadians(39.826206)
        val userLat = Math.toRadians(lat)
        val userLng = Math.toRadians(lng)

        val deltaLng = kaabaLng - userLng
        val y = sin(deltaLng)
        val x = cos(userLat) * tan(kaabaLat) - sin(userLat) * cos(deltaLng)
        val qibla = Math.toDegrees(atan2(y, x))
        return ((qibla + 360) % 360).toFloat()
    }

    fun calculateSchedule(
        city: CityLocation = cities[0],
        calendar: Calendar = Calendar.getInstance()
    ): PrayerSchedule {
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val lat = city.latitude
        val lng = city.longitude
        val tz = city.timezoneOffsetHours

        // Solar Declination & Equation of Time approximation
        val b = 2.0 * Math.PI * (dayOfYear - 81) / 365.0
        val eot = 9.87 * sin(2 * b) - 7.53 * cos(b) - 1.5 * sin(b) // minutes
        val declination = Math.toRadians(23.45 * sin(Math.toRadians(360.0 / 365.0 * (dayOfYear - 81)))) // radians

        // Solar Noon in hours (standard time)
        val noon = 12.0 + (tz * 15.0 - lng) / 15.0 - (eot / 60.0)

        val latRad = Math.toRadians(lat)

        // Helper to calculate hour angle from sun altitude angle
        fun hourAngle(altitudeDeg: Double): Double {
            val altRad = Math.toRadians(altitudeDeg)
            val cosHA = (sin(altRad) - sin(latRad) * sin(declination)) / (cos(latRad) * cos(declination))
            val clamped = cosHA.coerceIn(-1.0, 1.0)
            return Math.toDegrees(acos(clamped)) / 15.0
        }

        // Subuh: sun at -20 degrees (Kemenag standard)
        val haSubuh = hourAngle(-20.0)
        val subuhDec = noon - haSubuh

        // Sunrise: sun at -0.833 degrees
        val haSunrise = hourAngle(-0.833)
        val sunriseDec = noon - haSunrise

        // Sunset: sun at -0.833 degrees
        val sunsetDec = noon + haSunrise

        // Ashar: shadow length = object length + noon shadow
        val noonAlt = 90.0 - Math.toDegrees(abs(latRad - declination))
        val noonShadow = 1.0 / tan(Math.toRadians(noonAlt))
        val asharAlt = Math.toDegrees(atan(1.0 / (1.0 + noonShadow)))
        val haAshar = hourAngle(asharAlt)
        val asharDec = noon + haAshar

        // Isya: sun at -18 degrees (Kemenag standard)
        val haIsya = hourAngle(-18.0)
        val isyaDec = noon + haIsya

        // Add 2 minutes ihtiyat (safety margin)
        fun formatHour(decimalHours: Double): String {
            val totalMinutes = ((decimalHours * 60.0) + 2.0).toInt().coerceAtLeast(0)
            val h = (totalMinutes / 60) % 24
            val m = totalMinutes % 60
            return String.format(Locale.getDefault(), "%02d:%02d", h, m)
        }

        fun toMinutes(decimalHours: Double): Int {
            return ((decimalHours * 60.0) + 2.0).toInt()
        }

        val subuhStr = formatHour(subuhDec)
        val imsakStr = formatHour(subuhDec - (10.0 / 60.0))
        val terbitStr = formatHour(sunriseDec)
        val dhuhaStr = formatHour(sunriseDec + (20.0 / 60.0))
        val dzuhurStr = formatHour(noon)
        val asharStr = formatHour(asharDec)
        val maghribStr = formatHour(sunsetDec)
        val isyaStr = formatHour(isyaDec)

        // Calculate countdown to next prayer
        val currentMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
        val prayerTimes = listOf(
            "Subuh" to toMinutes(subuhDec),
            "Dzuhur" to toMinutes(noon),
            "Ashar" to toMinutes(asharDec),
            "Maghrib" to toMinutes(sunsetDec),
            "Isya" to toMinutes(isyaDec)
        )

        var nextName = "Subuh"
        var nextTime = subuhStr
        var diffMinutes = 0

        for ((name, timeMin) in prayerTimes) {
            if (timeMin > currentMinutes) {
                nextName = name
                nextTime = when (name) {
                    "Subuh" -> subuhStr
                    "Dzuhur" -> dzuhurStr
                    "Ashar" -> asharStr
                    "Maghrib" -> maghribStr
                    else -> isyaStr
                }
                diffMinutes = timeMin - currentMinutes
                break
            }
        }

        if (diffMinutes == 0) {
            // Next is tomorrow's Subuh
            nextName = "Subuh (Besok)"
            nextTime = subuhStr
            diffMinutes = (24 * 60 - currentMinutes) + toMinutes(subuhDec)
        }

        val hoursRemaining = diffMinutes / 60
        val minsRemaining = diffMinutes % 60
        val countdown = if (hoursRemaining > 0) {
            "$hoursRemaining jam $minsRemaining mnt menuju $nextName"
        } else {
            "$minsRemaining menit menuju $nextName"
        }

        val monthsIndo = listOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
        val dateFormatted = "${calendar.get(Calendar.DAY_OF_MONTH)} ${monthsIndo[calendar.get(Calendar.MONTH)]} ${calendar.get(Calendar.YEAR)}"

        // Approximated Hijri date
        val hijriFormatted = estimateHijriDate(calendar)

        val qibla = calculateQiblaBearing(lat, lng)

        return PrayerSchedule(
            cityName = city.name,
            dateFormatted = dateFormatted,
            hijriFormatted = hijriFormatted,
            imsak = imsakStr,
            subuh = subuhStr,
            terbit = terbitStr,
            dhuha = dhuhaStr,
            dzuhur = dzuhurStr,
            ashar = asharStr,
            maghrib = maghribStr,
            isya = isyaStr,
            nextPrayerName = nextName,
            nextPrayerTime = nextTime,
            countdownText = countdown,
            qiblaAngle = qibla
        )
    }

    private fun estimateHijriDate(calendar: Calendar): String {
        // Kuwaiti algorithm approximation for Islamic Hijri Calendar
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        var m = month
        var y = year
        if (m < 3) {
            y -= 1
            m += 12
        }

        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        val jd = floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5

        val z = jd - 1948438.5
        val cyc = floor(z / 10631.0)
        val rem = z - cyc * 10631.0
        val j = floor((rem) / 354.366)
        val iy = 30 * cyc + j
        val remDays = rem - floor(j * 354.366)
        val im = min(12.0, ceil(remDays / 29.5)).toInt()
        val id = (remDays - floor((im - 1) * 29.5)).toInt().coerceIn(1, 30)

        val hijriMonths = listOf(
            "Muharram", "Safar", "Rabi'ul Awwal", "Rabi'ul Akhir",
            "Jumadil Ula", "Jumadil Akhir", "Rajab", "Sya'ban",
            "Ramadhan", "Syawwal", "Dzulqa'dah", "Dzulhijjah"
        )
        val monthName = hijriMonths.getOrElse((im - 1).coerceIn(0, 11)) { "Sya'ban" }
        return "$id $monthName ${iy.toInt()} H"
    }
}
