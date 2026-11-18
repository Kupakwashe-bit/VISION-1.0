package com.krimson.kuona.core.network.source

import com.krimson.kuona.core.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Singleton
class SimulationDataSource @Inject constructor() : ScanSource {

    override val sourceType: ScanSourceType = ScanSourceType.SIMULATION
    override val isAvailable: Boolean = true

    private var running = false

    override fun discover(scanId: Long): Flow<ScanEvent> = flow {
        running = true
        emit(ScanEvent.StateChanged(ScanState.MAPPING))
        emit(ScanEvent.ScanProgress("SIMULATION PROCESSING", "MAPPING NETWORK"))

        val subCenters = listOf(
            Pair(-10f, -7f),
            Pair(10f, -7f),
            Pair(-10f, 8f),
            Pair(10f, 8f)
        )

        val allDevices = mutableListOf<Device>()
        var devIdCounter = 1L

        val core = Device(
            id = devIdCounter++,
            scanId = scanId,
            source = ScanSourceType.SIMULATION,
            ip = "10.0.0.1",
            type = DeviceType.CORE,
            subnetIndex = -1,
            vendor = "Cisco Systems",
            hostname = "core-router-01",
            posX = 0f, posY = 0f, posZ = 0f
        )
        allDevices.add(core)
        emit(ScanEvent.DeviceDiscovered(core))

        val subnets = mutableListOf(
            SubnetInfo(0, "SUBNET_01", 11, 0),
            SubnetInfo(1, "SUBNET_02", 11, 0),
            SubnetInfo(2, "SUBNET_03", 11, 0),
            SubnetInfo(3, "SUBNET_04", 11, 0)
        )

        subCenters.forEachIndexed { sIdx, pair ->
            val cx = pair.first
            val cz = pair.second
            val subNum = sIdx + 1
            val hub = Device(
                id = devIdCounter++,
                scanId = scanId,
                source = ScanSourceType.SIMULATION,
                ip = "10.0." + subNum + ".1",
                type = DeviceType.SWITCH,
                subnetIndex = sIdx,
                vendor = "Juniper Networks",
                hostname = "sw-edge-0" + subNum,
                posX = cx, posY = 0f, posZ = cz
            )
            allDevices.add(hub)
            emit(ScanEvent.DeviceDiscovered(hub))

            for (i in 0 until 11) {
                val angle = Random.nextFloat() * 6.283f
                val r = 3f + Random.nextFloat() * 3.2f
                val isSrv = i < 2
                val host = Device(
                    id = devIdCounter++,
                    scanId = scanId,
                    source = ScanSourceType.SIMULATION,
                    ip = "10.0." + subNum + "." + (i + 10),
                    type = if (isSrv) DeviceType.SERVER else DeviceType.HOST,
                    subnetIndex = sIdx,
                    vendor = if (isSrv) "Dell Enterprise" else "Generic Linux/Android",
                    hostname = (if (isSrv) "srv-" else "node-") + subNum + "-0" + i,
                    posX = cx + cos(angle) * r,
                    posY = 0f,
                    posZ = cz + sin(angle) * r
                )
                allDevices.add(host)
                emit(ScanEvent.DeviceDiscovered(host))
            }
        }

        emit(ScanEvent.SubnetUpdated(subnets.toList()))

        val airspaceSample = listOf(
            Device(id = devIdCounter++, scanId = scanId, source = ScanSourceType.WIFI_AP, ssid = "GUEST_AIR_5G", bssid = "AA:BB:CC:DD:EE:01", type = DeviceType.ACCESS_POINT, rssi = -42, distanceBand = DistanceBand.NEAR, posX = -18f, posY = 0f, posZ = -6f),
            Device(id = devIdCounter++, scanId = scanId, source = ScanSourceType.BLE, vendor = "Apple / AirTag", type = DeviceType.BLUETOOTH, rssi = -68, distanceBand = DistanceBand.MID, posX = 16f, posY = 0f, posZ = -14f),
            Device(id = devIdCounter++, scanId = scanId, source = ScanSourceType.HOTSPOT_CLIENT, ssid = "Pixel_Hotspot", type = DeviceType.HOTSPOT, rssi = -82, distanceBand = DistanceBand.FAR, posX = 0f, posY = 0f, posZ = 22f)
        )
        airspaceSample.forEach { dev ->
            allDevices.add(dev)
            emit(ScanEvent.DeviceDiscovered(dev))
        }

        delay(3500)
        if (!running) return@flow

        emit(ScanEvent.StateChanged(ScanState.DETECTING))
        emit(ScanEvent.ScanProgress("SIMULATION PROCESSING", "DETECTING THREATS"))

        val threatPool = allDevices.filter { it.type == DeviceType.HOST || it.type == DeviceType.SERVER }
        val threatTypes = listOf(
            ThreatType.PORT_SCAN to "PORT SCAN",
            ThreatType.BRUTE_FORCE to "BRUTE FORCE",
            ThreatType.C2_BEACON to "C2 BEACON",
            ThreatType.DATA_EXFIL to "DATA EXFIL",
            ThreatType.ARP_SPOOF to "ARP SPOOF",
            ThreatType.LATERAL_MOVE to "LATERAL MOVE"
        )

        val k = 4
        val targets = threatPool.shuffled().take(k)
        var threatCounter = 0

        targets.forEachIndexed { i, target ->
            if (!running) return@flow
            delay(1200)
            threatCounter++
            val pair = threatTypes[i % threatTypes.size]
            val tType = pair.first
            val tLabel = pair.second
            val finding = Finding(
                id = i + 1L,
                scanId = scanId,
                deviceId = target.id,
                type = tType,
                severity = Severity.HIGH,
                score = 0.88f + Random.nextFloat() * 0.1f,
                evidence = listOf(
                    "High abnormal flow volume detected on " + (target.ip ?: "unknown"),
                    "Repeated syn flood / inter-arrival burst score = 0.94",
                    "Classification matched signature " + tLabel
                ),
                explanationKey = tLabel
            )

            val sIdx = target.subnetIndex
            if (sIdx != null && sIdx in 0..3) {
                val cur = subnets[sIdx]
                subnets[sIdx] = cur.copy(flaggedCount = cur.flaggedCount + 1)
                emit(ScanEvent.SubnetUpdated(subnets.toList()))
            }

            emit(ScanEvent.ThreatRevealed(finding, target))
            emit(ScanEvent.ThreatCount(threatCounter, k))
        }

        delay(1000)
        if (!running) return@flow
        emit(ScanEvent.StateChanged(ScanState.CONFIRMED))
        emit(ScanEvent.ScanProgress(k.toString() + " THREATS", "CONFIRMED"))
        emit(ScanEvent.ThreatCount(k, k))

        delay(4000)
        if (!running) return@flow

        emit(ScanEvent.StateChanged(ScanState.DISSOLVE))
        delay(1500)
        emit(ScanEvent.ScanComplete)
    }

    override suspend fun stop() {
        running = false
    }
}

