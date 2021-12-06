package day4

import readInput

data class Tile(val value: Int) {
    var marked: Boolean = false
}

class Board {

    private val board = arrayOf(
        arrayOfNulls<Tile>(5),
        arrayOfNulls(5),
        arrayOfNulls(5),
        arrayOfNulls(5),
        arrayOfNulls(5),
    )

    private val draws = mutableSetOf<Int>()

    private var completed = false
    private var rows = 0
    private var lastX = 0
    private var lastY = 0

    fun addRow(row: String) {
        // | 6 31  5 74 67|
        board[rows][0] = Tile(row.substring(0, 2).trim().toInt())
        board[rows][1] = Tile(row.substring(3, 5).trim().toInt())
        board[rows][2] = Tile(row.substring(6, 8).trim().toInt())
        board[rows][3] = Tile(row.substring(9, 11).trim().toInt())
        board[rows][4] = Tile(row.substring(12, 14).trim().toInt())

        rows++
    }

    fun numberCalled(number: Int) {
        for (i in 0 until rows) {
            for (j in 0 until board[0].size) {
                val tile = board[i][j]
                if (tile?.value == number) {
                    lastX = i
                    lastY = j
                    tile.marked = true
                }
                draws.add(number)
            }
        }
    }

    fun isBingo(): Boolean {
        val bingo = (rowComplete(lastX) || columnComplete(lastY)) && !completed
        if (bingo) {
            completed = true
        }
        return bingo
    }

    fun calculateScore(lastNumber: Int): Int {
        println()
        board.forEach { r ->
            r.forEach { t ->
                print(t!!.value)
                print(" ")
            }
            println()
        }

        println()

        board.forEach { r ->
            r.forEach { t ->
                if (t!!.marked) {
                    print("XX")
                } else {
                    print(t!!.value)
                }
                print(" ")
            }
            println()
        }

        println(draws)
        val leftovers = board.flatMap { row ->
            row.filter { i -> !i!!.marked }
                .map { i -> i!!.value }
        }
        println(leftovers)
        println(lastNumber)
        return leftovers.sum() * lastNumber
    }

    private fun rowComplete(i: Int): Boolean {
        return board[i][0]!!.marked &&
                board[i][1]!!.marked &&
                board[i][2]!!.marked &&
                board[i][3]!!.marked &&
                board[i][4]!!.marked
    }

    private fun columnComplete(j: Int): Boolean {
        return board[0][j]!!.marked &&
                board[1][j]!!.marked &&
                board[2][j]!!.marked &&
                board[3][j]!!.marked &&
                board[4][j]!!.marked
    }
}

fun main() {
    fun part1(draws: List<Int>, boards: List<Board>): Int {
        for (draw in draws) {
            for (board in boards) {
                board.numberCalled(draw)
                if (board.isBingo()) {
                    return board.calculateScore(draw)
                }
            }
        }

        return -1
    }

    fun part2(draws: List<Int>, boards: List<Board>): Int {
        var completed = 0
        for (draw in draws) {
            for (board in boards) {
                board.numberCalled(draw)
                if(board.isBingo()) {
                    completed++
                    if (completed == boards.size) {
                        return board.calculateScore(draw)
                    }
                }
            }
        }

        return -1
    }

    val lines = readInput("day4/Day04")
    val draws = lines[0].split(",").stream().map { i -> i.toInt() }.toList()

    var b = Board()
    val boards = mutableListOf<Board>()

    for (line in lines.subList(2, lines.size)) {
        if (line.isEmpty() || line.isBlank()) {
            boards.add(b)
            b = Board()
            continue
        }

        b.addRow(line)
    }

//    println(part1(draws, boards))
    println(part2(draws, boards))
}
