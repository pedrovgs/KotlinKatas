package com.github.pedrovgs.gameoflife

import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec
import com.github.pedrovgs.gameoflife.Generators.EmptyUniverseGenerator
import com.github.pedrovgs.gameoflife.Generators.UniverseGenerator
import com.github.pedrovgs.gameoflife.Generators.universeWithAliveNumberOfCellsGenerator
import com.github.pedrovgs.gameoflife.Generators.universeWithDeadCells
import com.github.pedrovgs.gameoflife.Generators.universeWithDeadNumberOfCellsGenerator
import io.kotlintest.inspectors.forAll
import io.kotlintest.shouldBe
import io.kotlintest.tables.row

class UniverseSpec : StringSpec({

    val singleTickScenarios = listOf(
            row(
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(1, 0), Cell.alive),
                            Pair(Position(1, 1), Cell.alive)
                    )),
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(0, 1), Cell.dead),
                            Pair(Position(1, 1), Cell.dead)
                    ))),
            row(
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(1, 0), Cell.alive),
                            Pair(Position(1, 1), Cell.alive)
                    )),
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(0, 1), Cell.dead),
                            Pair(Position(1, 1), Cell.dead)
                    ))),
            row(
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(1, 0), Cell.alive),
                            Pair(Position(1, 1), Cell.alive),
                            Pair(Position(1, 2), Cell.alive)
                    )),
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(0, 1), Cell.alive),
                            Pair(Position(1, 1), Cell.alive),
                            Pair(Position(2, 1), Cell.alive)
                    ))),
            row(
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(0, 1), Cell.alive),
                            Pair(Position(1, 1), Cell.alive),
                            Pair(Position(2, 1), Cell.alive)
                    )),
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(1, 0), Cell.alive),
                            Pair(Position(1, 1), Cell.alive),
                            Pair(Position(1, 2), Cell.alive)
                    ))),
            row(
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(0, 1), Cell.alive),
                            Pair(Position(1, 1), Cell.alive),
                            Pair(Position(2, 1), Cell.alive),
                            Pair(Position(2, 2), Cell.alive)
                    )),
                    Universe.create(UniverseSize(10, 10), mapOf(
                            Pair(Position(1, 0), Cell.alive),
                            Pair(Position(1, 1), Cell.alive),
                            Pair(Position(2, 1), Cell.alive),
                            Pair(Position(2, 2), Cell.alive)
                    )))
    )

    "evaluates every single tick scenarios" {
        singleTickScenarios.forAll { (initialUniverse, expectedUniverse) ->
            val evolvedUniverse = initialUniverse.tick()

            evolvedUniverse shouldBe expectedUniverse
        }
    }

    "generates a complex figure starting from a simple one after 3 ticks" {
        val initialUniverse = Universe.create(UniverseSize(10, 10), mapOf(
                Pair(Position(2, 2), Cell.alive),
                Pair(Position(3, 2), Cell.alive),
                Pair(Position(4, 2), Cell.alive),
                Pair(Position(4, 3), Cell.alive)
        ))

        val evolvedUniverse = tickUniverse(initialUniverse, 4)

        val expectedUniverse = Universe.create(UniverseSize(10, 10), mapOf(
                Pair(Position(2, 2), Cell.alive),
                Pair(Position(3, 1), Cell.alive),
                Pair(Position(3, 3), Cell.alive),
                Pair(Position(4, 1), Cell.alive),
                Pair(Position(4, 3), Cell.alive),
                Pair(Position(5, 2), Cell.alive)
        ))
        evolvedUniverse shouldBe expectedUniverse
    }

    "every universe sum of alive and dad cells will be equivalent to the size of the universe" {
        forAll(UniverseGenerator()) { universe ->
            universe.aliveCells + universe.deadCells == universe.numberOfCells
        }
    }

    "every empty universe will not generate any new cell" {
        forAll(EmptyUniverseGenerator(), Generators.reasonableNumberOfTicks()) { universe, numberOfTicks ->
            tickUniverse(universe, numberOfTicks).aliveCells == 0
        }
    }

    "a universe with just a single cell alive will die" {
        forAll(universeWithAliveNumberOfCellsGenerator(1), Generators.reasonableNumberOfTicks()) { universe, numberOfTicks ->
            tickUniverse(universe, numberOfTicks).aliveCells == 0
        }
    }

    "a universe with 1 dead cell will remain dead" {
        forAll(universeWithDeadNumberOfCellsGenerator(1), Generators.reasonableNumberOfTicks()) { universe, numberOfTicks ->
            tickUniverse(universe, numberOfTicks).aliveCells == 0
        }
    }

    "every universe full of dead cells will remain dead" {
        forAll(universeWithDeadCells()) { universe ->
            universe.deadCells == universe.numberOfCells
        }
    }
})

private fun tickUniverse(universe: Universe, numberOfTicks: Int): Universe {
    val timeMachine = TimeMachine(SilentUniversePrinter())
    return timeMachine.advanceTime(numberOfTicks, universe).unsafeRunSync()
}
