package com.github.pedrovgs.maxibons

import arrow.effects.IO
import arrow.effects.extensions.io.monad.monad
import arrow.effects.fix
import com.github.pedrovgs.maxibons.interpreters.SlackModule

fun main() {
    val karumiHQs = KarumiHQs(IO.monad(), SlackModule())
    val initialWorld = World(KarumiFridge(10))
    val finalWorld = karumiHQs.openFridge(initialWorld, listOf(Karumies.pedro, Karumies.manolo, Karumies.tylos, Karumies.sarroyo, Karumies.sergio, Karumies.juanjo))
            .fix().unsafeRunSync()
    println("Execution result $finalWorld")
}