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

/*
 * Kata Questions
 * To what extent did the design decisions you made when writing the original programs make it
 * easier or harder to factor out common code?
 *
 * Making both programs returning a value instead of an Option<T> made harder the final
 * implementation because this makes no sense at all.
 *
 * However, using functional combinators and grouping the code related to the file read process
 * made the final refactor way easier.
 *
 * Was the way you wrote the second program influenced by writing the first?
 *
 * Nope, but it helped me to finalize the final design.
 *
 * Is factoring out as much common code as possible always a good thing? Did the readability of the programs suffer because of this requirement? How about the maintainability?
 *
 * Refactoring after the second usage was the best choice. We can't create the correct adapter if
 * we don't know how the code has to be used at least twice.
 *
 * We could move more code to the common abstraction because these two programs are designed
 * to find the minimum value for any param but I think this will reduce flexibility in the future.
 */