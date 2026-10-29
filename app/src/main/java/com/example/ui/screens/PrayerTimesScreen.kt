package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.PrayerTimesCalculator
import com.example.ui.AppScreen
import com.example.ui.YannQuranViewModel
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Emerald
import com.example.ui.theme.Forest
import com.example.ui.theme.ForestDark
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceDarkCard
import com.example.ui.theme.WarmWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTimesScreen(
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    val schedule by viewModel.prayerSchedule.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    val gpsState by viewModel.gpsState.collectAsState()
    val alertState by viewModel.prayerAlertState.collectAsState()
    val isAudioEnabled by viewModel.isPrayerAudioEnabled.collectAsState()
    val reminderType by viewModel.prayerReminderType.collectAsState()
    val audioVolume by viewModel.prayerAudioVolume.collectAsState()
    var showCityDialog by remember { mutableStateOf(false) }

    val locationPermissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (fineGranted || coarseGranted) {
            viewModel.requestGpsLocation()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ForestDark)
            .testTag("prayer_times_screen")
    ) {
        // Header
        Surface(
            color = Forest,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Jadwal Salat",
                            color = Gold,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = schedule.hijriFormatted,
                            color = GoldLight,
                            fontSize = 12.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // GPS Button
                        Surface(
                            color = if (gpsState.isGpsActive) Gold else Color(0x33D4AF37),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Gold),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    if (viewModel.gpsManager.hasLocationPermission()) {
                                        viewModel.requestGpsLocation()
                                    } else {
                                        locationPermissionLauncher.launch(
                                            arrayOf(
                                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                                            )
                                        )
                                    }
                                }
                                .testTag("btn_gps_sync")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                if (gpsState.isLocating) {
                                    CircularProgressIndicator(
                                        color = if (gpsState.isGpsActive) Forest else Gold,
                                        strokeWidth = 2.dp,
                                        modifier = Modifier.size(14.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.GpsFixed,
                                        contentDescription = "Deteksi GPS",
                                        tint = if (gpsState.isGpsActive) Forest else Gold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (gpsState.isLocating) "Mencari..." else if (gpsState.isGpsActive) "GPS Aktif" else "Deteksi GPS",
                                    color = if (gpsState.isGpsActive) Forest else WarmWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // City Selector Button
                        Surface(
                            color = Color(0x22FFFFFF),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { showCityDialog = true }
                                .testTag("btn_select_city")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = GoldLight,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = selectedCity.name,
                                    color = WarmWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                // GPS Status Banner
                if (gpsState.isGpsActive || gpsState.errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = if (gpsState.errorMessage != null) Color(0x33EF4444) else Color(0x22D4AF37),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (gpsState.errorMessage != null) "⚠️ ${gpsState.errorMessage}"
                            else "🛰️ GPS Presisi: ${selectedCity.province} (Akurasi: ±${gpsState.accuracyMeters.toInt()}m)",
                            color = if (gpsState.errorMessage != null) Color(0xFFFCA5A5) else GoldLight,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // Active Audio Alert / Playing Banner
        if (alertState.isAlertActive) {
            Surface(
                color = if (alertState.errorMessage != null) Color(0xFF7F1D1D) else Color(0xFF0F4335),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, if (alertState.errorMessage != null) Color(0xFFEF4444) else Gold),
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (alertState.errorMessage != null) Color(0xFFEF4444) else Gold)
                    ) {
                        if (alertState.isLoading) {
                            CircularProgressIndicator(
                                color = Forest,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(18.dp)
                            )
                        } else {
                            Icon(
                                imageVector = if (alertState.errorMessage != null) Icons.Default.Warning else Icons.Default.VolumeUp,
                                contentDescription = null,
                                tint = if (alertState.errorMessage != null) WarmWhite else Forest,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = alertState.title,
                            color = WarmWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (alertState.errorMessage != null) alertState.errorMessage!!
                            else if (alertState.isLoading) "Menghubungkan audio streaming..."
                            else "Sedang berbunyi • Waktu ${alertState.time}",
                            color = if (alertState.errorMessage != null) Color(0xFFFCA5A5) else GoldLight,
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = { viewModel.dismissPrayerAlert() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDC2626),
                            contentColor = WarmWhite
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("Hentikan", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Next Prayer Hero Card
            item {
                Surface(
                    color = Emerald,
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Gold),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Emerald, Forest)
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Menuju Waktu Salat",
                                    color = GoldLight,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = schedule.dateFormatted,
                                    color = Color(0xFFC4D5CE),
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column {
                                    Text(
                                        text = schedule.nextPrayerName,
                                        color = WarmWhite,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = schedule.nextPrayerTime,
                                        color = Gold,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }

                                // Quick Adzan Audio Test Button
                                Button(
                                    onClick = {
                                        viewModel.audioPlayer.playAudio(
                                            url = "https://cdn.aladhan.com/audio/adhans/makkah.mp3",
                                            title = "Kumandang Azan Makkah",
                                            subtitle = "Uji Pemicu Audio Azan"
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Gold,
                                        contentColor = Forest
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Uji Azan", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Surface(
                                color = Color(0x33000000),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "⏳ ${schedule.countdownText}",
                                    color = GoldLight,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Qibla Direction Shortcut
            item {
                Surface(
                    color = SurfaceDark,
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { viewModel.navigateTo(AppScreen.QiblaCompass) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0x33D4AF37))
                        ) {
                            Icon(Icons.Default.CompassCalibration, contentDescription = null, tint = Gold)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Arah Kiblat dari ${selectedCity.name}", color = WarmWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("${String.format("%.1f", schedule.qiblaAngle)}° dari Arah Utara", color = GoldLight, fontSize = 12.sp)
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Gold)
                    }
                }
            }

            // Full Prayer List items
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Jadwal Lengkap Hari Ini",
                    color = WarmWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            val prayerList = listOf(
                PrayerRowItem("Imsak", schedule.imsak, "Waktu berhenti sahur", Icons.Default.Nightlight),
                PrayerRowItem("Subuh", schedule.subuh, "Shalat Fardhu 2 Rakaat", Icons.Default.WbTwilight),
                PrayerRowItem("Terbit", schedule.terbit, "Matahari terbit (Syuruq)", Icons.Default.WbSunny),
                PrayerRowItem("Dhuha", schedule.dhuha, "Shalat Sunnah Dhuha", Icons.Default.Brightness5),
                PrayerRowItem("Dzuhur", schedule.dzuhur, "Shalat Fardhu 4 Rakaat", Icons.Default.Brightness7),
                PrayerRowItem("Ashar", schedule.ashar, "Shalat Fardhu 4 Rakaat", Icons.Default.WbCloudy),
                PrayerRowItem("Maghrib", schedule.maghrib, "Shalat Fardhu 3 Rakaat", Icons.Default.WbTwilight),
                PrayerRowItem("Isya", schedule.isya, "Shalat Fardhu 4 Rakaat", Icons.Default.Bedtime)
            )

            items(prayerList) { p ->
                val isNext = p.name.equals(schedule.nextPrayerName, ignoreCase = true)
                Surface(
                    color = if (isNext) Color(0xFF0F4335) else SurfaceDark,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isNext) Gold else BorderGoldMuted),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isNext) Gold else Color(0x22D4AF37))
                        ) {
                            Icon(
                                imageVector = p.icon,
                                contentDescription = null,
                                tint = if (isNext) Forest else Gold,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = p.name,
                                color = WarmWhite,
                                fontSize = 15.sp,
                                fontWeight = if (isNext) FontWeight.Bold else FontWeight.Medium
                            )
                            Text(
                                text = p.desc,
                                color = Color(0xFFA7C2B7),
                                fontSize = 11.sp
                            )
                        }

                        Text(
                            text = p.time,
                            color = if (isNext) Gold else WarmWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // ==========================================
            // UJI NYATA 4 AUDIO SESUAI WAKTU DARI API
            // ==========================================
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Uji Nyata Audio Pengingat (4 Sumber Audio)",
                    color = WarmWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Putar dan uji langsung audio azan, imsak, dan ayam berkokok",
                    color = Color(0xFFA7C2B7),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Audio Test Items
            val audioTests = listOf(
                AudioTestItem(
                    id = "SUBUH",
                    title = "Adzan Subuh",
                    desc = "Khusus Subuh (Assalatu Khairum Minan Naum)",
                    url = "https://wflq2j4mi1smqyku.public.blob.vercel-storage.com/adzan_subuh.mp3",
                    icon = Icons.Default.WbTwilight
                ),
                AudioTestItem(
                    id = "REGULER",
                    title = "Adzan Reguler (Dzuhur, Ashar, Maghrib, Isya)",
                    desc = "Audio adzan fardhu 4 waktu",
                    url = "https://wflq2j4mi1smqyku.public.blob.vercel-storage.com/adzan.mp3",
                    icon = Icons.Default.Brightness7
                ),
                AudioTestItem(
                    id = "IMSAK",
                    title = "Peringatan Waktu Imsak",
                    desc = "Sirine/audio peringatan 10 menit sebelum Subuh",
                    url = "https://wflq2j4mi1smqyku.public.blob.vercel-storage.com/imsak.mp3",
                    icon = Icons.Default.Nightlight
                ),
                AudioTestItem(
                    id = "TERBIT",
                    title = "Waktu Terbit (Ayam Berkokok)",
                    desc = "Audio penanda matahari terbit (Syuruq)",
                    url = "https://wflq2j4mi1smqyku.public.blob.vercel-storage.com/ayam.mp3",
                    icon = Icons.Default.WbSunny
                )
            )

            items(audioTests) { testItem ->
                val isCurrentTesting = alertState.isAlertActive && alertState.isTestMode && alertState.activeTestType == testItem.id
                val isPlaying = isCurrentTesting && alertState.isPlaying
                val isLoading = isCurrentTesting && alertState.isLoading

                Surface(
                    color = if (isPlaying) Color(0xFF0D4A3A) else SurfaceDark,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isPlaying) Gold else BorderGoldMuted),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (isPlaying) Gold else Color(0x22D4AF37))
                        ) {
                            Icon(
                                imageVector = testItem.icon,
                                contentDescription = null,
                                tint = if (isPlaying) Forest else Gold,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = testItem.title,
                                color = WarmWhite,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = testItem.desc,
                                color = Color(0xFFA7C2B7),
                                fontSize = 11.sp
                            )
                        }

                        // Play / Stop Test Button
                        Button(
                            onClick = { viewModel.testPrayerAudio(testItem.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isPlaying) Color(0xFFEF4444) else Gold,
                                contentColor = if (isPlaying) WarmWhite else Forest
                            ),
                            shape = RoundedCornerShape(14.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Forest,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(16.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isPlaying) "Hentikan" else "Uji Audio",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // ==========================================
            // PENGATURAN SUARA AZAN & NOTIFIKASI SALAT
            // ==========================================
            item {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Pengaturan Pengingat & Suara Azan",
                    color = WarmWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = SurfaceDark,
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Master Switch
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Aktifkan Pengingat Salat",
                                    color = WarmWhite,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Pemicu otomatis tepat waktu (maks 1x per hari)",
                                    color = Color(0xFFA7C2B7),
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = isAudioEnabled,
                                onCheckedChange = { viewModel.setPrayerAudioEnabled(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Forest,
                                    checkedTrackColor = Gold
                                )
                            )
                        }

                        if (isAudioEnabled) {
                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = Color(0x22FFFFFF))
                            Spacer(modifier = Modifier.height(12.dp))

                            // Reminder Type
                            Text(
                                text = "Jenis Pengingat:",
                                color = Gold,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            val reminderOptions = listOf(
                                "AUDIO_FULL" to "Suara Azan/Audio Penuh",
                                "VIBRATE_ONLY" to "Hanya Getaran Perangkat",
                                "OFF" to "Nonaktifkan Semua Suara"
                            )

                            reminderOptions.forEach { (key, label) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { viewModel.setPrayerReminderType(key) }
                                        .padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = reminderType == key,
                                        onClick = { viewModel.setPrayerReminderType(key) },
                                        colors = RadioButtonDefaults.colors(selectedColor = Gold)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = label,
                                        color = WarmWhite,
                                        fontSize = 13.sp
                                    )
                                }
                            }

                            if (reminderType == "AUDIO_FULL") {
                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(color = Color(0x22FFFFFF))
                                Spacer(modifier = Modifier.height(12.dp))

                                // Volume Slider
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Volume Suara Azan",
                                        color = WarmWhite,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "${(audioVolume * 100).toInt()}%",
                                        color = Gold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                                Slider(
                                    value = audioVolume,
                                    onValueChange = { viewModel.setPrayerAudioVolume(it) },
                                    valueRange = 0.1f..1.0f,
                                    colors = SliderDefaults.colors(
                                        thumbColor = Gold,
                                        activeTrackColor = Gold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color(0x22FFFFFF))
                        Spacer(modifier = Modifier.height(10.dp))

                        // Realistic platform note
                        Text(
                            text = "ℹ️ Catatan Sistem: Audio akan berbunyi saat aplikasi aktif di layar. Notifikasi sistem perangkat akan dikirimkan untuk mengingatkan masuknya waktu salat.",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }

    // City Selection Dialog
    if (showCityDialog) {
        AlertDialog(
            onDismissRequest = { showCityDialog = false },
            title = {
                Text("Pilih Kota / Wilayah", color = Gold, fontWeight = FontWeight.Bold)
            },
            text = {
                LazyColumn(modifier = Modifier.heightIn(max = 350.dp)) {
                    items(PrayerTimesCalculator.cities) { city ->
                        val isSelected = city.name == selectedCity.name
                        Surface(
                            color = if (isSelected) Color(0x33D4AF37) else Color.Transparent,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    viewModel.selectCity(city)
                                    showCityDialog = false
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = city.name,
                                        color = if (isSelected) Gold else WarmWhite,
                                        fontSize = 14.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                    Text(city.province, color = Color(0xFFA7C2B7), fontSize = 11.sp)
                                }
                                if (isSelected) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Gold)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCityDialog = false }) {
                    Text("Tutup", color = Gold, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Forest,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

data class PrayerRowItem(
    val name: String,
    val time: String,
    val desc: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

data class AudioTestItem(
    val id: String,
    val title: String,
    val desc: String,
    val url: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
