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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.TahlilData
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

@Composable
fun YasinTahlilScreen(
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Surat Yasin", "Susunan Tahlil", "Doa Arwah")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ForestDark)
            .testTag("yasin_tahlil_screen")
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
                    .padding(top = 14.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Surat Yasin & Tahlil",
                    color = Gold,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Qalb al-Qur'an dan rangkaian tahlil lengkap",
                    color = GoldLight,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Tab Row
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Gold,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 13.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == index) Gold else Color(0xFFA7C2B7)
                                )
                            }
                        )
                    }
                }
            }
        }

        when (selectedTab) {
            0 -> YasinTabContent(viewModel)
            1 -> TahlilTabContent()
            2 -> DoaArwahTabContent()
        }
    }
}

@Composable
fun YasinTabContent(viewModel: YannQuranViewModel) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Keutamaan Surah Yasin Banner
        item {
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "✨ Keutamaan Surat Yasin",
                            color = Gold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )

                        // Play Surah Yasin Audio
                        Button(
                            onClick = {
                                viewModel.audioPlayer.playAudio(
                                    url = "https://equran.nos.wjv-1.neo.id/audio-full/Misyari-Rasyid-Al-Afasi/036.mp3",
                                    title = "Surat Yasin (Full Murottal)",
                                    subtitle = "Misyari Rasyid Al-Afasy"
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Gold,
                                contentColor = Forest
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Putar Audio", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = TahlilData.keutamaanYasin,
                        color = Color(0xFFC4D5CE),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Bismillah Banner
        item {
            Surface(
                color = SurfaceDarkCard,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                    color = Gold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }
        }

        // Yasin Verses
        items(TahlilData.yasinVerses, key = { it.nomorAyat }) { ayat ->
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            color = Color(0x33D4AF37),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Ayat ${ayat.nomorAyat}",
                                color = Gold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.playAyatAudio("Yasin", ayat.nomorAyat, ayat.audio)
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Putar Ayat", tint = Gold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = ayat.teksArab,
                        color = WarmWhite,
                        fontSize = 24.sp,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = ayat.teksLatin,
                        color = GoldLight,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = ayat.teksIndonesia,
                        color = Color(0xFFC4D5CE),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TahlilTabContent() {
    var stepTahlilCount by remember { mutableIntStateOf(0) }

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(TahlilData.tahlilSequence, key = { it.id }) { step ->
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = step.title,
                            color = Gold,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )

                        if (step.repeatCount > 1) {
                            Surface(
                                color = Color(0x33D4AF37),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "${step.repeatCount}x",
                                    color = GoldLight,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    if (step.note.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = step.note,
                            color = Color(0xFFA7C2B7),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Arabic Text
                    Text(
                        text = step.arabic,
                        color = WarmWhite,
                        fontSize = 22.sp,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Latin
                    Text(
                        text = step.latin,
                        color = GoldLight,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Translation
                    Text(
                        text = step.translation,
                        color = Color(0xFFC4D5CE),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    // Interactive counter for Kalimat Tahlil (Step 11)
                    if (step.id == 11) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            color = Color(0x22D4AF37),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "Hitungan Tahlil",
                                        color = GoldLight,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = "$stepTahlilCount / 33x",
                                        color = WarmWhite,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = { stepTahlilCount = 0 },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(Icons.Default.RestartAlt, contentDescription = "Reset", tint = GoldLight)
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = { stepTahlilCount++ },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Gold,
                                            contentColor = Forest
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("+1 Hitung", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DoaArwahTabContent() {
    var deceasedName by remember { mutableStateOf("") }
    val doaStep = TahlilData.tahlilSequence.last()

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Personalization Card
        item {
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Khususkan Doa Untuk Almarhum / Almarhumah",
                        color = Gold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = deceasedName,
                        onValueChange = { deceasedName = it },
                        placeholder = {
                            Text("Tuliskan nama almarhum/ah di sini...", color = Color(0xFFA7C2B7), fontSize = 13.sp)
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Forest,
                            unfocusedContainerColor = Forest,
                            focusedBorderColor = Gold,
                            unfocusedBorderColor = BorderGoldMuted,
                            focusedTextColor = WarmWhite,
                            unfocusedTextColor = WarmWhite
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (deceasedName.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = Color(0x33D4AF37),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Niat: Dikhususkan kepada almarhum/ah $deceasedName bin/binti...",
                                color = GoldLight,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Doa Arwah Text
        item {
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Teks Lengkap Doa Tahlil & Arwah",
                        color = Gold,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = doaStep.arabic,
                        color = WarmWhite,
                        fontSize = 22.sp,
                        lineHeight = 38.sp,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Terjemahan:",
                        color = GoldLight,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = doaStep.translation,
                        color = Color(0xFFC4D5CE),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}
