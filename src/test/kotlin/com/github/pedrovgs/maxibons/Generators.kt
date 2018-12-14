package com.github.pedrovgs.maxibons

import io.kotlintest.properties.Gen

object Generators {

    class DeveloperGenerator : Gen<Developer> {
        override fun constants(): Iterable<Developer> = emptyList()

        override fun random(): Sequence<Developer> =
                generateSequence {
                    Developer(Gen.string().random().first(), Gen.int().random().first())
                }
    }

    class WorldGenerator : Gen<World> {
        override fun constants(): Iterable<World> = emptyList()

        override fun random(): Sequence<World> =
                generateSequence {
                    World(KarumiFridge(Gen.int().random().first()))
                }
    }

    class HungryDeveloperGenerator : Gen<Developer> {
        override fun constants(): Iterable<Developer> = emptyList()

        override fun random(): Sequence<Developer> = generateSequence {
            Developer(Gen.string().random().first(), Gen.choose(8, Int.MAX_VALUE).random().first())
        }
    }

    class NotSoHungryDeveloperGenerator : Gen<Developer> {
        override fun constants(): Iterable<Developer> = emptyList()

        override fun random(): Sequence<Developer> = generateSequence {
            Developer(Gen.string().random().first(), Gen.choose(0, 7).random().first())
        }
    }
}