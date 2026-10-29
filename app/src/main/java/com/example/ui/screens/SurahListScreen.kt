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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Surah
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

@Composable
fun SurahListScreen(
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    val surahs by viewModel.surahs.collectAsState()
    val isLoading by viewModel.surahsLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedFilter by viewModel.surahFilter.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()

    val filteredSurahs = surahs.filter { surah ->
        val matchesQuery = if (searchQuery.isBlank()) true else {
            surah.namaLatin.contains(searchQuery, ignoreCase = true) ||
                    surah.arti.contains(searchQuery, ignoreCase = true) ||
                    surah.nomor.toString() == searchQuery.trim()
        }
        val matchesFilter = when (selectedFilter) {
            "Makkiyyah" -> surah.tempatTurun.equals("Mekah", ignoreCase = true)
            "Madaniyyah" -> surah.tempatTurun.equals("Madinah", ignoreCase = true)
            "Tersimpan" -> bookmarks.any { it.surahNomor == surah.nomor }
            else -> true
        }
        matchesQuery && matchesFilter
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ForestDark)
            .testTag("surah_list_screen")
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
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Al-Qur'an 30 Juz",
                        color = Gold,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Surface(
                        color = Color(0x22D4AF37),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${filteredSurahs.size} Surat",
                            color = GoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = {
                        Text("Cari surat (contoh: Yasin, Al-Baqarah, 36...)", color = Color(0xFFA7C2B7), fontSize = 13.sp)
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Cari", tint = Gold)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Hapus", tint = Gold)
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceDark,
                        unfocusedContainerColor = SurfaceDark,
                        focusedBorderColor = Gold,
                        unfocusedBorderColor = BorderGoldMuted,
                        focusedTextColor = WarmWhite,
                        unfocusedTextColor = WarmWhite
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_surah_field")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Filter Chips
                val filterOptions = listOf("Semua", "Makkiyyah", "Madaniyyah", "Tersimpan")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    filterOptions.forEach { filter ->
                        val isSelected = selectedFilter == filter
                        Surface(
                            color = if (isSelected) Gold else Color(0x1AD4AF37),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) Gold else BorderGoldMuted),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { viewModel.setSurahFilter(filter) }
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) Forest else WarmWhite,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Surah List
        if (isLoading && surahs.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = Gold)
            }
        } else if (filteredSurahs.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Tidak ada surat yang cocok",
                        color = WarmWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Silakan coba kata kunci lain",
                        color = Color(0xFFA7C2B7),
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredSurahs, key = { it.nomor }) { surah ->
                    SurahListItem(
                        surah = surah,
                        onSurahClick = {
                            viewModel.openSurahDetail(surah.nomor, surah.namaLatin)
                        },
                        onPlayAudio = {
                            viewModel.playSurahAudio(surah)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SurahListItem(
    surah: Surah,
    onSurahClick: () -> Unit,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceDark,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onSurahClick() }
            .testTag("surah_item_${surah.nomor}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
            // Number badge in gold border
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0x22D4AF37))
                    .border(1.dp, Gold, RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = surah.nomor.toString(),
                    color = Gold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Surah details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = surah.namaLatin,
                    color = WarmWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${surah.tempatTurun} • ${surah.jumlahAyat} Ayat",
                    color = Color(0xFFA7C2B7),
                    fontSize = 12.sp
                )
                Text(
                    text = "Artinya: ${surah.arti}",
                    color = GoldLight,
                    fontSize = 11.sp
                )
            }

            // Arabic calligraphy name
            Text(
                text = surah.nama,
                color = Gold,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            // Play Murottal button
            IconButton(
                onClick = onPlayAudio,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0x22D4AF37))
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Putar Murottal Surat",
                    tint = Gold,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
