package com.krimson.kuona.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.krimson.kuona.core.model.AudioEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioEngine @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var soundPool: SoundPool? = null
    private var ambientPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    // Sound IDs
    private var sfxBoot = 0
    private var sfxScanSweep = 0
    private var sfxTypeTick = 0
    private var sfxUiBlip = 0
    private var sfxThreatPing = 0
    private var sfxThreatConfirmed = 0
    private var sfxGlitch = 0

    // Volume & settings
    var masterVolume: Float = 0.8f
    var fxVolume: Float = 1.0f
    var ambientVolume: Float = 0.3f
    var soundEnabled: Boolean = true
    var hapticsEnabled: Boolean = true

    private var lastTickTime = 0L
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private val vibrator: Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    fun init() {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(attrs)
            .build()

        soundPool?.let { sp ->
            sfxBoot = sp.load(context, R.raw.boot, 1)
            sfxScanSweep = sp.load(context, R.raw.scan_sweep, 1)
            sfxTypeTick = sp.load(context, R.raw.type_tick, 1)
            sfxUiBlip = sp.load(context, R.raw.ui_blip, 1)
            sfxThreatPing = sp.load(context, R.raw.threat_ping, 1)
            sfxThreatConfirmed = sp.load(context, R.raw.threat_confirmed, 1)
            sfxGlitch = sp.load(context, R.raw.glitch, 1)
        }
    }

    fun handleEvent(event: AudioEvent) {
        if (!soundEnabled) return

        // Respect DND / Silent mode
        if (audioManager.ringerMode != AudioManager.RINGER_MODE_NORMAL) {
            return
        }

        val effVol = masterVolume * fxVolume

        when (event) {
            is AudioEvent.Boot -> {
                soundPool?.play(sfxBoot, effVol, effVol, 1, 0, 1.0f)
            }
            is AudioEvent.ScanSweepStart -> {
                soundPool?.play(sfxScanSweep, effVol, effVol, 1, 0, 1.0f)
            }
            is AudioEvent.TypeTick -> {
                val now = System.currentTimeMillis()
                if (now - lastTickTime >= 40) { // Throttle tick to max 25Hz to avoid stacking
                    lastTickTime = now
                    soundPool?.play(sfxTypeTick, effVol * 0.35f, effVol * 0.35f, 0, 0, 1.0f)
                }
            }
            is AudioEvent.UiBlip -> {
                soundPool?.play(sfxUiBlip, effVol * 0.7f, effVol * 0.7f, 1, 0, 1.0f)
                hapticTick()
            }
            is AudioEvent.ThreatPing -> {
                // Pitch variation based on severity: 1..5 -> 0.85f..1.15f
                val pitch = 0.85f + (event.severity.coerceIn(1, 5) * 0.06f)
                soundPool?.play(sfxThreatPing, effVol, effVol, 2, 0, pitch)
                hapticTick()
            }
            is AudioEvent.ThreatsConfirmed -> {
                soundPool?.play(sfxThreatConfirmed, effVol, effVol, 2, 0, 1.0f)
                hapticStrong()
            }
            is AudioEvent.Glitch -> {
                soundPool?.play(sfxGlitch, effVol * 0.85f, effVol * 0.85f, 1, 0, 1.0f)
            }
            is AudioEvent.AmbientStart -> startAmbient()
            is AudioEvent.AmbientStop -> stopAmbient()
        }
    }

    private fun startAmbient() {
        if (!soundEnabled) return
        if (ambientPlayer == null) {
            try {
                ambientPlayer = MediaPlayer.create(context, R.raw.ambient_hum)?.apply {
                    isLooping = true
                    val vol = masterVolume * ambientVolume
                    setVolume(vol, vol)
                    start()
                }
            } catch (_: Exception) {}
        }
    }

    private fun stopAmbient() {
        ambientPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        ambientPlayer = null
    }

    private fun hapticTick() {
        if (!hapticsEnabled) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(15)
        }
    }

    private fun hapticStrong() {
        if (!hapticsEnabled) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 45, 60, 45), -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(90)
        }
    }

    fun release() {
        stopAmbient()
        soundPool?.release()
        soundPool = null
        scope.cancel()
    }
}
