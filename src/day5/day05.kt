package day5

import readInput

const val STARTING_VALUE = Integer.MIN_VALUE
const val WINDOW_SIZE = 3

data class Location(val x:Int, val y:Int)

data class Line(val from:Location, val to: Location) {

    var intersections = 0

    fun isHorizontal() : Boolean{
        return from.y == to.y
    }

    fun isVertical() : Boolean{
        return from.x == to.x
    }

    fun draw(): List<Location> {

    }
}

fun main() {
    fun part1(input: List<Line>): Int {
        val filtered = input.filter { l -> l.isHorizontal() || l.isVertical() }

        val lineMap = arrayListOf<Location>()
        for (line in filtered) {
            line.
        }
    }

    fun part2(input: List<Int>): Int {
        return 0
    }

    val input = readInput("day5/Day05").map { l ->
        val parts = l.split("->")

        val from = parts[0].split(",").map { p -> p.toInt() }
        val fromLocation = Location(from[0], from[1])

        val to = parts[1].split(",").map { p -> p.toInt() }
        val toLocation = Location(to[0], to[1])

        Line(fromLocation, toLocation)
    }

    println(part1(input))
    println(part2(input))
}
