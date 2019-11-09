package ch.nicolasguillen.azurepos.di.modules

import android.app.Application
import android.content.Context
import ch.nicolasguillen.azurepos.services.*
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule(private val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    internal fun provideLoggerService(context: Context): LoggerService {
        return AzureLoggerService(context)
    }

    @Provides
    internal fun provideTemperatureSensorService(): TemperatureSensorService {
        return FakeTemperatureSensorService()
    }

    @Provides
    internal fun provideDeviceBandwidthService(): DeviceBandwidthService {
        return FakeDeviceBandwidthService()
    }

}