package day1

import readInput

const val STARTING_VALUE = Integer.MIN_VALUE
const val WINDOW_SIZE = 3

fun main() {
    fun part1(input: List<Int>): Int {
        var prev: Int
        var curr : Int = STARTING_VALUE
        var increases = 0
        for (i in input) {
            prev = curr
            curr = i

            if (curr > prev && prev != STARTING_VALUE) {
                increases++
            }
        }
        return increases
    }

    fun part2(input: List<Int>): Int {
        var increases = 0
        for (i in 0 until input.size - WINDOW_SIZE) {
            val first = input.subList(i, i + WINDOW_SIZE)
            val second = input.subList(i + 1, i + 1 + WINDOW_SIZE)

            if (second.sum() > first.sum()) {
                increases++
            }
        }
        return increases
    }

    // test if implementation meets criteria from the description, like:
    val input = readInput("day1/Day01").map{ i -> i.toInt() }.toList()

    println(part1(input))
    println(part2(input))
}
