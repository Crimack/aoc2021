package day15

import readInput
import java.util.*

data class Risk(val location: Pair<Int, Int>, val danger: Int)

fun main() {
    val riskMap = readInput("day15/Day15")
        .map { l -> l.toCharArray().map { c -> c.digitToInt() }.toMutableList() }
    val height = riskMap.size
    val width = riskMap[0].size

    val expandedMap = arrayOfNulls<Int>(height * 5).map { arrayOfNulls<Int>(width * 5) }
    riskMap.forEachIndexed { i, row ->
        row.forEachIndexed { j, cell ->
            IntRange(0, 4).flatMap { rowOffset ->
                IntRange(0, 4).map { columnOffset ->
                    var new = cell + rowOffset + columnOffset
                    if (new > 9) {
                        new %= 9
                    }
                    expandedMap[i + height * columnOffset][j + width * rowOffset] = new
                }
            }
        }
    }

    expandedMap.forEach{ r ->
        r.forEach { c -> print(c) }
        println()
    }

    val unvisitedNodes = expandedMap.flatMapIndexed { i, list ->
        list.mapIndexed { j, cell ->
            Risk(Pair(i, j), cell!!)
        }
    }.toMutableSet()

    val start = Pair(0, 0)
    val end = Pair(expandedMap.size - 1, expandedMap[0].size - 1)

    val distanceMap = mutableMapOf(Pair(start, 0))

    val nextQueue = PriorityQueue { a: Pair<Risk, Int>, b: Pair<Risk, Int> -> a.second - b.second }
    nextQueue.add(Pair(Risk(start, expandedMap[0][0]!!), 0))

    while (unvisitedNodes.isNotEmpty() && nextQueue.isNotEmpty()) {
        val curr = nextQueue.poll()
        if (!unvisitedNodes.contains(curr.first)) {
            continue
        }

        unvisitedNodes.remove(curr.first)
        val currentDistance = distanceMap[curr.first.location]!!

        // Visit all the neighbours
        for (offset in listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))) {
            val i = curr.first.location.first + offset.first
            val j = curr.first.location.second + offset.second

            if (i < 0 || i >= expandedMap.size || j < 0 || j >= expandedMap[i].size) {
                continue
            }

            val neighbourLocation = Pair(i, j)
            val existingDistance = distanceMap[neighbourLocation]

            val neighbourCost = expandedMap[i][j]!!
            val newDistance = currentDistance + neighbourCost
            if (existingDistance == null || newDistance < existingDistance) {
                distanceMap[neighbourLocation] = newDistance
            }

            nextQueue.add(Pair(Risk(neighbourLocation, neighbourCost), newDistance))
        }
    }

    println(distanceMap[end])
}