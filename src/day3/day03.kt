package day3

import readInput

fun main() {
    fun part1(lines: List<String>): Int {
        var gamma = ""
        var epsilon = ""

        val frequencies = lines[0].map { _ -> mutableMapOf(false to 0, true to 0) }.toList()

        for (line in lines) {
            for (i in line.indices) {
                when (line[i]) {
                    '0' -> {
                        frequencies[i][false] = frequencies[i][false]!! + 1
                    }
                    '1' -> {
                        frequencies[i][true] = frequencies[i][true]!! + 1
                    }
                }
            }
        }

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

    fun filterLines(lines: List<String>, mostCommon: Boolean): Int{
        val length = lines[0].length
        var curr = lines

        for (i in 0 until length) {
            val (zeroes, ones) = curr.partition { l -> l[i] == '0' }
            curr = if (mostCommon) {
                if (zeroes.size > ones.size) {
                    zeroes
                } else {
                    ones
                }
            } else {
                if (zeroes.size > ones.size) {
                    ones
                } else {
                    zeroes
                }
            }

            if (curr.size == 1) {
                return curr[0].toInt(2)
            }
        }

        return -1
    }

    fun part2(lines: List<String>): Int {
        val oxygen = filterLines(lines, true)
        val c02 = filterLines(lines, false)
        return oxygen * c02
    }



    val lines = readInput("day3/Day03")

    println(part1(lines))
    println(part2(lines))
}
