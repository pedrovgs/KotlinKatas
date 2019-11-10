package com.github.pedrovgs.datamunging

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.extensions.fx
import arrow.core.extensions.sequence.functorFilter.flattenOption
import arrow.core.fix
import arrow.fx.IO
import arrow.fx.extensions.fx
import com.github.pedrovgs.datamunging.interpreter.StdConsole
import arrow.mtl.Kleisli

class WeatherExtractor(private val reader: FileReader, private val console: StdConsole) {
    fun findDayWithSmallestTemperature(file: String): IO<WeatherInfoForDay> = IO.fx {
        val (lines) = reader.readLines(file)
        val coldestDay = lines.asSequence().map { it.trim().replace("\\s+".toRegex(), ",") }
                .filter { it.firstOrNull()?.isDigit() == true }
                .map {
                    Option.fx {
                        val parts = it.split(",")
                        val (day) = parts[0].safeToInt()
                        val (minTemp) = parts[2].safeToDouble()
                        val (maxTemp) = parts[1].safeToDouble()
                        WeatherInfoForDay(day, minTemp, maxTemp)
                    }
                }
                .flattenOption()
                .sortedBy { it.minTemp }
                .first()
        val (printedStr) = console.log("The day with the lowest temperature is $coldestDay")
        coldestDay
    }

    private val optionStringToIntKleisli = Kleisli { str: String ->
        if (str.toCharArray().all { it.isDigit() }) Some(str.toInt()) else None
    }
    private val optionStringToDoubleKleisli = Kleisli { str: String ->
        if (str.toCharArray().all { it.isDigit() }) Some(str.toDouble()) else None
    }
    private fun String.safeToInt(): Option<Int> {
        return optionStringToIntKleisli.run(this).fix()
    }
    private fun String.safeToDouble(): Option<Double> {
        return optionStringToDoubleKleisli.run(this).fix()
    }
}

data class WeatherInfoForDay(val dayOfMonth: Int, val minTemp: Double, val maxTemp: Double)