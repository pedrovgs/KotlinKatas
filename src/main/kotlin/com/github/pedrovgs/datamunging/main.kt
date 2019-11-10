package com.github.pedrovgs.datamunging

import arrow.fx.handleError
import com.github.pedrovgs.datamunging.interpreter.ResourcesFileReader
import com.github.pedrovgs.datamunging.interpreter.StdConsole

fun main() {
    val weatherExtractor = WeatherExtractor(ResourcesFileReader(), StdConsole())
    weatherExtractor.findDayWithSmallestTemperature("src/resources/weather.dat")
            .handleError { exception ->
                println("Found unexpected error while trying to find the coldest day $exception")
            }.unsafeRunSync()
}