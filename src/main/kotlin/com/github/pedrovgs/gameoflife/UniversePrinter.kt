package com.github.pedrovgs.gameoflife

import arrow.fx.IO

interface UniversePrinter {
    fun print(universe: Universe): IO<String>
}