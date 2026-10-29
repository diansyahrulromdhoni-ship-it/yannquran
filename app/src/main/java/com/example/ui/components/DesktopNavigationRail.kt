package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Emerald
import com.example.ui.theme.Forest
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.WarmWhite

@Composable
fun DesktopNavigationRail(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    isGpsActive: Boolean,
    onGpsClick: () -> Unit,
    onToggleDesktop: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        Triple(AppScreen.Home, "Beranda", Icons.Default.Home),
        Triple(AppScreen.SurahList, "Al-Qur'an", Icons.Default.MenuBook),
        Triple(AppScreen.PrayerTimes, "Jadwal Salat", Icons.Default.AccessTime),
        Triple(AppScreen.QiblaCompass, "Kiblat", Icons.Default.CompassCalibration),
        Triple(AppScreen.YasinTahlil, "Yasin & Tahlil", Icons.Default.AutoStories),
        Triple(AppScreen.DoaDzikir, "Doa & Dzikir", Icons.Default.VolunteerActivism),
        Triple(AppScreen.Bookmarks, "Penanda", Icons.Default.Bookmark)
    )

    NavigationRail(
        containerColor = Forest,
        contentColor = WarmWhite,
        header = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Emerald)
                        .border(1.5.dp, Gold, CircleShape)
                ) {
                    Text(
                        text = "📖",
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Yann Quran",
                    color = Gold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // GPS Indicator Button in Rail
                Surface(
                    color = if (isGpsActive) Color(0x33D4AF37) else Color(0x22FFFFFF),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isGpsActive) Gold else Color(0x33FFFFFF)
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onGpsClick() }
                        .testTag("rail_gps_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.GpsFixed,
                            contentDescription = "GPS",
                            tint = if (isGpsActive) Gold else Color(0xFFA7C2B7),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isGpsActive) "GPS ON" else "GPS",
                            color = if (isGpsActive) GoldLight else Color(0xFFA7C2B7),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        modifier = modifier
            .fillMaxHeight()
            .testTag("desktop_navigation_rail")
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items.forEach { (screenTarget, title, icon) ->
                val isSelected = when (currentScreen) {
                    is AppScreen.Home -> screenTarget is AppScreen.Home
                    is AppScreen.SurahList, is AppScreen.SurahDetailScreen, is AppScreen.TafsirScreen -> screenTarget is AppScreen.SurahList
                    is AppScreen.PrayerTimes -> screenTarget is AppScreen.PrayerTimes
                    is AppScreen.QiblaCompass -> screenTarget is AppScreen.QiblaCompass
                    is AppScreen.YasinTahlil -> screenTarget is AppScreen.YasinTahlil
                    is AppScreen.DoaDzikir -> screenTarget is AppScreen.DoaDzikir
                    is AppScreen.Bookmarks -> screenTarget is AppScreen.Bookmarks
                }

                NavigationRailItem(
                    selected = isSelected,
                    onClick = { onNavigate(screenTarget) },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Forest,
                        selectedTextColor = Gold,
                        indicatorColor = Gold,
                        unselectedIconColor = Color(0xFFA7C2B7),
                        unselectedTextColor = Color(0xFFA7C2B7)
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Switch to Mobile layout button
            IconButton(
                onClick = onToggleDesktop,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = Icons.Default.StayCurrentPortrait,
                    contentDescription = "Tampilan Ponsel",
                    tint = GoldLight
                )
            }
        }
    }
}
