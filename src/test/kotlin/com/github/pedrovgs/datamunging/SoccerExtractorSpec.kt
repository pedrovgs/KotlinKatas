package com.github.pedrovgs.datamunging

import arrow.core.some
import com.github.pedrovgs.datamunging.interpreter.ResourcesFileReader
import com.github.pedrovgs.datamunging.interpreter.StdConsole
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class SoccerExtractorSpec : StringSpec({
    "finds the team with less difference between for and against goals" {
        val soccerExtractor = SoccerExtractor(CsvFileReader(ResourcesFileReader()), StdConsole())

        val team = soccerExtractor.findTeamWithSmallestDifferenceInForAndAgainstGoals("src/resources/football.dat").unsafeRunSync()

        team shouldBe TeamInfo("Leicester", 30, 64).some()
    }
})