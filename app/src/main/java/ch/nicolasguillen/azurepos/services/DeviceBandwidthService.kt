package ch.nicolasguillen.azurepos.services

import kotlin.random.Random

interface DeviceBandwidthService {
    fun getTotalBandwidthInBytes(): Long
}

class FakeDeviceBandwidthService: DeviceBandwidthService {

    override fun getTotalBandwidthInBytes(): Long {
        return Random.nextLong(100_000, 1_500_000)
    }

}