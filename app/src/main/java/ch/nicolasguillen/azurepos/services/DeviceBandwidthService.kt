package ch.nicolasguillen.azurepos.services

import kotlin.random.Random

interface DeviceBandwidthService {
    fun getTotalBandwidthInBytes(): Long
}

class FakeDeviceBandwidthService: DeviceBandwidthService {

    override fun getTotalBandwidthInBytes(): Long {
        return Random.nextLong(0, 100)
    }

}