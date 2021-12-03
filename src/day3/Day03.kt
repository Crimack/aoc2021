package day3

import readInput

fun main() {
    fun part1(frequencies: List<MutableMap<Boolean, Int>>): Int {
        var gamma = ""
        var epsilon = ""

        for (f in frequencies) {
            val zeroes = f[false]
            val ones = f[true]

            if (ones!! > zeroes!!) {
                gamma += "1"
                epsilon += "0"
            } else {
                gamma += "0"
                epsilon += "1"
            }
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(frequencies: List<MutableMap<Boolean, Int>>): Int {
        return 0
    }

    val lines = readInput("day3/Day03")
    // Just eagerly instantiate the counters
    val frequencyMaps = lines[0].map { _ -> mutableMapOf(false to 0, true to 0) }.toList()
    for (line in lines) {
        for (i in line.indices) {
            when (line[i]) {
                '0' -> {
                    frequencyMaps[i][false] = frequencyMaps[i][false]!! + 1
                }
                '1' -> {
                    frequencyMaps[i][true] = frequencyMaps[i][true]!! + 1
                }
            }
        }
    }

    println(part1(frequencyMaps))
    println(part2(frequencyMaps))
}
