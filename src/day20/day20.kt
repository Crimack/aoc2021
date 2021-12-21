package day20

import readInput

fun main() {
    val lines = readInput("day20/Day20Test")

    val legend = lines[0]
    println(legend.length)

    var curr =
        lines.subList(2, lines.size).flatMapIndexed { i, row -> row.mapIndexed { j, char -> Pair(Pair(i, j), char) } }
            .filter { it.second != '.' }
            .toMap()

    printLit(curr)


    for (step in 1 until 3) {
        println("Step $step")

        val xValues = curr.keys.map { it.first }.sorted()
        val minX = xValues.first() - 1
        val maxX = xValues.last() + 2

        val yValues = curr.keys.map { it.second }.sorted()
        val minY = yValues.first() - 1
        val maxY = yValues.last() + 2

        println("Drawing box $minX..$maxX, $minY..$maxY")

        val next = mutableMapOf<Pair<Int, Int>, Char>()
        for (x in minX until maxX) {
            for (y in minY until maxY) {
                print(curr.getOrDefault(Pair(x, y), '.'))

                val tile = mutableListOf<Char>()
                for (i in x - 1 until x + 2) {
                    for (j in y - 1 until y + 2) {
                        tile.add(curr.getOrDefault(Pair(i, j), '.'))
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

                if(legend[index] != '.') {
                    next[Pair(x, y)] = legend[index]
                }
            }
            println()
        }
        curr = next
        printLit(curr)
    }
}

fun printLit(curr: Map<Pair<Int, Int>, Char>) {
    println("Lit: " + curr.values.map {
        when (it) {
            '.' -> 0
            '#' -> 1
            else -> throw RuntimeException("Bucked")
        }
    }.sum())
    println()
}