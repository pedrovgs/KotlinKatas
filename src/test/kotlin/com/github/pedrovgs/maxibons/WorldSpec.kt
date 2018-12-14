package com.github.pedrovgs.maxibons

import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec

class WorldSpec : StringSpec({

    "never instantiates a KarumiFrigde with less than 3 maxibons" {
        forAll { maxibonsLeft: Int ->
            val world = World(KarumiFridge(maxibonsLeft))
            val configuredMaxibonsLeft = World.worldMaxibonsLeft.get(world)
            configuredMaxibonsLeft > 2 || configuredMaxibonsLeft == maxibonsLeft
        }
    }

    "never instantiates a KarumiFrigde with more than 12 maxibons" {
        forAll { maxibonsLeft: Int ->
            val world = World(KarumiFridge(maxibonsLeft))
            val configuredMaxibonsLeft = World.worldMaxibonsLeft.get(world)
            configuredMaxibonsLeft <= 12 || configuredMaxibonsLeft == maxibonsLeft
        }
    }
})