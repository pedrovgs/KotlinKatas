package com.github.pedrovgs.gameoflife

import java.lang.StringBuilder

data class Cell(val isAlive: Boolean) {
    companion object {
        val alive: Cell = Cell(true)
        val dead: Cell = Cell(false)
    }

    fun evolve(aliveNeighbours: Int): Cell =
            if (isAlive) {
                evolveLiveCell(aliveNeighbours)
            } else {
                evolveDeadCell(aliveNeighbours)
            }

    private fun evolveLiveCell(aliveNeighbours: Int): Cell =
            when {
                aliveNeighbours < 2 -> die()
                aliveNeighbours == 2 || aliveNeighbours == 3 -> live()
                else -> die()
            }

    private fun evolveDeadCell(aliveNeighbours: Int): Cell =
            when (aliveNeighbours) {
                3 -> live()
                else -> die()
            }

    private fun live(): Cell = copy(isAlive = true)
    private fun die(): Cell = copy(isAlive = false)
}

data class UniverseSize(val rows: Int, val columns: Int)
data class Position(val x: Int, val y: Int)

data class Universe(val cells: Array<Array<Cell>>) {
    companion object {
        fun create(size: UniverseSize, initialCells: Map<Position, Cell>) =
                Universe((0 until size.rows).map { x ->
                    (0 until size.columns).map { y ->
                        initialCells[Position(x, y)] ?: Cell.dead
                    }.toTypedArray()
                }.toTypedArray())
    }

    val aliveCells = cells.map { it.count { cell -> cell.isAlive } }.sum()

    val deadCells = cells.map { it.count() }.sum() - aliveCells

    val numberOfCells = cells.map { it.count() }.sum()

    fun tick(): Universe {
        val newCellsAfterTick = cells.mapIndexed { x, rows ->
            rows.mapIndexed { y, _ ->
                val cell = cells[x][y]
                val neighbours = neighbours(x, y)
                val aliveNeighbours = neighbours.count { it.isAlive }
                cell.evolve(aliveNeighbours)
            }.toTypedArray()
        }.toTypedArray()
        return copy(cells = newCellsAfterTick)
    }

    private fun neighbours(x: Int, y: Int): List<Cell> {
        val tentativePositions = listOf(
                Pair(x - 1, y - 1),
                Pair(x, y - 1),
                Pair(x + 1, y - 1),
                Pair(x - 1, y),
                Pair(x + 1, y),
                Pair(x - 1, y + 1),
                Pair(x, y + 1),
                Pair(x + 1, y + 1)
        )
        val existingPositions = tentativePositions.filter {
            it.first >= 0 &&
                    it.first < cells.count() &&
                    it.second >= 0 &&
                    it.second < cells[it.first].count()
        }
        return existingPositions.map { cells[it.first][it.second] }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        cells.forEach {
            it.forEach { cell ->
                if (cell.isAlive) {
                    stringBuilder.append("X")
                } else {
                    stringBuilder.append("_")
                }
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Universe

        if (!cells.contentDeepEquals(other.cells)) return false

        return true
    }

    override fun hashCode(): Int {
        return cells.contentDeepHashCode()
    }
}