package com.github.pedrovgs.datamunging

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.github.pedrovgs.datamunging.interpreter.ResourcesFileReader
import com.github.pedrovgs.datamunging.interpreter.StdConsole

fun main() {
    IO.fx {
        val console = StdConsole()
        val reader = CsvFileReader(ResourcesFileReader())
        val weatherExtractor = WeatherExtractor(reader, console)
        val (weather) = weatherExtractor.findDayWithSmallestTemperature("src/resources/weather.dat")
        val soccerExtractor = SoccerExtractor(reader, console)
        val (team) = soccerExtractor.findTeamWithSmallestDifferenceInForAndAgainstGoals("src/resources/football.dat")
        Pair(weather, team)
    }.unsafeRunSync()
}