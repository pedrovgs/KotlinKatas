package com.github.pedrovgs.datamunging

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.extensions.fx
import arrow.core.extensions.sequence.functorFilter.flattenOption
import arrow.core.fix
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.mtl.Kleisli
import com.github.pedrovgs.datamunging.interpreter.StdConsole

class SoccerExtractor(private val reader: FileReader, private val console: StdConsole) {
    fun findTeamWithSmallestDifferenceInForAndAgainstGoals(file: String): IO<TeamInfo> = IO.fx {
        val (lines) = reader.readLines(file)
        val team = lines.asSequence().map { it.trim().replace("\\s+".toRegex(), ",") }
                .filter { it.firstOrNull()?.isDigit() == true }
                .map {
                    Option.fx {
                        val parts = it.split(",")
                        val name = parts[1]
                        val (forGoals) = parts[6].safeToInt()
                        val (againstGoals) = parts[8].safeToInt()
                        TeamInfo(name, forGoals, againstGoals)
                    }
                }
                .flattenOption()
                .sortedBy { it.goalsDifference }
                .first()
        val (str) = console.log("The team with the lowest difference between for and against goals is: ${team.name}")
        team
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

data class TeamInfo(val name: String, val forGoals: Int, val againstGoals: Int) {
    val goalsDifference: Int = forGoals - againstGoals
}