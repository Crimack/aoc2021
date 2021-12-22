package day20

import readInput

fun main() {
    val lines = readInput("day20/Day20")
//    val lines = readInput("day20/Day20Test")

    val legend = lines[0]
    println(legend.length)

    var curr =
        lines.subList(2, lines.size).flatMapIndexed { i, row -> row.mapIndexed { j, char -> Pair(Pair(i, j), char) } }
            .toMap()

    printLit(curr)

    val xValues = curr.keys.map { it.first }.sorted()
    var minX = xValues.first()
    var maxX = xValues.last()

    val yValues = curr.keys.map { it.second }.sorted()
    var minY = yValues.first()
    var maxY = yValues.last()

    for (step in 1..50) {
        println("Step $step, box $minX..$maxX, $minY..$maxY")

        val next = mutableMapOf<Pair<Int, Int>, Char>()
        for (x in minX - 1..maxX + 1) {
            for (y in minY - 1..maxY + 1) {

                val tile = mutableListOf<Char>()
                for (i in x - 1..x + 1) {
                    for (j in y - 1..y + 1) {
                        tile.add(
                            curr.getOrDefault(
                                Pair(i, j), when (legend.first() == '#') {
                                    // The space around the known area of the board will always be binary 000000.
                                    // This effectively means that it'll 'blink' from . -> # repeatedly, and we need
                                    // to treat the unknown space differently depending on how many iterations we've
                                    // been through.
                                    true -> if (step % 2 == 0) legend.first() else legend.last()
                                    false -> '.'
                                }
                            )
                        )
                    }
                }
                if (tile.size != 9) {
                    throw RuntimeException("Broken")
                }

                val index = tile.map {
                    when (it) {
                        '.' -> '0'
                        '#' -> '1'
                        else -> throw RuntimeException("Busted")
                    }
                }.joinToString("").toInt(2)

                next[Pair(x, y)] = legend[index]
            }
        }
        curr = next
        minX--
        minY--
        maxX++
        maxY++
    }
    printLit(curr)
}

fun printLit(curr: Map<Pair<Int, Int>, Char>) {
    println("Size: ${curr.size}")
    println("Lit: " + curr.values.map {
        when (it) {
            '.' -> 0
            '#' -> 1
            else -> throw RuntimeException("Bucked")
        }
    }.sum())
    println()
}