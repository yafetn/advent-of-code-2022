

fun main() {
    val charToPriority = CharRange('a', 'z').zip(1..26).toMap().toMutableMap()
    charToPriority.putAll(
        CharRange('A', 'Z').zip(27..52).toMap()
    )

    fun part1(input: List<String>): Int {
        val priorities = mutableListOf<Int>()

        for (rucksack in input) {
            val (first, second) = rucksack.chunked(rucksack.length / 2)

            for (char in first) {
                if (char in second) {
                    priorities.add(
                        charToPriority[char]!!
                    )
                    break
                }
            }
        }

        return priorities.sum()

    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("Day03")
    println(part1(testInput))
}