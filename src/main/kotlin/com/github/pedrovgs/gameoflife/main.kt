package com.github.pedrovgs.gameoflife

import com.github.pedrovgs.gameoflife.interpreters.ConsoleUniversePrinter

fun main() {
    val bigBang = Universe.create(size = UniverseSize(25, 25),
            initialCells = mapOf(
                    Pair(Position(5, 5), Cell.alive),
                    Pair(Position(6, 5), Cell.alive),
                    Pair(Position(7, 5), Cell.alive),
                    Pair(Position(8, 5), Cell.alive),
                    Pair(Position(5, 6), Cell.alive)
            ))
    val timeMachine = TimeMachine(ConsoleUniversePrinter())
    timeMachine.advanceTime(95, bigBang).unsafeRunSync()
}