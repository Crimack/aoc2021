package day17

import readInput

val PARSER = Regex("target area: x=(-?\\d+)\\.\\.(-?\\d+), y=-(-?\\d+)\\.\\.(-?\\d+)")

fun main() {

    val input = readInput("day17/Day17")[0]
    val matches = PARSER.matchEntire(input)

    val groups = matches!!.groupValues

    val x1 = groups[1].toInt()
    val x2 = groups[2].toInt()
    val y1 = groups[3].toInt()
    val y2 = groups[4].toInt()

    val minX = minOf(x1, x2)
    val maxX = maxOf(x1, x2)
    val minY = minOf(y1, y2)
    val maxY = maxOf(y1, y2)

    println("x $minX to $maxX")
    println("y $maxY to $minY")

    var maxHeight = Integer.MIN_VALUE
    var bestInitialVelocity = Pair(0, 0)

    for (i in 1 until 281) {
        for (j in 0 until 10000) {
            var currPos = Pair(0, 0)
            var maxHeightReached = 0
            var xVelocity = i
            var yVelocity = j

            do {
                currPos = Pair(currPos.first + xVelocity, currPos.second + yVelocity)
                if (currPos.second > maxHeightReached) {
                    maxHeightReached = currPos.second
                }

                if (currPos.first in minX..maxX && currPos.second >= minY && currPos.second <= maxY) {
                    println("Curr Pos $currPos, velocity $i, $j, curr velocity $xVelocity, $yVelocity")
                    if (maxHeightReached > maxHeight) {
                        maxHeight = maxHeightReached
                        bestInitialVelocity = Pair(i, j)
                    }
                }

                if (xVelocity > 0) {
                    xVelocity -= 1
                }
                yVelocity -= 1

            } while (currPos.first <= maxX && currPos.second >= minY)
        }
    }

    println("Height $maxHeight , velocity $bestInitialVelocity")
}