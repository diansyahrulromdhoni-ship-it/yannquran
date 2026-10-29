package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LastRead
import com.example.ui.AppScreen
import com.example.ui.theme.Emerald
import com.example.ui.theme.Forest
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.WarmWhite

@Composable
fun FloatingQuickAction(
    lastRead: LastRead,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier.padding(end = 16.dp, bottom = 8.dp)
    ) {
        // Expandable Speed Dial Items
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Bottom),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                // Item 1: Lanjut Baca Tilawah
                FloatingActionItem(
                    label = "Lanjut Baca (QS. ${lastRead.surahNamaLatin} : ${lastRead.ayatNomor})",
                    icon = Icons.Default.AutoStories,
                    onClick = {
                        isExpanded = false
                        onNavigate(AppScreen.SurahDetailScreen(lastRead.surahNomor, lastRead.surahNamaLatin))
                    }
                )

                // Item 2: Kompas Kiblat
                FloatingActionItem(
                    label = "Kompas Arah Kiblat",
                    icon = Icons.Default.CompassCalibration,
                    onClick = {
                        isExpanded = false
                        onNavigate(AppScreen.QiblaCompass)
                    }
                )

                // Item 3: Tasbih Digital
                FloatingActionItem(
                    label = "Tasbih Digital",
                    icon = Icons.Default.Fingerprint,
                    onClick = {
                        isExpanded = false
                        onNavigate(AppScreen.DoaDzikir)
                    }
                )

                // Item 4: Yasin & Tahlil
                FloatingActionItem(
                    label = "Yasin & Tahlil",
                    icon = Icons.Default.MenuBook,
                    onClick = {
                        isExpanded = false
                        onNavigate(AppScreen.YasinTahlil)
                    }
                )
            }
        }

        // Main Floating Trigger Button
        FloatingActionButton(
            onClick = { isExpanded = !isExpanded },
            containerColor = Gold,
            contentColor = Forest,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
            modifier = Modifier
                .size(56.dp)
                .border(2.dp, Forest, CircleShape)
                .testTag("floating_action_button")
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.FlashOn,
                contentDescription = if (isExpanded) "Tutup Menu Cepat" else "Buka Menu Cepat Floating",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun FloatingActionItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Surface(
            color = Forest,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66D4AF37)),
            shadowElevation = 6.dp
        ) {
            Text(
                text = label,
                color = WarmWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Emerald)
                .border(1.5.dp, Gold, CircleShape)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Gold,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
