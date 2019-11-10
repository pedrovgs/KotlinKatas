package com.github.pedrovgs.gameoflife

import arrow.fx.IO

class SilentUniversePrinter : UniversePrinter {
    override fun print(universe: Universe): IO<String> = IO.just("")
}