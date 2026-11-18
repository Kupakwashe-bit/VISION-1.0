package com.krimson.kuona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.krimson.kuona.core.audio.AudioEngine
import com.krimson.kuona.navigation.Screen
import com.krimson.kuona.ui.theme.KuonaBlack
import com.krimson.kuona.ui.theme.KuonaForeground
import com.krimson.kuona.ui.theme.KuonaTheme
import com.krimson.kuona.ui.theme.KuonaThreatRed
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var audioEngine: AudioEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KuonaTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Boot.route
                ) {
                    composable(Screen.Boot.route) {
                        BootScreen(
                            audioEngine = audioEngine,
                            onBootComplete = {
                                navController.navigate(Screen.Scan.route) {
                                    popUpTo(Screen.Boot.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.Scan.route) {
                        ScanPlaceholderScreen(
                            onNavigateAnalyst = { navController.navigate(Screen.Analyst.route) }
                        )
                    }

                    composable(Screen.Analyst.route) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(KuonaBlack),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("ASK KUONA — ON-DEVICE ANALYST", color = KuonaForeground)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioEngine.release()
    }
}

@Composable
fun ScanPlaceholderScreen(onNavigateAnalyst: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KuonaBlack)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "KUONA HUD",
                color = KuonaForeground,
                fontSize = 28.sp,
                letterSpacing = 4.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "FOUNDATION ONLINE — READY FOR HUD ENGINE",
                color = KuonaThreatRed,
                fontSize = 14.sp,
                letterSpacing = 2.sp
            )
        }
    }
}
