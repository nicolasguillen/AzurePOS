package ch.nicolasguillen.azurepos

import android.app.Application
import android.content.Intent
import android.os.Build
import ch.nicolasguillen.azurepos.di.ApplicationComponent
import ch.nicolasguillen.azurepos.di.DaggerApplicationComponent
import ch.nicolasguillen.azurepos.di.modules.ApplicationModule
import ch.nicolasguillen.azurepos.di.modules.UseCaseModule
import ch.nicolasguillen.azurepos.services.HealthCheckService

class AzurePOSApp: Application() {

    companion object {
        @JvmStatic var applicationComponent: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = initApplicationComponent()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, HealthCheckService::class.java))
        } else {
            startService(Intent(this, HealthCheckService::class.java))
        }
    }

    private fun initApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .useCaseModule(UseCaseModule())
            .build()
    }

}