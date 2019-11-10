package com.github.pedrovgs.gameoflife

import arrow.fx.IO

class TimeMachine(private val printer: UniversePrinter) {

    fun advanceTime(ticks: Int, universe: Universe): IO<Universe> =
            printer.print(universe).flatMap {
                if (ticks == 0) {
                    IO.just(universe)
                } else {
                    advanceTime(ticks - 1, universe.tick())
                }
            }
}