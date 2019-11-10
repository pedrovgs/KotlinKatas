package com.github.pedrovgs.gameoflife.interpreters

import arrow.fx.IO
import com.github.pedrovgs.gameoflife.Universe
import com.github.pedrovgs.gameoflife.UniversePrinter

class ConsoleUniversePrinter : UniversePrinter {
    override fun print(universe: Universe): IO<String> = IO {
        println("-------------------------")
        val stringRepresentation = universe.toString()
        print(stringRepresentation)
        stringRepresentation
    }
}