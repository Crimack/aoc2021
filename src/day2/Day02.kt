package day2

import readInput

data class CourseInstruction(val direction: String, val magnitude: Int)

fun main() {
    fun part1(instructions: List<CourseInstruction>): Int {
        var x = 0
        var y = 0

        for (instruction in instructions) {
            when (instruction.direction) {
                "forward" -> x += instruction.magnitude
                "up" -> y -= instruction.magnitude
                "down" -> y += instruction.magnitude
            }
        }

        return x * y
    }

    fun part2(instructions: List<CourseInstruction>): Int {
        var x = 0
        var y = 0
        var aim = 0

        for (instruction in instructions) {
            when (instruction.direction) {
                "forward" -> {
                    x += instruction.magnitude
                    y += aim * instruction.magnitude
                }
                "up" -> aim -= instruction.magnitude
                "down" -> aim += instruction.magnitude
            }
        }

        return x * y
    }

    val input =
        readInput("day2/Day02").map { i -> i.split(" ") }.map { j -> CourseInstruction(j[0], j[1].toInt()) }.toList()

    println(part1(input))
    println(part2(input))
}
