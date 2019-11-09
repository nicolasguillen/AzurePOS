package ch.nicolasguillen.azurepos.usecases

import ch.nicolasguillen.azurepos.services.DeviceBandwidthService
import ch.nicolasguillen.azurepos.services.TemperatureSensorService
import io.reactivex.Single

interface LoadBandwidthUseCase {
    fun getBandwidth(): Single<Long>
}

class FelFelLoadBandwidthUseCase(private val deviceBandwidthService: DeviceBandwidthService): LoadBandwidthUseCase {

    override fun getBandwidth(): Single<Long> {
        return Single.just(deviceBandwidthService.getTotalBandwidthInBytes())
    }

}