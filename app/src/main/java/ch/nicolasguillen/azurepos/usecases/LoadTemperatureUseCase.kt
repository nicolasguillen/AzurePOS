package ch.nicolasguillen.azurepos.usecases

import ch.nicolasguillen.azurepos.services.TemperatureSensorService
import io.reactivex.Single

interface LoadTemperatureUseCase {
    fun getTemperature(): Single<Double>
}

class FelFelLoadTemperatureUseCase(private val temperatureSensorService: TemperatureSensorService): LoadTemperatureUseCase {

    override fun getTemperature(): Single<Double> {
        return Single.just(temperatureSensorService.getFridgeTemperature())
    }

}