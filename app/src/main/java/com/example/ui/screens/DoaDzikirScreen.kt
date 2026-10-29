package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.DoaDzikirData
import com.example.data.repository.DoaItem
import com.example.ui.YannQuranViewModel
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Emerald
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.Forest
import com.example.ui.theme.ForestDark
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.WarmWhite

@Composable
fun DoaDzikirScreen(
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("Semua") }
    var isTasbihMode by remember { mutableStateOf(false) }

    val tasbihCount by viewModel.tasbihCount.collectAsState()
    val tasbihTarget by viewModel.tasbihTarget.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ForestDark)
            .testTag("doa_dzikir_screen")
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
                    Column {
                        Text(
                            text = if (isTasbihMode) "Tasbih Digital" else "Doa & Dzikir",
                            color = Gold,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isTasbihMode) "Hitung wirid dengan umpan balik getar" else "Wirid harian, pagi-petang, dan doa pilihan",
                            color = GoldLight,
                            fontSize = 12.sp
                        )
                    }

                    // Mode Toggle Button (Tasbih vs Doa)
                    Button(
                        onClick = { isTasbihMode = !isTasbihMode },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isTasbihMode) Forest else Gold,
                            contentColor = if (isTasbihMode) Gold else Forest
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Gold),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fingerprint,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isTasbihMode) "Buka Doa" else "Tasbih",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (!isTasbihMode) {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Categories Bar
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(DoaDzikirData.categories) { cat ->
                            val isSelected = selectedCategory == cat
                            Surface(
                                color = if (isSelected) Gold else Color(0x1AD4AF37),
                                shape = RoundedCornerShape(20.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) Gold else BorderGoldMuted
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable { selectedCategory = cat }
                            ) {
                                Text(
                                    text = cat,
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
        }

        if (isTasbihMode) {
            // Interactive Digital Tasbih Screen
            DigitalTasbihView(
                count = tasbihCount,
                target = tasbihTarget,
                onIncrement = { viewModel.incrementTasbih() },
                onReset = { viewModel.resetTasbih() },
                onSetTarget = { viewModel.setTasbihTarget(it) }
            )
        } else {
            // Doa List
            val filteredDoas = if (selectedCategory == "Semua") {
                DoaDzikirData.doaList
            } else {
                DoaDzikirData.doaList.filter { it.category == selectedCategory }
            }

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredDoas, key = { it.id }) { doa ->
                    DoaCard(
                        doa = doa,
                        onCopy = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Doa", "${doa.title}\n\n${doa.arabic}\n\n${doa.translation}\n(${doa.source})")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Doa disalin ke papan klip", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DigitalTasbihView(
    count: Int,
    target: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    onSetTarget: (Int) -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isPressed) 0.94f else 1f, label = "button_press")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        // Target Presets
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            listOf(33, 99, 1000).forEach { t ->
                val isSelected = target == t
                Surface(
                    color = if (isSelected) Gold else Color(0x22D4AF37),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) Gold else BorderGoldMuted),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSetTarget(t) }
                ) {
                    Text(
                        text = "Target $t",
                        color = if (isSelected) Forest else WarmWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Counter Display Box
        Surface(
            color = SurfaceDark,
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Gold),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "HITUNGAN WIRID",
                    color = GoldLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$count",
                    color = Gold,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Putaran: ${count / target} • Target: $target",
                    color = Color(0xFFA7C2B7),
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Big Touch Button for counting
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(220.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(Emerald)
                .border(6.dp, Gold, CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onIncrement()
                }
                .testTag("btn_tasbih_tap")
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Tekan untuk Menghitung",
                    tint = Gold,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "TEKAN",
                    color = WarmWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Reset Button
        OutlinedButton(
            onClick = onReset,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFCA5A5)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66EF4444)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Mulai Ulang (Reset)", fontSize = 13.sp)
        }
    }
}

@Composable
fun DoaCard(
    doa: DoaItem,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceDark,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = doa.title,
                        color = Gold,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (doa.note.isNotBlank()) {
                        Text(
                            text = doa.note,
                            color = Color(0xFFA7C2B7),
                            fontSize = 11.sp
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (doa.repeat > 1) {
                        Surface(
                            color = Color(0x33D4AF37),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "${doa.repeat}x",
                                color = GoldLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Salin", tint = Color(0xFFA7C2B7))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic text
            Text(
                text = doa.arabic,
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
                text = doa.latin,
                color = GoldLight,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Translation
            Text(
                text = doa.translation,
                color = Color(0xFFC4D5CE),
                fontSize = 12.sp,
                lineHeight = 18.sp
            )

            if (doa.source.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = doa.source,
                    color = Color(0xFF8DA39A),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
