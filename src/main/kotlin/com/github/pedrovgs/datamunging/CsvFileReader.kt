package com.github.pedrovgs.datamunging

import arrow.core.Option
import arrow.core.extensions.list.functorFilter.flattenOption
import arrow.core.extensions.sequence.functorFilter.flattenOption
import arrow.fx.IO
import arrow.fx.extensions.fx

class CsvFileReader(private val reader: FileReader) {
    companion object {
        private val spacesRegex = "\\s+".toRegex()
    }

    fun <T> read(file: String, lineTransformer: (String) -> Option<T>): IO<List<T>> = IO.fx {
            val (lines) = reader.readLines(file)
            lines.asSequence()
                    .map { it.trim().replace(spacesRegex, ",") }
                    .filter { it.firstOrNull()?.isDigit() == true }
                    .map { lineTransformer(it) }
                    .toList()
                    .flattenOption()
        }
}