package ch.nicolasguillen.azurepos.services

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import ch.nicolasguillen.azurepos.BuildConfig
import com.microsoft.azure.sdk.iot.device.DeviceClient
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol
import com.microsoft.azure.sdk.iot.device.Message
import io.reactivex.Single

interface LoggerService {
    fun log(temperature: Double, bandwidth: Long): Single<Unit>
}

class AzureLoggerService(private val context: Context) : LoggerService {

    override fun log(temperature: Double, bandwidth: Long): Single<Unit> {
        return Single.create { observer ->
            val json = """{
                "locationId": "${getLocationId()}",
                "temperatureInCelsius": $temperature,
                "bandwidthInBytes": $bandwidth
            }""".trimIndent()
            val message = Message(json).apply {
                setContentTypeFinal("utf-8")
                setContentTypeFinal("application/json")
            }
            val client = DeviceClient(BuildConfig.AZURE_IOT_HUB_CONNECTION_KEY, IotHubClientProtocol.MQTT)
            client.open()

            try {
                client.sendEventAsync(message, { _, _ ->
                    client.closeNow()
                    observer.onSuccess(Unit)
                }, 1)
            } catch (exception: Exception) {
                observer.onError(exception)
            }

        }
    }

    @SuppressLint("HardwareIds")
    private fun getLocationId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

}