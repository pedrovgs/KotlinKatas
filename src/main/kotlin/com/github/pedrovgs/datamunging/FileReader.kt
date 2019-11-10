package com.github.pedrovgs.datamunging

import arrow.fx.IO

interface FileReader {
    fun readLines(file: String): IO<List<String>>
}