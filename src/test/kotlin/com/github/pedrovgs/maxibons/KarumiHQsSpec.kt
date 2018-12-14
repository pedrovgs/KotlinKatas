package com.github.pedrovgs.maxibons

import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.instances.io.monad.monad
import com.github.pedrovgs.maxibons.interpreters.SlackModule
import io.kotlintest.specs.StringSpec

class KarumiHQsSpec: StringSpec({

    "always have more than 2 maxibons" {
        val karumiHQs = KarumiHQs(IO.monad(), SlackModule())
        val initialWorld = World(KarumiFridge(10))
        val finalWorld = karumiHQs.openFridge(initialWorld, listOf(Karumies.pedro, Karumies.manolo, Karumies.tylos, Karumies.sarroyo, Karumies.sergio, Karumies.juanjo))
                .fix().unsafeRunSync()
        println("Execution result $finalWorld")
    }

})