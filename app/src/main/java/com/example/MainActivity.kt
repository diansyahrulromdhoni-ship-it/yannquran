package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppScreen
import com.example.ui.YannQuranViewModel
import com.example.ui.components.AppBottomNavBar
import com.example.ui.components.BottomAudioPlayerBar
import com.example.ui.components.DesktopNavigationRail
import com.example.ui.components.FloatingQuickAction
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.DoaDzikirScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PrayerTimesScreen
import com.example.ui.screens.QiblaScreen
import com.example.ui.screens.SurahDetailScreen
import com.example.ui.screens.SurahListScreen
import com.example.ui.screens.TafsirScreen
import com.example.ui.screens.YasinTahlilScreen
import com.example.ui.theme.ForestDark
import com.example.ui.theme.YannQuranTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YannQuranTheme(darkTheme = true) {
                YannQuranApp()
            }
        }
    }
}

@Composable
fun YannQuranApp(
    viewModel: YannQuranViewModel = viewModel()
) {
    val currentScreen by viewModel.screen.collectAsState()
    val lastRead by viewModel.lastRead.collectAsState()
    val isDesktopMode by viewModel.isDesktopMode.collectAsState()
    val gpsState by viewModel.gpsState.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useDesktopLayout = maxWidth >= 740.dp || isDesktopMode

        if (useDesktopLayout) {
            // ==========================================
            // DESKTOP & TABLET ADAPTIVE DUAL-PANE LAYOUT
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ForestDark)
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                // Side Navigation Rail on Desktop
                DesktopNavigationRail(
                    currentScreen = currentScreen,
                    onNavigate = { viewModel.navigateToTab(it) },
                    isGpsActive = gpsState.isGpsActive,
                    onGpsClick = { viewModel.requestGpsLocation() },
                    onToggleDesktop = { viewModel.toggleDesktopMode() }
                )

                // Main Content & Persistent Audio Pane
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    // Audio Player Bar on Desktop
                    BottomAudioPlayerBar(audioPlayer = viewModel.audioPlayer)

                    // Desktop Adaptive Content Area
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "desktop_screen_transition"
                        ) { screen ->
                            when (screen) {
                                is AppScreen.Home -> HomeScreen(viewModel = viewModel)

                                is AppScreen.SurahList, is AppScreen.SurahDetailScreen -> {
                                    // Dual-Pane Quran Desktop Mode: Left List | Right Reader
                                    Row(modifier = Modifier.fillMaxSize()) {
                                        Box(
                                            modifier = Modifier
                                                .width(360.dp)
                                                .fillMaxHeight()
                                        ) {
                                            SurahListScreen(viewModel = viewModel)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxHeight()
                                                .background(ForestDark)
                                        ) {
                                            if (screen is AppScreen.SurahDetailScreen) {
                                                SurahDetailScreen(
                                                    surahNomor = screen.surahNomor,
                                                    surahName = screen.surahName,
                                                    viewModel = viewModel
                                                )
                                            } else {
                                                SurahDetailScreen(
                                                    surahNomor = lastRead.surahNomor,
                                                    surahName = lastRead.surahNamaLatin,
                                                    viewModel = viewModel
                                                )
                                            }
                                        }
                                    }
                                }

                                is AppScreen.TafsirScreen -> TafsirScreen(
                                    surahNomor = screen.surahNomor,
                                    surahName = screen.surahName,
                                    viewModel = viewModel
                                )

                                is AppScreen.PrayerTimes, is AppScreen.QiblaCompass -> {
                                    // Dual-Pane Prayer Schedule & Qibla Compass
                                    Row(modifier = Modifier.fillMaxSize()) {
                                        Box(
                                            modifier = Modifier
                                                .weight(1.2f)
                                                .fillMaxHeight()
                                        ) {
                                            PrayerTimesScreen(viewModel = viewModel)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxHeight()
                                        ) {
                                            QiblaScreen(viewModel = viewModel)
                                        }
                                    }
                                }

                                is AppScreen.YasinTahlil -> YasinTahlilScreen(viewModel = viewModel)
                                is AppScreen.DoaDzikir -> DoaDzikirScreen(viewModel = viewModel)
                                is AppScreen.Bookmarks -> BookmarksScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        } else {
            // ==========================================
            // HANDHELD MOBILE LAYOUT (FLOATING NAVBAR + FAB)
            // ==========================================
            val showBottomBar = when (currentScreen) {
                is AppScreen.Home,
                is AppScreen.SurahList,
                is AppScreen.PrayerTimes,
                is AppScreen.YasinTahlil,
                is AppScreen.DoaDzikir -> true
                else -> false
            }

            Scaffold(
                bottomBar = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(WindowInsets.navigationBars)
                    ) {
                        // Persistent bottom audio player
                        BottomAudioPlayerBar(audioPlayer = viewModel.audioPlayer)

                        // Modern Floating Bottom Navigation Bar
                        if (showBottomBar) {
                            AppBottomNavBar(
                                currentScreen = currentScreen,
                                onTabSelected = { viewModel.navigateToTab(it) }
                            )
                        }
                    }
                },
                containerColor = ForestDark,
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = innerPadding.calculateBottomPadding())
                ) {
                    // Mobile Screen Transitions
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "screen_transition"
                    ) { screen ->
                        when (screen) {
                            is AppScreen.Home -> HomeScreen(viewModel = viewModel)
                            is AppScreen.SurahList -> SurahListScreen(viewModel = viewModel)
                            is AppScreen.SurahDetailScreen -> SurahDetailScreen(
                                surahNomor = screen.surahNomor,
                                surahName = screen.surahName,
                                viewModel = viewModel
                            )
                            is AppScreen.TafsirScreen -> TafsirScreen(
                                surahNomor = screen.surahNomor,
                                surahName = screen.surahName,
                                viewModel = viewModel
                            )
                            is AppScreen.PrayerTimes -> PrayerTimesScreen(viewModel = viewModel)
                            is AppScreen.QiblaCompass -> QiblaScreen(viewModel = viewModel)
                            is AppScreen.YasinTahlil -> YasinTahlilScreen(viewModel = viewModel)
                            is AppScreen.DoaDzikir -> DoaDzikirScreen(viewModel = viewModel)
                            is AppScreen.Bookmarks -> BookmarksScreen(viewModel = viewModel)
                        }
                    }

                    // Floating Quick Action Speed Dial Button
                    if (showBottomBar) {
                        FloatingQuickAction(
                            lastRead = lastRead,
                            onNavigate = { viewModel.navigateTo(it) },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 12.dp)
                        )
                    }
                }
            }
        }
    }
}
