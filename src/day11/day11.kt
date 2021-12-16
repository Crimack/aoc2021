package day11

import readInput

fun main() {
    val octopi = readInput("day11/Day11")
        .map { l -> l.toCharArray().map { c -> c.digitToInt() }.toMutableList() }

    var flashes = 0

    var step = 0
    var allFlashed = false
    while (!allFlashed) {
        step += 1

//        println("Step $step")
//        octopi.forEach{r ->
//            r.forEach{j -> print(j)}
//            println()
//        }
//        println()

        val flashQueue = ArrayDeque<Pair<Int, Int>>()
        for ((i, row) in octopi.withIndex()) {
            for ((j, octopus) in row.withIndex()) {
                val new = octopus + 1
                octopi[i][j] = new

                if (new > 9) {
                    flashQueue.addLast(Pair(i, j))
                }
            }
        }

        val flashed = mutableSetOf<Pair<Int, Int>>()
        while (flashQueue.isNotEmpty()) {
            val toFlash = flashQueue.removeFirst()
            if (flashed.contains(toFlash)) {
                continue
            }

            flashes++
            flashed.add(toFlash)

            for (i in toFlash.first - 1 until toFlash.first + 2) {
                for (j in toFlash.second - 1 until toFlash.second + 2) {
                    if (i < 0 || i >= octopi.size || j < 0 || j >= octopi[i].size) {
                        continue
                    }
                    if (i == toFlash.first && j == toFlash.second) {
                        continue
                    }

                    val newPair = Pair(i, j)
                    val curr = octopi[i][j] + 1
                    octopi[i][j] = curr
                    if (curr > 9) {
                        flashQueue.addLast(newPair)
                    }
                }
            }
        }

        flashed.forEach { f -> octopi[f.first][f.second] = 0 }

        println("${flashed.size} flashed on $step")
        if (flashed.size == 100) {
            println("All flashed on $step")
            allFlashed = true
        }
    }
}