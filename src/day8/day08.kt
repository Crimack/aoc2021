package day8

import readInput

fun main() {
    println(part1())
    println(part2())
}

private fun part1(): Int {
    return readInput("day8/Day08")
        .map { l -> l.split("|")[1] }
        .map { r -> r.trim().split(" ") }
        .flatMap { r ->
            r.map { s ->
                when (s.length) {
                    2 -> 1
                    3 -> 1
                    4 -> 1
                    7 -> 1
                    else -> 0
                }
            }
        }
        .sum()
}

private fun part2(): Int {
    return readInput("day8/Day08")
        .map { l ->
            val (inputs, outputs) = l.split("|")
            Pair(inputs, outputs)
        }
        .map { p ->
            val inputs = p.first.trim().split(" ")
            val outputs = p.second.trim().split(" ")
            calculateOutput(inputs, outputs)
        }.sum()
}

private fun calculateOutput(inputs: List<String>, outputs: List<String>): Int {
    // This might be the worst thing I've ever written
    val inputsByLength = inputs.groupBy { l -> l.length }

    var values = mutableMapOf<String, Int>()

    val one = inputsByLength[2]!![0]
    values[one.toCharArray().sorted().joinToString("")] = 1

    val four = inputsByLength[4]!![0]
    values[four.toCharArray().sorted().joinToString("")] = 4

    val seven = inputsByLength[3]!![0]
    values[seven.toCharArray().sorted().joinToString("")] = 7

    val eight = inputsByLength[7]!![0]
    values[eight.toCharArray().sorted().joinToString("")] = 8


    // Length 2 ==> 1
    // Length 3 ==> 7
    // Length 4 ==> 4
    // Length 5 ==> 2, 3, 5
    // Length 6 ==> 0, 6, 9
    // Length 7 ==> 8

    // Find 3, leave you with 2, 5
    val lengthFive = inputsByLength[5]!!.toMutableSet()

    val sevenSet = seven.toSortedSet()
    for (i in lengthFive) {
        val s = i.toSortedSet()
        if (s.subtract(sevenSet).size == 2) {
            values[i.toCharArray().sorted().joinToString("")] = 3
            lengthFive.remove(i)
            break
        }
    }

    // 9 is a superset of 5 and 4
    val lengthSix = inputsByLength[6]!!.toMutableSet()
    val fourSet = four.toSortedSet()

    for (i in lengthSix.toSet()) {
        val s = i.toSortedSet()
        for (j in lengthFive.toSet()) {
            val t = j.toSortedSet()
            if (fourSet.subtract(s).isEmpty() && t.subtract(s).isEmpty()) {
                values[i.toCharArray().sorted().joinToString("")] = 9
                lengthSix.remove(i)

                values[j.toCharArray().sorted().joinToString("")] = 5
                lengthFive.remove(j)
                break
            }
        }
    }

    // 2 is the only item left in fiveset
    val two = lengthFive.first()
    values[two.toCharArray().sorted().joinToString("")] = 2

    for (i in lengthSix) {
        val s = i.toSortedSet()
        if (sevenSet.subtract(s).isEmpty()) {
            values[i.toCharArray().sorted().joinToString("")] = 0
            lengthSix.remove(i)
            break
        }
    }

    // 6 is the only item left in sixset
    val six = lengthSix.first()
    values[six.toCharArray().sorted().joinToString("")] = 6

    return outputs.map { o -> values[o.toCharArray().sorted().joinToString("")]!!.toString() }
        .joinToString("")
        .toInt()
}
