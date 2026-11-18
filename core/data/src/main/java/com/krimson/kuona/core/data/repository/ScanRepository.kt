package com.krimson.kuona.core.data.repository

import com.krimson.kuona.core.data.dao.*
import com.krimson.kuona.core.data.db.*
import com.krimson.kuona.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScanRepository @Inject constructor(
    private val scanDao: ScanDao,
    private val deviceDao: DeviceDao,
    private val flowDao: FlowDao,
    private val findingDao: FindingDao,
    private val consentLogDao: ConsentLogDao
) {
    suspend fun createScan(mode: ScanMode, networkName: String): Long {
        return scanDao.insert(ScanEntity(startedAt = System.currentTimeMillis(), endedAt = null, mode = mode, networkName = networkName))
    }

    suspend fun completeScan(scanId: Long) {
        scanDao.getById(scanId)?.let { scanDao.update(it.copy(endedAt = System.currentTimeMillis())) }
    }

    fun observeScans(): Flow<List<Scan>> = scanDao.observeAll().map { list -> list.map { it.toDomain() } }

    suspend fun insertDevice(device: Device, scanId: Long): Long {
        return deviceDao.insert(device.toEntity(scanId))
    }

    suspend fun insertDevices(devices: List<Device>, scanId: Long): List<Long> {
        return deviceDao.insertAll(devices.map { it.toEntity(scanId) })
    }

    fun observeDevices(scanId: Long): Flow<List<Device>> = deviceDao.observeByScan(scanId).map { list -> list.map { it.toDomain() } }

    suspend fun getDevices(scanId: Long): List<Device> = deviceDao.getByScan(scanId).map { it.toDomain() }

    suspend fun insertFinding(finding: Finding): Long = findingDao.insert(finding.toEntity())

    fun observeFindings(scanId: Long): Flow<List<Finding>> = findingDao.observeByScan(scanId).map { list -> list.map { it.toDomain() } }

    suspend fun logConsent(text: String) { consentLogDao.insert(ConsentLogEntity(timestamp = System.currentTimeMillis(), text = text)) }

    suspend fun wipeAllData() {
        scanDao.deleteAll()
        findingDao.deleteAll()
        consentLogDao.deleteAll()
    }

    private fun ScanEntity.toDomain() = Scan(id, startedAt, endedAt, mode, networkName)
    private fun DeviceEntity.toDomain() = Device(id, scanId, source, ip, mac, bssid, ssid, vendor, hostname, type, subnetIndex, rssi, distanceBand, firstSeen, lastSeen, posX, posY, posZ)
    private fun Device.toEntity(sid: Long) = DeviceEntity(id, sid, source, ip, mac, bssid, ssid, vendor, hostname, type, subnetIndex, rssi, distanceBand, firstSeen, lastSeen, posX, posY, posZ)
    private fun FindingEntity.toDomain() = Finding(id, scanId, deviceId, type, severity, score, evidence, status, firstSeen, lastSeen, explanationKey)
    private fun Finding.toEntity() = FindingEntity(id, scanId, deviceId, type, severity, score, evidence, status, firstSeen, lastSeen, explanationKey)
}
