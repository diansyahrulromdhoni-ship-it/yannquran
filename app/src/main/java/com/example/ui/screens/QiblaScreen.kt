package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.YannQuranViewModel
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Emerald
import com.example.ui.theme.Forest
import com.example.ui.theme.ForestDark
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.WarmWhite
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QiblaScreen(
    viewModel: YannQuranViewModel,
    modifier: Modifier = Modifier
) {
    val azimuth by viewModel.compassAzimuth.collectAsState()
    val isAligned by viewModel.isQiblaAligned.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    val schedule by viewModel.prayerSchedule.collectAsState()

    val targetQibla = schedule.qiblaAngle
    // Rotation of compass dial relative to phone
    val smoothAzimuth by animateFloatAsState(targetValue = azimuth, label = "azimuth")

    BackHandler {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Kompas Kiblat", color = Gold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
        modifier = modifier.testTag("qibla_screen")
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // City & Qibla Angle Info Card
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Gold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Lokasi: ${selectedCity.name}",
                            color = WarmWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Derajat Ka'bah: ${String.format("%.1f", targetQibla)}° dari Utara",
                            color = GoldLight,
                            fontSize = 12.sp
                        )
                    }

                    Surface(
                        color = if (isAligned) Gold else Color(0x22D4AF37),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (isAligned) "TEPAT" else "${smoothAzimuth.toInt()}°",
                            color = if (isAligned) Forest else WarmWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Compass Dial (Visual Canvas)
            val ringBorderColor by animateColorAsState(
                targetValue = if (isAligned) Gold else BorderGoldMuted,
                label = "ringColor"
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(290.dp)
                    .clip(CircleShape)
                    .background(Forest)
                    .border(3.dp, ringBorderColor, CircleShape)
            ) {
                // Rotating Dial
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(-smoothAzimuth)
                ) {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val radius = size.width / 2f - 20.dp.toPx()

                    // Outer ticks
                    for (i in 0 until 360 step 15) {
                        val angleRad = Math.toRadians(i.toDouble() - 90.0)
                        val isCardinal = i % 90 == 0
                        val tickLength = if (isCardinal) 16.dp.toPx() else 8.dp.toPx()
                        val tickColor = if (isCardinal) Gold else Color(0x55D4AF37)
                        val strokeW = if (isCardinal) 3.dp.toPx() else 1.5.dp.toPx()

                        val start = Offset(
                            x = (center.x + (radius - tickLength) * cos(angleRad)).toFloat(),
                            y = (center.y + (radius - tickLength) * sin(angleRad)).toFloat()
                        )
                        val end = Offset(
                            x = (center.x + radius * cos(angleRad)).toFloat(),
                            y = (center.y + radius * sin(angleRad)).toFloat()
                        )
                        drawLine(color = tickColor, start = start, end = end, strokeWidth = strokeW)
                    }

                    // North Red Needle Marker
                    val northPath = Path().apply {
                        moveTo(center.x, center.y - radius + 10.dp.toPx())
                        lineTo(center.x - 10.dp.toPx(), center.y - 40.dp.toPx())
                        lineTo(center.x + 10.dp.toPx(), center.y - 40.dp.toPx())
                        close()
                    }
                    drawPath(path = northPath, color = Color(0xFFEF4444))

                    // South Needle Marker
                    val southPath = Path().apply {
                        moveTo(center.x, center.y + radius - 10.dp.toPx())
                        lineTo(center.x - 10.dp.toPx(), center.y + 40.dp.toPx())
                        lineTo(center.x + 10.dp.toPx(), center.y + 40.dp.toPx())
                        close()
                    }
                    drawPath(path = southPath, color = Color(0xFFA7C2B7))

                    // Qibla Indicator Line & Ka'bah Star Marker
                    rotate(targetQibla, center) {
                        val qiblaLineStart = Offset(center.x, center.y - 30.dp.toPx())
                        val qiblaLineEnd = Offset(center.x, center.y - radius + 5.dp.toPx())
                        drawLine(
                            color = Gold,
                            start = qiblaLineStart,
                            end = qiblaLineEnd,
                            strokeWidth = 3.dp.toPx()
                        )
                        // Ka'bah indicator circle
                        drawCircle(
                            color = Gold,
                            radius = 12.dp.toPx(),
                            center = Offset(center.x, center.y - radius + 22.dp.toPx())
                        )
                    }

                    // Center Hub
                    drawCircle(color = Gold, radius = 8.dp.toPx(), center = center)
                    drawCircle(color = Forest, radius = 4.dp.toPx(), center = center)
                }

                // Ka'bah Center Symbol
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🕋",
                        fontSize = 32.sp
                    )
                    Text(
                        text = "KIBLAT",
                        color = Gold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Alignment Status Alert
            if (isAligned) {
                Surface(
                    color = Color(0x33D4AF37),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Gold)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Gold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tepat Menghadap Ka'bah!",
                            color = GoldLight,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Text(
                    text = "Putar ponsel hingga jarum emas sejajar dengan simbol Ka'bah di atas",
                    color = Color(0xFFA7C2B7),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calibration Note
            Surface(
                color = SurfaceDark,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "💡 Tips: Letakkan ponsel di permukaan datar dan jauhkan dari benda logam/magnet untuk kalibrasi sensor kompas terbaik.",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
