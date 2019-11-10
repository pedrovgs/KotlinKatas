package com.github.pedrovgs.datamunging.interpreter

import arrow.fx.IO
import com.github.pedrovgs.datamunging.Console

class StdConsole : Console {

    override fun log(str: String): IO<String> = IO {
        println(str)
        str
    }
}