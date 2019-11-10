package com.github.pedrovgs.datamunging

import arrow.core.Option
import arrow.core.extensions.fx
import arrow.fx.IO
import arrow.fx.extensions.fx
import com.github.pedrovgs.datamunging.interpreter.StdConsole

class SoccerExtractor(private val reader: CsvFileReader, private val console: StdConsole) {
    fun findTeamWithSmallestDifferenceInForAndAgainstGoals(file: String): IO<Option<TeamInfo>> = IO.fx {
        val (teams) = reader.read(file) {
            Option.fx {
                val parts = it.split(",")
                val name = parts[1]
                val (forGoals) = parts[6].safeToInt()
                val (againstGoals) = parts[8].safeToInt()
                TeamInfo(name, forGoals, againstGoals)
            }
        }
        val team = Option.fromNullable(teams.minBy { it.goalsDifference })
        val (str) = console.log("The team with the lowest difference between for and against goals is: ${team.map { it.name }}")
        team
    }
}

data class TeamInfo(val name: String, val forGoals: Int, val againstGoals: Int) {
    val goalsDifference: Int = forGoals - againstGoals
}