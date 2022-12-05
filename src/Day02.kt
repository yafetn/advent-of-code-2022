enum class Options(val value: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

enum class Result(val value: Int) {
    BEATS(6),
    LOSES(0),
    DRAWS(3)
}

data class Play(
    val option: Options
): Comparable<Play> {
    override fun compareTo(other: Play): Int {
        var comparator = 0

        if (this.option == Options.ROCK) {
            comparator = when(other.option) {
                Options.PAPER ->  -1
                Options.SCISSORS -> 1
                Options.ROCK -> 0
            }
        }

        if (this.option == Options.PAPER) {
            comparator = when(other.option) {
                Options.PAPER -> 0
                Options.SCISSORS -> -1
                Options.ROCK -> 1
            }
        }

        if (this.option == Options.SCISSORS) {
            comparator = when(other.option) {
                Options.PAPER -> 1
                Options.SCISSORS -> 0
                Options.ROCK -> -1
            }
        }

        return comparator
    }
}

data class Round(
    val player1: Play,
    var player2: Play? = null,
    val result: Result,
    var scores: Pair<Int, Int>? = null
) {
    init {
        determinePlayer2()
        scoreRound()
    }

    private fun scoreRound() {
        var first = player1.option.value
        var second = player2?.option?.value

        if (player1 > player2!!) {
            first += Result.BEATS.value
        } else if (player1 < player2!!) {
            second = second!! + Result.BEATS.value
        } else {
            first += Result.DRAWS.value
            second = second!! + Result.DRAWS.value
        }

        scores = first to second!!
    }

    private fun determinePlayer2() {
        var res: Play?

        when (player1.option) {
            Options.ROCK -> {
                res = when (result) {
                    Result.BEATS -> Play(Options.PAPER)
                    Result.DRAWS -> Play(Options.ROCK)
                    Result.LOSES -> Play(Options.SCISSORS)
                }
            }
            Options.PAPER -> {
                res = when (result) {
                    Result.BEATS -> Play(Options.SCISSORS)
                    Result.DRAWS -> Play(Options.PAPER)
                    Result.LOSES -> Play(Options.ROCK)
                }
            }
            else -> {
                res = when (result) {
                    Result.BEATS -> Play(Options.ROCK)
                    Result.DRAWS -> Play(Options.SCISSORS)
                    Result.LOSES -> Play(Options.PAPER)
                }
            }
        }

        player2 = res
    }
}

val letterToOption = mapOf(
    "A" to Options.ROCK,
    "B" to Options.PAPER,
    "C" to Options.SCISSORS,
)

val letterToResult = mapOf(
    "X" to Result.LOSES,
    "Y" to Result.DRAWS,
    "Z" to Result.BEATS,
)

fun main() {
    fun part1(input: List<String>): Int {
        val scoredRounds = input.map {
            val plays = it.split(" ")
            Round(
                player1 = Play(
                    option = letterToOption[plays[0]]!!
                ),
                result = letterToResult[plays[1]]!!
            )
        }

        return scoredRounds.sumOf {
            it.scores!!.second
        }

    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("Day02")
    println(part1(testInput))
}
