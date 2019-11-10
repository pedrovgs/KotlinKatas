package com.github.pedrovgs.datamunging

import arrow.core.Option
import arrow.core.extensions.fx
import arrow.fx.IO
import arrow.fx.extensions.fx
import com.github.pedrovgs.datamunging.interpreter.StdConsole

class WeatherExtractor(private val reader: CsvFileReader, private val console: StdConsole) {
    fun findDayWithSmallestTemperature(file: String): IO<Option<WeatherInfoForDay>> = IO.fx {
        val (info) = reader.read(file) {
            Option.fx {
                val parts = it.split(",")
                val (day) = parts[0].safeToInt()
                val (minTemp) = parts[2].safeToDouble()
                val (maxTemp) = parts[1].safeToDouble()
                WeatherInfoForDay(day, minTemp, maxTemp)
            }
        }
        val coldestDay = Option.fromNullable(info.minBy { it.minTemp })
        val (str) = console.log("The day with the lowest temperature is $coldestDay")
        coldestDay
    }
}

data class WeatherInfoForDay(val dayOfMonth: Int, val minTemp: Double, val maxTemp: Double)