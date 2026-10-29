package com.example.service.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.data.repository.CityLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.TimeZone

data class GpsState(
    val isLocating: Boolean = false,
    val isGpsActive: Boolean = false,
    val location: CityLocation? = null,
    val accuracyMeters: Float = 0f,
    val errorMessage: String? = null
)

class GpsLocationManager(private val context: Context) {
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    private val _gpsState = MutableStateFlow(GpsState())
    val gpsState: StateFlow<GpsState> = _gpsState.asStateFlow()

    fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fine || coarse
    }

    @SuppressLint("MissingPermission")
    fun requestCurrentLocation(onLocationReceived: (CityLocation) -> Unit = {}) {
        if (!hasLocationPermission()) {
            _gpsState.value = _gpsState.value.copy(
                isLocating = false,
                errorMessage = "Izin lokasi diperlukan untuk fitur GPS otomatis."
            )
            return
        }

        _gpsState.value = _gpsState.value.copy(
            isLocating = true,
            errorMessage = null
        )

        // Try getting last known location first for immediate responsiveness
        val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER, LocationManager.PASSIVE_PROVIDER)
        var bestLocation: Location? = null

        for (provider in providers) {
            try {
                if (locationManager.isProviderEnabled(provider)) {
                    val loc = locationManager.getLastKnownLocation(provider)
                    if (loc != null && (bestLocation == null || loc.accuracy < bestLocation.accuracy)) {
                        bestLocation = loc
                    }
                }
            } catch (e: Exception) {
                // Ignore provider error
            }
        }

        if (bestLocation != null) {
            processLocation(bestLocation, onLocationReceived)
        }

        // Also request a fresh update from GPS / Network
        val singleUpdateListener = object : LocationListener {
            override fun onLocationChanged(loc: Location) {
                locationManager.removeUpdates(this)
                processLocation(loc, onLocationReceived)
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        try {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0f,
                    singleUpdateListener,
                    context.mainLooper
                )
            } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    singleUpdateListener,
                    context.mainLooper
                )
            } else if (bestLocation == null) {
                _gpsState.value = _gpsState.value.copy(
                    isLocating = false,
                    errorMessage = "Sensor GPS/Lokasi perangkat sedang nonaktif."
                )
            }
        } catch (e: Exception) {
            if (bestLocation == null) {
                _gpsState.value = _gpsState.value.copy(
                    isLocating = false,
                    errorMessage = "Gagal mengakses GPS: ${e.localizedMessage}"
                )
            }
        }
    }

    private fun processLocation(loc: Location, onLocationReceived: (CityLocation) -> Unit) {
        scope.launch {
            val tzOffsetHours = withContext(Dispatchers.Default) {
                val tz = TimeZone.getDefault()
                tz.rawOffset.toDouble() / 3600000.0
            }

            var cityName = "Lokasi Saya"
            var provinceName = String.format(Locale.US, "GPS: %.3f°, %.3f°", loc.latitude, loc.longitude)

            try {
                withContext(Dispatchers.IO) {
                    val geocoder = Geocoder(context, Locale("id", "ID"))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val addr = addresses[0]
                            cityName = addr.subAdminArea ?: addr.locality ?: addr.adminArea ?: "Lokasi GPS"
                            provinceName = addr.adminArea ?: provinceName
                        }
                    } else {
                        @Suppress("DEPRECATION")
                        val addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val addr = addresses[0]
                            cityName = addr.subAdminArea ?: addr.locality ?: addr.adminArea ?: "Lokasi GPS"
                            provinceName = addr.adminArea ?: provinceName
                        }
                    }
                }
            } catch (e: Exception) {
                // Keep default lat/lng label
            }

            val cityLocation = CityLocation(
                name = cityName,
                province = provinceName,
                latitude = loc.latitude,
                longitude = loc.longitude,
                timezoneOffsetHours = if (tzOffsetHours != 0.0) tzOffsetHours else 7.0
            )

            _gpsState.value = GpsState(
                isLocating = false,
                isGpsActive = true,
                location = cityLocation,
                accuracyMeters = loc.accuracy,
                errorMessage = null
            )

            onLocationReceived(cityLocation)
        }
    }
}
