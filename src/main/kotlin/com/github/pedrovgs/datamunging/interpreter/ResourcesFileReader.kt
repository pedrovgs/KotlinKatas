package com.github.pedrovgs.datamunging.interpreter

import arrow.fx.IO
import com.github.pedrovgs.datamunging.FileReader
import java.io.File

class ResourcesFileReader : FileReader {
    override fun readLines(file: String): IO<List<String>> = IO {
        File(file).readLines()
    }
}