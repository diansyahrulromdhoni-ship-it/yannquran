package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BorderGoldMuted
import com.example.ui.theme.Emerald
import com.example.ui.theme.Forest
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldLight
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.WarmWhite

@Composable
fun AppFooter(
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceDark,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGoldMuted),
        modifier = modifier
            .fillMaxWidth()
            .testTag("app_footer_section")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header Title
            Surface(
                color = Color(0x22D4AF37),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x44D4AF37))
            ) {
                Text(
                    text = "KOLABORASI & DUKUNGAN",
                    color = GoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Dipersembahkan Bersama",
                color = WarmWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Three Logos Grid / Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Logo 1: Yayasan Karya Pondok Mandiri
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, Gold, CircleShape)
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_ykpm),
                            contentDescription = "Logo Yayasan Karya Pondok Mandiri",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Yayasan Karya\nPondok Mandiri",
                        color = WarmWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }

                // Logo 2: BBPVP Bekasi
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White)
                            .border(2.dp, Gold, RoundedCornerShape(14.dp))
                            .padding(6.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_bbpvp),
                            contentDescription = "Logo BBPVP Bekasi",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "BBPVP\nBekasi",
                        color = WarmWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }

                // Logo 3: Computindo
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.Black)
                            .border(2.dp, Gold, RoundedCornerShape(14.dp))
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_computindo),
                            contentDescription = "Logo Computindo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Computindo\nTech",
                        color = WarmWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Divider Line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0x33D4AF37))
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Brand Tagline & Copyright
            Text(
                text = "Yann Quran • Your Daily Companion with the Holy Quran",
                color = Gold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Baca. Pahami. Amalkan. Diberdayakan oleh EQuran.id v2 & Kemenag RI",
                color = Color(0xFFA7C2B7),
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
