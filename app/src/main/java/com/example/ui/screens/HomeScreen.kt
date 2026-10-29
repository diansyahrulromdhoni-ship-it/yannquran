package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.OfflineQuranData
import com.example.ui.AppScreen
import com.example.ui.YannQuranViewModel
import com.example.ui.theme.AyahBadgeBg
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Emerald
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.Forest
import com.example.ui.theme.ForestDark
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldAmber
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceDarkCard
import com.example.ui.theme.WarmWhite

@Composable
fun HomeScreen(
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    val lastRead by viewModel.lastRead.collectAsState()
    val schedule by viewModel.prayerSchedule.collectAsState()
    val isDesktopMode by viewModel.isDesktopMode.collectAsState()
    val gpsState by viewModel.gpsState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ForestDark)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Brand Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Emerald, Forest)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "Yann Quran",
                                color = Gold,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Your Daily Companion with the Holy Quran",
                                color = GoldLight,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Desktop Mode Toggle Button
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(if (isDesktopMode) Color(0x44D4AF37) else Color(0x33000000))
                                    .border(1.dp, if (isDesktopMode) Gold else BorderGoldMuted, CircleShape)
                                    .clickable { viewModel.toggleDesktopMode() }
                                    .testTag("btn_toggle_desktop")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Laptop,
                                    contentDescription = "Mode Desktop",
                                    tint = if (isDesktopMode) Gold else Color(0xFFA7C2B7),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Bookmark icon button
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x33000000))
                                    .border(1.dp, BorderGoldMuted, CircleShape)
                                    .clickable { viewModel.navigateTo(AppScreen.Bookmarks) }
                                    .testTag("btn_bookmarks")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "Daftar Penanda",
                                    tint = Gold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tagline badge
                    Surface(
                        color = Color(0x2BD4AF37),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted)
                    ) {
                        Text(
                            text = "✨ Baca. Pahami. Amalkan.",
                            color = WarmWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Last Read Card
                    Surface(
                        color = SurfaceDarkCard,
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.openSurahDetail(lastRead.surahNomor, lastRead.surahNamaLatin)
                            }
                            .testTag("last_read_card")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Emerald)
                                    .border(1.dp, Gold, RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoStories,
                                    contentDescription = null,
                                    tint = Gold,
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Terakhir Dibaca",
                                    color = GoldLight,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Surat ${lastRead.surahNamaLatin}",
                                    color = WarmWhite,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Ayat ${lastRead.ayatNomor}",
                                    color = Color(0xFFA7C2B7),
                                    fontSize = 13.sp
                                )
                            }

                            Button(
                                onClick = {
                                    viewModel.openSurahDetail(lastRead.surahNomor, lastRead.surahNamaLatin)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Gold,
                                    contentColor = Forest
                                ),
                                shape = RoundedCornerShape(20.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text("Lanjut", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        // Prayer Schedule Compact Bar
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { viewModel.navigateTo(AppScreen.PrayerTimes) }
                    .testTag("home_prayer_card")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(0x33D4AF37))
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = Gold,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${schedule.cityName} • ",
                                color = GoldLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = schedule.hijriFormatted,
                                color = Color(0xFFA7C2B7),
                                fontSize = 11.sp
                            )
                        }
                        Text(
                            text = schedule.countdownText,
                            color = WarmWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Lihat Jadwal",
                        tint = Gold
                    )
                }
            }
        }

        // Quick Navigation Tiles Header
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Fitur Utama",
                color = WarmWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // 6 Action Tiles (2 columns)
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FeatureTile(
                        title = "Al-Qur'an 30 Juz",
                        subtitle = "Teks, Terjemah & Audio",
                        icon = Icons.Default.MenuBook,
                        badge = "114 Surat",
                        onClick = { viewModel.navigateTo(AppScreen.SurahList) },
                        modifier = Modifier.weight(1f)
                    )
                    FeatureTile(
                        title = "Jadwal Salat",
                        subtitle = "Waktu Subuh s.d Isya",
                        icon = Icons.Default.AccessTime,
                        badge = "Presisi",
                        onClick = { viewModel.navigateTo(AppScreen.PrayerTimes) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FeatureTile(
                        title = "Kompas Kiblat",
                        subtitle = "Arah Ka'bah Makkah",
                        icon = Icons.Default.CompassCalibration,
                        badge = "Sensor",
                        onClick = { viewModel.navigateTo(AppScreen.QiblaCompass) },
                        modifier = Modifier.weight(1f)
                    )
                    FeatureTile(
                        title = "Yasin & Tahlil",
                        subtitle = "Doa Arwah Lengkap",
                        icon = Icons.Default.AutoStories,
                        badge = "Lengkap",
                        onClick = { viewModel.navigateTo(AppScreen.YasinTahlil) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FeatureTile(
                        title = "Doa & Dzikir",
                        subtitle = "Pagi Petang & Harian",
                        icon = Icons.Default.VolunteerActivism,
                        badge = "Sunnah",
                        onClick = { viewModel.navigateTo(AppScreen.DoaDzikir) },
                        modifier = Modifier.weight(1f)
                    )
                    FeatureTile(
                        title = "Tasbih Digital",
                        subtitle = "Hitung Wirid & Haptic",
                        icon = Icons.Default.Fingerprint,
                        badge = "Interaktif",
                        onClick = { viewModel.navigateTo(AppScreen.DoaDzikir) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Ayat of the Day (Ayat Kursi Highlight)
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Ayat Hari Ini",
                color = WarmWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            val ayat = OfflineQuranData.ayatKursi
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            color = Color(0x33D4AF37),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "QS. Al-Baqarah : 255 (Ayat Kursi)",
                                color = Gold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.playAyatAudio("Al-Baqarah", 255, ayat.audio)
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Dengarkan Audio",
                                tint = Gold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = ayat.teksArab,
                        color = WarmWhite,
                        fontSize = 22.sp,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = ayat.teksIndonesia,
                        color = Color(0xFFC4D5CE),
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }
            }
        }

        // App Footer with 3 Partner Logos (YKPM, BBPVP Bekasi, Computindo)
        item {
            Spacer(modifier = Modifier.height(28.dp))
            com.example.ui.components.AppFooter(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun FeatureTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    badge: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceDark,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("tile_${title.lowercase().replace(" ", "_")}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Emerald)
                        .border(1.dp, Color(0x55D4AF37), RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Gold,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Surface(
                    color = Color(0x22D4AF37),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = badge,
                        color = GoldLight,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                color = WarmWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = Color(0xFFA7C2B7),
                fontSize = 11.sp,
                maxLines = 1
            )
        }
    }
}
