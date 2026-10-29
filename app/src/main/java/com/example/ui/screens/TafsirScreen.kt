package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.YannQuranViewModel
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Forest
import com.example.ui.theme.ForestDark
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.WarmWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TafsirScreen(
    surahNomor: Int,
    surahName: String,
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    val tafsir by viewModel.currentTafsir.collectAsState()
    val isLoading by viewModel.tafsirLoading.collectAsState()

    BackHandler {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Tafsir Kemenag",
                            color = WarmWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Surat $surahName (Nomor $surahNomor)",
                            color = GoldLight,
                            fontSize = 12.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Gold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Forest,
                    titleContentColor = WarmWhite
                )
            )
        },
        containerColor = ForestDark,
        modifier = modifier.testTag("tafsir_screen")
    ) { innerPadding ->
        if (isLoading && tafsir == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator(color = Gold)
            }
        } else {
            val items = tafsir?.tafsir ?: emptyList()
            if (items.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Text(
                        text = "Memuat penjelasan tafsir resmi Kemenag RI...",
                        color = Color(0xFFA7C2B7),
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = innerPadding.calculateTopPadding() + 12.dp,
                        bottom = 90.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items, key = { it.ayat }) { item ->
                        Surface(
                            color = SurfaceDark,
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Surface(
                                    color = Color(0x33D4AF37),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Ayat ${item.ayat}",
                                        color = Gold,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = item.teks,
                                    color = WarmWhite,
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
