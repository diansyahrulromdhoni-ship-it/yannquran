package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
fun BookmarksScreen(
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    val bookmarks by viewModel.bookmarks.collectAsState()

    BackHandler {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ayat Tersimpan", color = Gold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
        modifier = modifier.testTag("bookmarks_screen")
    ) { innerPadding ->
        if (bookmarks.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Belum Ada Ayat Tersimpan",
                        color = WarmWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tekan ikon penanda pada ayat saat membaca untuk menyimpannya di sini.",
                        color = Color(0xFFA7C2B7),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = innerPadding.calculateTopPadding() + 14.dp,
                    bottom = 90.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(bookmarks, key = { "${it.surahNomor}_${it.ayatNomor}" }) { bookmark ->
                    Surface(
                        color = SurfaceDark,
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                viewModel.openSurahDetail(bookmark.surahNomor, bookmark.surahNamaLatin)
                            }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
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
                                        text = "Surat ${bookmark.surahNamaLatin} : Ayat ${bookmark.ayatNomor}",
                                        color = Gold,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.toggleBookmark(bookmark) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteOutline,
                                        contentDescription = "Hapus Penanda",
                                        tint = Color(0xFFFCA5A5)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = bookmark.teksArab,
                                color = WarmWhite,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Right,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = bookmark.teksIndonesia,
                                color = Color(0xFFC4D5CE),
                                fontSize = 12.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}
