package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Forest
import com.example.ui.theme.Gold
import com.example.ui.theme.WarmWhite

data class NavTab(
    val screen: AppScreen,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun AppBottomNavBar(
    currentScreen: AppScreen,
    onTabSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        NavTab(AppScreen.Home, "Beranda", Icons.Filled.Home, Icons.Outlined.Home),
        NavTab(AppScreen.SurahList, "Al-Qur'an", Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
        NavTab(AppScreen.PrayerTimes, "Salat", Icons.Filled.AccessTime, Icons.Outlined.AccessTime),
        NavTab(AppScreen.YasinTahlil, "Yasin", Icons.Filled.AutoStories, Icons.Outlined.AutoStories),
        NavTab(AppScreen.DoaDzikir, "Doa", Icons.Filled.VolunteerActivism, Icons.Outlined.VolunteerActivism)
    )

    // Modern Floating Navbar Container
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .testTag("app_floating_nav_bar")
    ) {
        Surface(
            color = Forest,
            shape = RoundedCornerShape(26.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
            shadowElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
        ) {
            NavigationBar(
                containerColor = Color.Transparent,
                contentColor = WarmWhite,
                tonalElevation = 0.dp,
                windowInsets = WindowInsets(0, 0, 0, 0),
                modifier = Modifier.height(64.dp)
            ) {
                tabs.forEach { tab ->
                    val isSelected = when (currentScreen) {
                        is AppScreen.Home -> tab.screen is AppScreen.Home
                        is AppScreen.SurahList, is AppScreen.SurahDetailScreen, is AppScreen.TafsirScreen -> tab.screen is AppScreen.SurahList
                        is AppScreen.PrayerTimes, is AppScreen.QiblaCompass -> tab.screen is AppScreen.PrayerTimes
                        is AppScreen.YasinTahlil -> tab.screen is AppScreen.YasinTahlil
                        is AppScreen.DoaDzikir -> tab.screen is AppScreen.DoaDzikir
                        else -> false
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onTabSelected(tab.screen) },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 11.sp,
                                maxLines = 1
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Forest,
                            selectedTextColor = Gold,
                            indicatorColor = Gold,
                            unselectedIconColor = Color(0xFFA7C2B7),
                            unselectedTextColor = Color(0xFFA7C2B7)
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.title.lowercase()}")
                    )
                }
            }
        }
    }
}
