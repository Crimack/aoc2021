package day9

import readInput
import java.util.*

fun main() {
    var heightMap = readInput("day9/Day09")
        .map { l -> l.toCharArray().map { c -> c.digitToInt() } }

    // Part 1
    val lowPoints = mutableListOf<Pair<Int, Int>>()
    for (i in heightMap.indices) {
        for (j in heightMap[0].indices) {

            val adjacent = mutableListOf<Int>()
            if (i > 0) {
                adjacent.add(heightMap[i - 1][j])
            }
            if (i < heightMap.size - 1) {
                adjacent.add(heightMap[i + 1][j])
            }
            if (j > 0) {
                adjacent.add(heightMap[i][j - 1])
            }
            if (j < heightMap[0].size - 1) {
                adjacent.add(heightMap[i][j + 1])
            }
            val point = heightMap[i][j]
            if (point < adjacent.minOrNull()!!) {
                lowPoints.add(Pair(i, j))
            }
        }
    }

    println(lowPoints.map { point -> heightMap[point.first][point.second] + 1 }.sum())

    // Part 2
    val basinSizes = mutableListOf<Int>()
    for (lowPoint in lowPoints) {
        var basinSize = 0

        val queue = LinkedList<Pair<Int, Int>>()
        queue.push(lowPoint)

        val visited = mutableSetOf<Pair<Int, Int>>()

        while (!queue.isEmpty()) {
            val location = queue.poll()
            val (i, j) = location
            val point = heightMap[i][j]
            if (point == 9 || visited.contains(location)) {
                continue
            }
            visited.add(location)

            if (i > 0) {
                val next = Pair(i - 1, j)
                if (!visited.contains(next)) {
                    queue.push(next)
                }
            }
            if (i < heightMap.size - 1) {
                val next = Pair(i + 1, j)
                if (!visited.contains(next)) {
                    queue.push(next)
                }
            }
            if (j > 0) {
                val next = Pair(i, j - 1)
                if (!visited.contains(next)) {
                    queue.push(next)
                }
            }
            if (j < heightMap[0].size - 1) {
                val next = Pair(i, j + 1)
                if (!visited.contains(next)) {
                    queue.push(next)
                }
            }

            basinSize++
        }
        basinSizes.add(basinSize)
    }

    println(basinSizes.sorted().takeLast(3).fold(1) { x, y -> x * y })
}