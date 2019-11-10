package com.github.pedrovgs.gameoflife

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec
import org.junit.jupiter.api.Assertions.assertEquals

class CellSpec : StringSpec({

    "every alive cell returns an alive cell if the number of alive neighbours is 2 or 3" {
        forAll(Gen.Companion.choose(2, 3)) { aliveNeighbours ->
            Cell.alive == Cell.alive.evolve(aliveNeighbours)
        }
    }

    "every alive cell returns a dead cell if the number of alive neighbours is lower than 2" {
        forAll(Gen.choose(Int.MIN_VALUE, 1)) { aliveNeighbours ->
            Cell.dead == Cell.alive.evolve(aliveNeighbours)
        }
    }

    "every alive cell returns a dead cell if the number of alive neighbours is greater than 3" {
        forAll(Gen.choose(4, Int.MAX_VALUE)) { aliveNeighbours ->
            Cell.dead == Cell.alive.evolve(aliveNeighbours)
        }
    }

    "every dead cell returns a dead cell if the number of alive neighbours is not 3" {
        forAll(Gen.int().filter { it != 3 }) { aliveNeighbours ->
            Cell.dead == Cell.dead.evolve(aliveNeighbours)
        }
    }

    "every dead cell returns a live cell if the number of alive neighbours is 3" {
        assertEquals(Cell.alive, Cell.dead.evolve(3))
    }
})
