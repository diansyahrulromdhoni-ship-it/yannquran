package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.service.audio.AudioPlayerManager
import com.example.ui.theme.Emerald
import com.example.ui.theme.Forest
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldLight
import com.example.ui.theme.WarmWhite

@Composable
fun BottomAudioPlayerBar(
    audioPlayer: AudioPlayerManager,
    modifier: Modifier = Modifier
) {
    val state by audioPlayer.playerState.collectAsState()

    AnimatedVisibility(
        visible = state.currentUrl.isNotBlank(),
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Surface(
            color = Forest,
            tonalElevation = 8.dp,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("audio_player_bar")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                // Linear Progress bar
                val progress = if (state.durationMs > 0) {
                    (state.currentPositionMs.toFloat() / state.durationMs.toFloat()).coerceIn(0f, 1f)
                } else 0f

                LinearProgressIndicator(
                    progress = { progress },
                    color = Gold,
                    trackColor = Color(0x33D4AF37),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Play/Pause Button
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Gold)
                            .clickable { audioPlayer.togglePlayPause() }
                            .testTag("audio_play_pause_button")
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = Forest,
                                strokeWidth = 2.5.dp,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Icon(
                                imageVector = if (state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (state.isPlaying) "Jeda" else "Putar",
                                tint = Forest,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Title & Reciter details
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = state.title.ifEmpty { "Murottal Al-Qur'an" },
                            color = WarmWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (state.error != null) state.error!! else state.subtitle.ifEmpty { "Memutar audio..." },
                            color = if (state.error != null) Color(0xFFF87171) else GoldLight,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Duration display
                    val posSec = state.currentPositionMs / 1000
                    val durSec = state.durationMs / 1000
                    Text(
                        text = String.format("%02d:%02d", posSec / 60, posSec % 60) +
                                if (durSec > 0) String.format(" / %02d:%02d", durSec / 60, durSec % 60) else "",
                        color = Color(0xFFC4D5CE),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // Close Button
                    IconButton(
                        onClick = { audioPlayer.stop() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup Pemutar",
                            tint = Color(0xFFA7C2B7)
                        )
                    }
                }
            }
        }
    }
}
