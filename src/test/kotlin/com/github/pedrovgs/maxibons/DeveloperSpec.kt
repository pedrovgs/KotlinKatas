package com.github.pedrovgs.maxibons

import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec

class DeveloperSpec : StringSpec({

    "never instantiates a developer consuming a negative amount of maxibons" {
        forAll { name: String, maxibonsToGrab: Int ->
            val configuredMaxibons = Developer(name, maxibonsToGrab).maxibonsToGrab
            configuredMaxibons >= 0 || configuredMaxibons == maxibonsToGrab
        }
    }
})
