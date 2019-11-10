package com.github.pedrovgs.datamunging

import com.github.pedrovgs.datamunging.interpreter.ResourcesFileReader
import com.github.pedrovgs.datamunging.interpreter.StdConsole
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class SoccerExtractorSpec : StringSpec({
    "finds the Leicester as the team with less difference between for and against goals" {
        val console = StdConsole()
        val resourcesReader = ResourcesFileReader()
        val soccerExtractor = SoccerExtractor(resourcesReader, console)

        val team = soccerExtractor.findTeamWithSmallestDifferenceInForAndAgainstGoals("src/resources/football.dat").unsafeRunSync()

        team shouldBe TeamInfo("Leicester", 30, 64)
    }
})