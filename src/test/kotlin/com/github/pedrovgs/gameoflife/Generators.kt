package com.github.pedrovgs.gameoflife

import io.kotlintest.properties.Gen

object Generators {
    class CellGenerator : Gen<Cell> {
        override fun constants(): Iterable<Cell> = emptyList()

        override fun random(): Sequence<Cell> =
                generateSequence {
                    Cell(Gen.bool().random().first())
                }
    }

    class EmptyUniverseGenerator : Gen<Universe> {
        override fun constants(): Iterable<Universe> = emptyList()

        override fun random(): Sequence<Universe> = generateSequence {
            val rows = Gen.choose(0, 100).random().first()
            val columns = Gen.choose(0, 100).random().first()
            Universe.create(UniverseSize(rows, columns), emptyMap())
        }
    }

    class UniverseGenerator(private val numberOfCells: Int? = null, private val cellGenerator: Gen<Cell> = CellGenerator()) : Gen<Universe> {
        override fun constants(): Iterable<Universe> = emptyList()

        override fun random(): Sequence<Universe> = generateSequence {
            val rows = Gen.choose(2, 20).random().first()
            val columns = Gen.choose(2, 20).random().first()
            val numberOfCells = numberOfCells ?: Gen.choose(0, rows * columns).random().first()
            val cells = (0 until numberOfCells).map {
                val y = Gen.choose(0, columns - 1).random().first()
                val x = Gen.choose(0, rows - 1).random().first()
                val position = Position(x, y)
                val cell = cellGenerator.random().first()
                Pair(position, cell)
            }
            val initialCells = cells.toMap()
            Universe.create(UniverseSize(rows, columns), initialCells)
        }
    }

    fun universeWithDeadCells() = UniverseGenerator(null, Gen.constant(Cell.dead))

    fun universeWithAliveNumberOfCellsGenerator(numberOfCells: Int): Gen<Universe> =
            UniverseGenerator(numberOfCells, Gen.constant(Cell.alive))

    fun universeWithDeadNumberOfCellsGenerator(numberOfCells: Int): Gen<Universe> =
            UniverseGenerator(numberOfCells, Gen.constant(Cell.dead))

    fun reasonableNumberOfTicks(): Gen<Int> = Gen.choose(1, 50)
}