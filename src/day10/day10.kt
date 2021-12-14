package day10

import readInput
import java.lang.RuntimeException
import java.util.*

val Scores = mapOf<Char, Int>(
    Pair(')', 3),
    Pair(']', 57),
    Pair('}', 1197),
    Pair('>', 25137),
)

val Opens = setOf<Char>('(', '[', '{', '<')

fun main() {

    fun calculateScore(line: String): Int {
        val stack = ArrayDeque<Char>()
        for (c in line) {
            if (Opens.contains(c)) {
                stack.push(c)
            } else {
                val last = stack.pop()
                val expected = when (last) {
                    '(' -> ')'
                    '[' -> ']'
                    '<' -> '>'
                    '{' -> '}'
                    else -> ' '
                }
                if (c != expected) {
                    return Scores[c]!!
                }
            }
        }

        return 0
    }


    fun fixLine(line: String): Long {
        val stack = ArrayDeque<Char>()
        for (c in line) {
            if (Opens.contains(c)) {
                stack.push(c)
            } else {
                val last = stack.pop()
                val expected = when (last) {
                    '(' -> ')'
                    '[' -> ']'
                    '{' -> '}'
                    '<' -> '>'
                    else -> ' '
                }
                if (c != expected) {
                    return 0
                }
            }
        }

        var score = 0L

        do {
            println(score)
            score *= 5
            score += when (stack.pop()) {
                '(' -> 1
                '[' -> 2
                '{' -> 3
                '<' -> 4
                else -> throw RuntimeException("Broke")
            }
        } while (!stack.isEmpty())

        return score
    }


    // Part 1
    val result = readInput("Day10/Day10")
        .map { l -> calculateScore(l) }
        .sum()

    println(result)

    // Part 2
    val result2 = readInput("Day10/Day10")
        .map { l -> fixLine(l) }
        .filter{s -> s != 0L}
        .sorted()
    println(result2)

    println(result2[result2.size / 2])
}
