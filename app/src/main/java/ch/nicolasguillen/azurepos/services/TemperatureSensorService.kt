package ch.nicolasguillen.azurepos.services

import kotlin.random.Random

interface TemperatureSensorService {
    fun getFridgeTemperature(): Double
}

class FakeTemperatureSensorService: TemperatureSensorService {

    override fun getFridgeTemperature(): Double {
        return Random.nextDouble(-10.0, 20.0)
    }

}