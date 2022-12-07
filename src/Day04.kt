

fun main() {

    fun inputToRanges(input: List<String>): List<Pair<IntRange, IntRange>> {
        return input.map {
            val (firstRange, secondRange) = it.split(",")
            firstRange to secondRange
        }.map {
            val first = it.first.split("-")
            val second = it.second.split("-")
            first[0].toInt()..first[1].toInt() to second[0].toInt()..second[1].toInt()
        }
    }

    fun part1(input: List<String>): Int {
        val rangePairs = inputToRanges(input = input)
        var counter = 0

        for (rangePair in rangePairs) {
            if ((rangePair.first.first in rangePair.second && rangePair.first.last in rangePair.second) ||
                    rangePair.second.first in rangePair.first && rangePair.second.last in rangePair.first) {
                counter++
            }
        }

        return counter
    }


    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("Day04")
    println(part1(testInput))
    // println(part2(testInput))
}
