package com.krimson.kuona.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "kuona_settings")

@Singleton
class KuonaPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val HAPTICS_ENABLED = booleanPreferencesKey("haptics_enabled")
        val MASTER_VOLUME = floatPreferencesKey("master_volume")
        val AMBIENT_VOLUME = floatPreferencesKey("ambient_volume")
        val FX_VOLUME = floatPreferencesKey("fx_volume")
        val CONSENT_ACCEPTED = booleanPreferencesKey("consent_accepted")
        val REDUCED_MOTION = booleanPreferencesKey("reduced_motion")
        val ANOMALY_MODEL_PATH = stringPreferencesKey("anomaly_model_path")
        val LLM_MODEL_PATH = stringPreferencesKey("llm_model_path")
    }

    val soundEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.SOUND_ENABLED] ?: true }
    val hapticsEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.HAPTICS_ENABLED] ?: true }
    val masterVolume: Flow<Float> = context.dataStore.data.map { it[Keys.MASTER_VOLUME] ?: 0.8f }
    val ambientVolume: Flow<Float> = context.dataStore.data.map { it[Keys.AMBIENT_VOLUME] ?: 0.3f }
    val fxVolume: Flow<Float> = context.dataStore.data.map { it[Keys.FX_VOLUME] ?: 1.0f }
    val consentAccepted: Flow<Boolean> = context.dataStore.data.map { it[Keys.CONSENT_ACCEPTED] ?: false }
    val reducedMotion: Flow<Boolean> = context.dataStore.data.map { it[Keys.REDUCED_MOTION] ?: false }
    val anomalyModelPath: Flow<String?> = context.dataStore.data.map { it[Keys.ANOMALY_MODEL_PATH] }
    val llmModelPath: Flow<String?> = context.dataStore.data.map { it[Keys.LLM_MODEL_PATH] }

    suspend fun setSoundEnabled(v: Boolean) { context.dataStore.edit { it[Keys.SOUND_ENABLED] = v } }
    suspend fun setHapticsEnabled(v: Boolean) { context.dataStore.edit { it[Keys.HAPTICS_ENABLED] = v } }
    suspend fun setMasterVolume(v: Float) { context.dataStore.edit { it[Keys.MASTER_VOLUME] = v } }
    suspend fun setAmbientVolume(v: Float) { context.dataStore.edit { it[Keys.AMBIENT_VOLUME] = v } }
    suspend fun setFxVolume(v: Float) { context.dataStore.edit { it[Keys.FX_VOLUME] = v } }
    suspend fun setConsentAccepted(v: Boolean) { context.dataStore.edit { it[Keys.CONSENT_ACCEPTED] = v } }
    suspend fun setReducedMotion(v: Boolean) { context.dataStore.edit { it[Keys.REDUCED_MOTION] = v } }
    suspend fun setAnomalyModelPath(v: String?) { context.dataStore.edit { if (v != null) it[Keys.ANOMALY_MODEL_PATH] = v else it.remove(Keys.ANOMALY_MODEL_PATH) } }
    suspend fun setLlmModelPath(v: String?) { context.dataStore.edit { if (v != null) it[Keys.LLM_MODEL_PATH] = v else it.remove(Keys.LLM_MODEL_PATH) } }
}
