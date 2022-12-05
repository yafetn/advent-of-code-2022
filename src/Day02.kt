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
    val player2: Play,
    var scores: Pair<Int, Int>? = null
) {
    init {
        scoreRound()
    }

    private fun scoreRound() {
        var first = player1.option.value
        var second = player2.option.value

        if (player1 > player2) {
            first += Result.BEATS.value
        } else if (player1 < player2) {
            second += Result.BEATS.value
        } else {
            first += Result.DRAWS.value
            second += Result.DRAWS.value
        }

        scores = first to second
    }
}

val map = mapOf(
    "A" to Options.ROCK,
    "X" to Options.ROCK,

    "B" to Options.PAPER,
    "Y" to Options.PAPER,

    "C" to Options.SCISSORS,
    "Z" to Options.SCISSORS
)

fun main() {
    fun part1(input: List<String>): Int {
        val scoredRounds = input.map {
            val plays = it.split(" ")
            Round(
                player1 = Play(
                    option = map[plays[0]]!!
                ),
                player2 = Play(
                    option = map[plays[1]]!!
                    )
            )
        }

        return scoredRounds.map {
            it.scores!!.second
        }.sum()

    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("Day02")
    println(part1(testInput))
}
