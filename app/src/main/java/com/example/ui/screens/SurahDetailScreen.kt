package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Ayat
import com.example.data.model.Bookmark
import com.example.data.model.AvailableReciters
import com.example.ui.YannQuranViewModel
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Emerald
import com.example.ui.theme.Forest
import com.example.ui.theme.ForestDark
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldAmber
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceDarkCard
import com.example.ui.theme.WarmWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahDetailScreen(
    surahNomor: Int,
    surahName: String,
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val detail by viewModel.currentDetail.collectAsState()
    val isLoading by viewModel.detailLoading.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val lastRead by viewModel.lastRead.collectAsState()
    val arabicFontSize by viewModel.arabicFontSize.collectAsState()
    val showLatin by viewModel.showLatin.collectAsState()
    val showTranslation by viewModel.showTranslation.collectAsState()
    val selectedReciterKey by viewModel.selectedReciterKey.collectAsState()

    var showSettingsDialog by remember { mutableStateOf(false) }

    BackHandler {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Surat $surahName",
                            color = WarmWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        detail?.let {
                            Text(
                                text = "${it.tempatTurun} • ${it.jumlahAyat} Ayat",
                                color = GoldLight,
                                fontSize = 12.sp
                            )
                        }
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
                actions = {
                    // Full Murottal play button
                    IconButton(onClick = {
                        detail?.let { d ->
                            viewModel.playSurahAudio(
                                com.example.data.model.Surah(
                                    nomor = d.nomor,
                                    nama = d.nama,
                                    namaLatin = d.namaLatin,
                                    jumlahAyat = d.jumlahAyat,
                                    tempatTurun = d.tempatTurun,
                                    arti = d.arti,
                                    deskripsi = d.deskripsi,
                                    audioFull = d.audioFull
                                )
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Putar Audio Penuh",
                            tint = Gold
                        )
                    }

                    // Tafsir Button
                    IconButton(onClick = {
                        viewModel.openTafsir(surahNomor, surahName)
                    }) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "Tafsir Surat",
                            tint = GoldLight
                        )
                    }

                    // Settings Button
                    IconButton(onClick = { showSettingsDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = "Pengaturan Font",
                            tint = GoldLight
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
        modifier = modifier.testTag("surah_detail_screen")
    ) { innerPadding ->
        if (isLoading && detail == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator(color = Gold)
            }
        } else {
            val d = detail
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    bottom = 90.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Header Banner
                item {
                    SurahHeaderCard(detail = d, surahNomor = surahNomor, surahName = surahName)
                }

                // Bismillah Banner (Omit for Surah 9 At-Taubah)
                if (surahNomor != 9) {
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
                                    .padding(vertical = 14.dp)
                            )
                        }
                    }
                }

                // Ayat List
                val verses = d?.ayat ?: emptyList()
                items(verses, key = { it.nomorAyat }) { ayat ->
                    val isBookmarked = viewModel.isBookmarked(surahNomor, ayat.nomorAyat)
                    val isLastRead = lastRead.surahNomor == surahNomor && lastRead.ayatNomor == ayat.nomorAyat

                    AyatCard(
                        ayat = ayat,
                        surahNomor = surahNomor,
                        surahName = surahName,
                        arabicFontSize = arabicFontSize,
                        showLatin = showLatin,
                        showTranslation = showTranslation,
                        isBookmarked = isBookmarked,
                        isLastRead = isLastRead,
                        onPlayAudio = {
                            viewModel.playAyatAudio(surahName, ayat.nomorAyat, ayat.audio)
                        },
                        onToggleBookmark = {
                            viewModel.toggleBookmark(
                                Bookmark(
                                    surahNomor = surahNomor,
                                    surahNamaLatin = surahName,
                                    ayatNomor = ayat.nomorAyat,
                                    teksArab = ayat.teksArab,
                                    teksIndonesia = ayat.teksIndonesia
                                )
                            )
                            Toast.makeText(
                                context,
                                if (isBookmarked) "Penanda dihapus" else "Ayat ditandai",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onMarkLastRead = {
                            viewModel.markLastRead(surahNomor, surahName, ayat.nomorAyat)
                            Toast.makeText(context, "Posisi baca terakhir disimpan", Toast.LENGTH_SHORT).show()
                        },
                        onCopy = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Ayat Al-Qur'an", "${ayat.teksArab}\n\n${ayat.teksIndonesia}\n(QS. $surahName: ${ayat.nomorAyat})")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Teks ayat berhasil disalin", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

    // Font & Display Settings Dialog
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = {
                Text("Pengaturan Tampilan & Qari", color = Gold, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Font Size Slider
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ukuran Tulisan Arab", color = WarmWhite, fontSize = 14.sp)
                            Text("${arabicFontSize.toInt()} sp", color = Gold, fontWeight = FontWeight.Bold)
                        }
                        Slider(
                            value = arabicFontSize,
                            onValueChange = { viewModel.setArabicFontSize(it) },
                            valueRange = 20f..38f,
                            colors = SliderDefaults.colors(
                                thumbColor = Gold,
                                activeTrackColor = Gold
                            )
                        )
                    }

                    // Transliteration Toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tampilkan Teks Latin", color = WarmWhite, fontSize = 14.sp)
                        Switch(
                            checked = showLatin,
                            onCheckedChange = { viewModel.setShowLatin(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Forest,
                                checkedTrackColor = Gold
                            )
                        )
                    }

                    // Translation Toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tampilkan Terjemahan", color = WarmWhite, fontSize = 14.sp)
                        Switch(
                            checked = showTranslation,
                            onCheckedChange = { viewModel.setShowTranslation(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Forest,
                                checkedTrackColor = Gold
                            )
                        )
                    }

                    // Qari Selection
                    Text("Pilihan Qari Murottal", color = Gold, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    AvailableReciters.forEach { reciter ->
                        val isSelected = selectedReciterKey == reciter.key
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { viewModel.setSelectedReciterKey(reciter.key) }
                                .padding(vertical = 6.dp, horizontal = 4.dp)
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { viewModel.setSelectedReciterKey(reciter.key) },
                                colors = RadioButtonDefaults.colors(selectedColor = Gold)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(reciter.name, color = WarmWhite, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                Text(reciter.origin, color = Color(0xFFA7C2B7), fontSize = 11.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("Selesai", color = Gold, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Forest,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun SurahHeaderCard(
    detail: com.example.data.model.SurahDetail?,
    surahNomor: Int,
    surahName: String
) {
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = detail?.nama ?: "القرآن",
                    color = Gold,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Surat $surahName",
                    color = WarmWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                detail?.let {
                    Text(
                        text = "(${it.arti})",
                        color = GoldLight,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${it.tempatTurun} • ${it.jumlahAyat} Ayat",
                        color = Color(0xFFC4D5CE),
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AyatCard(
    ayat: Ayat,
    surahNomor: Int,
    surahName: String,
    arabicFontSize: Float,
    showLatin: Boolean,
    showTranslation: Boolean,
    isBookmarked: Boolean,
    isLastRead: Boolean,
    onPlayAudio: () -> Unit,
    onToggleBookmark: () -> Unit,
    onMarkLastRead: () -> Unit,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (isLastRead) Color(0xFF0A3C2F) else SurfaceDark,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isLastRead) Gold else BorderGoldMuted),
        modifier = modifier
            .fillMaxWidth()
            .testTag("ayat_card_${ayat.nomorAyat}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Verse Toolbar Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Verse badge
                Surface(
                    color = Color(0x33D4AF37),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Gold)
                ) {
                    Text(
                        text = "$surahNomor : ${ayat.nomorAyat}",
                        color = Gold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Actions row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Play Verse Audio
                    IconButton(onClick = onPlayAudio, modifier = Modifier.size(34.dp)) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Putar Ayat", tint = GoldLight)
                    }

                    // Bookmark
                    IconButton(onClick = onToggleBookmark, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Simpan Ayat",
                            tint = if (isBookmarked) Gold else Color(0xFFA7C2B7)
                        )
                    }

                    // Mark Last Read
                    IconButton(onClick = onMarkLastRead, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = if (isLastRead) Icons.Default.CheckCircle else Icons.Default.Done,
                            contentDescription = "Tandai Terakhir Dibaca",
                            tint = if (isLastRead) Gold else Color(0xFFA7C2B7)
                        )
                    }

                    // Copy
                    IconButton(onClick = onCopy, modifier = Modifier.size(34.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Salin Teks", tint = Color(0xFFA7C2B7))
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Arabic text (RTL, large, high-legibility)
            Text(
                text = ayat.teksArab,
                color = WarmWhite,
                fontSize = arabicFontSize.sp,
                lineHeight = (arabicFontSize * 1.7f).sp,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )

            // Latin transliteration
            if (showLatin && ayat.teksLatin.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = ayat.teksLatin,
                    color = GoldLight,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }

            // Translation in Indonesian
            if (showTranslation && ayat.teksIndonesia.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = ayat.teksIndonesia,
                    color = Color(0xFFC4D5CE),
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
