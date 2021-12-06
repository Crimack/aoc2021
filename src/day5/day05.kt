package day5

import readInput
import kotlin.math.max
import kotlin.math.min

data class Location(val x: Int, val y: Int) {
    var visits = 1
}

data class Line(val from: Location, val to: Location) {
    fun isHorizontal(): Boolean {
        return from.y == to.y
    }

    fun isVertical(): Boolean {
        return from.x == to.x
    }

    // I've clearly forgotten how lines work, send help
    fun draw(): List<Location> {
        if (isVertical()) {
            return min(from.y, to.y)
                .rangeTo(max(from.y, to.y))
                .map { y -> Location(from.x, y) }
        }

        if (isHorizontal()) {
            return min(from.x, to.x)
                .rangeTo(max(from.x, to.x))
                .map { x -> Location(x, from.y) }
        }

        val deltaX = if (from.x < to.x) 1 else  -1
        val deltaY = if (from.y < to.y) 1 else -1

        val locations = mutableListOf<Location>()
        var curr = from
        while(curr != to) {
            locations.add(curr)
            curr = Location(curr.x + deltaX, curr.y + deltaY)
        }
        locations.add(to)

        return locations
    }
}

fun main() {
    fun part1(input: List<Line>): Int {
        val filtered = input.filter { l -> l.isHorizontal() || l.isVertical() }

        val lineMap = mutableMapOf<Int, Map<Int, Location>>()
        for (line in filtered) {
            val locations = line.draw()
            for (location in locations) {
                var row = lineMap.getOrPut(location.x) { mutableMapOf() }.toMutableMap()

                var existingLocation = row[location.y]
                if (existingLocation != null) {
                    existingLocation.visits++
                } else {
                    existingLocation = location
                }
                row[location.y] = existingLocation
                lineMap[location.x] = row
            }
        }

        return lineMap.flatMap { row -> row.value.map { l -> l.value }.filter { l -> l.visits > 1 } }.size
    }

    fun part2(input: List<Line>): Int {
        val lineMap = mutableMapOf<Int, Map<Int, Location>>()
        for (line in input) {
            val locations = line.draw()
            for (location in locations) {
                var row = lineMap.getOrPut(location.x) { mutableMapOf() }.toMutableMap()

                var existing = row[location.y]
                if (existing != null) {
                    existing.visits++
                } else {
                    existing = location
                }
                row[location.y] = existing
                lineMap[location.x] = row
            }
        }

        return lineMap.flatMap { row -> row.value.map { l -> l.value }.filter { l -> l.visits > 1 } }.size
    }

    val input = readInput("day5/Day05").map { l ->
        val parts = l.split("->")

        val from = parts[0].split(",").map { p -> p.trim().toInt() }
        val fromLocation = Location(from[0], from[1])

        val to = parts[1].split(",").map { p -> p.trim().toInt() }
        val toLocation = Location(to[0], to[1])

        Line(fromLocation, toLocation)
    }

    println(part1(input))
    println(part2(input))
}
