

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

    fun findBadgeLetter(group: List<String>): Char {
        var result = ' '
        for (char in group[0]) {
            if (group.all { char in it }) {
                result = char
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val priorities = mutableListOf<Int>()

        val chunkedInput = input.chunked(3)

        chunkedInput.map {
            val badgeLetter = findBadgeLetter(it)
            charToPriority[badgeLetter]!!
        }.forEach {
            priorities.add(it)
        }

        return priorities.sum()
    }

    val testInput = readInput("Day03")
    println(part1(testInput))
    println(part2(testInput))
}