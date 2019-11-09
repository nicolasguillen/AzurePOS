package ch.nicolasguillen.azurepos.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ch.nicolasguillen.azurepos.AzurePOSApp
import ch.nicolasguillen.azurepos.usecases.CheckHealthUseCase
import javax.inject.Inject
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.os.Build

class HealthCheckService: Service() {

    @Inject
    lateinit var useCase: CheckHealthUseCase

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notificationChannel"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(NotificationChannel(channelId, "Health check", NotificationManager.IMPORTANCE_DEFAULT))

            startForeground(1, NotificationCompat.Builder(this, channelId)
                .setContentTitle("")
                .setContentText("")
                .build())
        }

        AzurePOSApp.applicationComponent?.inject(this)

        this.useCase.startHealthCheck()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

}