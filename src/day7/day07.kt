package day7

import readInput
import kotlin.math.abs

fun main() {
    fun calculateDistance(crabs: List<Int>, point: Int): Int {
        return crabs.sumOf { c -> abs(c - point) }
    }

    fun calculateCost(distance: Int, costMap: MutableMap<Int, Int>): Int {
        val cached = costMap[distance]
        return if (cached != null) {
            cached
        } else if (distance == 1 || distance == 0) {
            costMap[distance] = distance
            distance
        } else {
            val cost = distance + calculateCost(distance - 1, costMap)
            costMap[distance] = cost
            cost
        }
    }

    fun calculateDistanceAndCost(crabs: List<Int>, costMap: MutableMap<Int, Int>, point: Int): Int {
        return crabs.sumOf { c -> calculateCost(abs(c - point), costMap) }
    }

    // Brute force - I'm sure there's something with a mean or a binary search that'll speed this up
    fun part1(crabs: List<Int>): Int {
        var distance = Int.MAX_VALUE
        for (i in 0 until crabs.maxOrNull()!!) {
            val curr = calculateDistance(crabs, i)
            if (curr < distance) {
                distance = curr
            }
        }

        return distance
    }

    fun part2(crabs: List<Int>): Int {
        var costMap = mutableMapOf<Int, Int>()
        var distance = Int.MAX_VALUE
        for (i in 0 until crabs.maxOrNull()!!) {
            val curr = calculateDistanceAndCost(crabs, costMap, i)
            if (curr < distance) {
                distance = curr
            }
        }

        return distance
    }

    val crabs = readInput("day7/Day07")[0].split(",").map { i -> i.toInt() }.toList()
    println(part1(crabs))
    println(part2(crabs))
}