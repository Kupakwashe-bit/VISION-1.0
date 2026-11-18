package com.krimson.kuona

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krimson.kuona.core.audio.AudioEngine
import com.krimson.kuona.core.model.AudioEvent
import com.krimson.kuona.ui.theme.KuonaBlack
import com.krimson.kuona.ui.theme.KuonaDim
import com.krimson.kuona.ui.theme.KuonaForeground
import com.krimson.kuona.ui.theme.KuonaThreatRed
import kotlinx.coroutines.delay

@Composable
fun BootScreen(
    audioEngine: AudioEngine,
    onBootComplete: () -> Unit
) {
    val fullText = "KUONA"
    var typedChars by remember { mutableIntStateOf(0) }
    var scanLineY by remember { mutableFloatStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "scanline")
    val sweepProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep"
    )

    LaunchedEffect(Unit) {
        audioEngine.handleEvent(AudioEvent.Boot)
        // Typewriter effect for wordmark
        for (i in 1..fullText.length) {
            delay(120)
            typedChars = i
            audioEngine.handleEvent(AudioEvent.TypeTick)
        }
        delay(1400)
        onBootComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KuonaBlack),
        contentAlignment = Alignment.Center
    ) {
        // Red horizontal scan plane sweeping down
        Canvas(modifier = Modifier.fillMaxSize()) {
            val y = size.height * sweepProgress
            drawLine(
                color = KuonaThreatRed.copy(alpha = 0.85f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 2.5f
            )
            // Soft gradient halo behind line
            drawRect(
                color = KuonaThreatRed.copy(alpha = 0.08f),
                topLeft = Offset(0f, y - 24f),
                size = androidx.compose.ui.geometry.Size(size.width, 48f)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Stylized HUD wireframe box around wordmark
            Box(
                modifier = Modifier
                    .background(KuonaBlack)
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(
                    text = fullText.take(typedChars),
                    color = KuonaForeground,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 8.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "OFFLINE NETWORK SIGHT",
                color = KuonaDim,
                fontSize = 13.sp,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "KRIMSON TECHNOLOGIES",
                color = KuonaDim.copy(alpha = 0.5f),
                fontSize = 11.sp,
                letterSpacing = 2.sp
            )
        }
    }
}
