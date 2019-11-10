package com.github.pedrovgs.maxibons

import arrow.fx.IO
import arrow.fx.extensions.io.async.async
import arrow.fx.extensions.io.monad.monad
import arrow.fx.fix
import com.github.pedrovgs.maxibons.interpreters.SlackModule

fun main() {
    val karumiHQs = KarumiHQs(SlackModule(IO.async()), IO.monad(), IO.async())
    val initialWorld = World(KarumiFridge(10))
    val finalWorld = karumiHQs.openFridge(initialWorld, listOf(Karumies.pedro, Karumies.manolo, Karumies.tylos, Karumies.sarroyo, Karumies.sergio, Karumies.juanjo))
            .fix().unsafeRunSync()
    println("Execution result $finalWorld")
}