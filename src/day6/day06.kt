package day6

import readInput

private val NEW_FISH = 8
private val PARENT_FISH = 6

fun main() {
    fun part1(input: List<Int>): Int {
        var curr = input
        var next = mutableListOf<Int>()

        for (i in 0 until 80) {
            for (fish in curr) {
                if (fish == 0) {
                    next.add(PARENT_FISH)
                    next.add(NEW_FISH)
                } else {
                    next.add(fish -1)
                }
            }
            curr = next
            next = mutableListOf()
        }

        return curr.size
    }

    fun part2(input: List<Int>): Long {
        // I'm sure you could do this without maintaining two maps as long as you correctly
        // remove now unused values, but two just seemed less complex. Happy to trade space for
        // readability
        var curr = input.groupBy { i -> i }.mapValues { i -> i.value.size.toLong() }.toMutableMap()
        var next = mutableMapOf<Int, Long>()

        for (i in 0 until 256) {
            val parentFish = curr.getOrDefault(0, 0)
            next[6] = parentFish
            next[8] = parentFish

            for (k in curr.keys) {
                if (k > 0) {
                    next[k - 1] = next.getOrDefault(k - 1, 0) + curr[k]!!
                }
            }
            curr = next
            next = mutableMapOf()
        }

        return curr.map { entry -> entry.value }.sum()
    }

    val fish = readInput("day6/Day06")[0].split(",").map { i -> i.toInt() }.toList()
    println(part1(fish))
    println(part2(fish))
}
