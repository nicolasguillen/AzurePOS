package ch.nicolasguillen.azurepos.di.modules

import ch.nicolasguillen.azurepos.services.DeviceBandwidthService
import ch.nicolasguillen.azurepos.services.LoggerService
import ch.nicolasguillen.azurepos.services.TemperatureSensorService
import ch.nicolasguillen.azurepos.usecases.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class UseCaseModule {

    @Provides
    @Singleton
    fun providesCheckConnectionsUseCase(
        loadTemperatureUseCase: LoadTemperatureUseCase,
        loadBandwidthUseCase: LoadBandwidthUseCase,
        loggerService: LoggerService
    ): CheckHealthUseCase =
        FelFelCheckHealthUseCase(loadTemperatureUseCase, loadBandwidthUseCase, loggerService)

    @Provides
    fun providesLoadTemperatureUseCase(
        temperatureSensorService: TemperatureSensorService
    ): LoadTemperatureUseCase =
        FelFelLoadTemperatureUseCase(temperatureSensorService)

    @Provides
    fun providesLoadBandwidthUseCase(
        deviceBandwidthService: DeviceBandwidthService
    ): LoadBandwidthUseCase =
        FelFelLoadBandwidthUseCase(deviceBandwidthService)

}