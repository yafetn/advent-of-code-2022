fun main() {
    fun part1(input: List<String>): Int {
        val map = mutableMapOf<Int, Int>()
        val elfSequence = generateSequence(1) { it + 1 }.iterator()

        var currentElf: Int = elfSequence.next()

        for (line in input) {
            if (line.isEmpty()) {
                currentElf = elfSequence.next()
            } else {
                map[currentElf] = map.getOrDefault(currentElf, 0) + line.toInt()
            }
        }

        return map.values.max()
    }

    fun part2(input: List<String>): Int {
        val map = mutableMapOf<Int, Int>()
        val elfSequence = generateSequence(1) { it + 1 }.iterator()

        var currentElf: Int = elfSequence.next()

        for (line in input) {
            if (line.isEmpty()) {
                currentElf = elfSequence.next()
            } else {
                map[currentElf] = map.getOrDefault(currentElf, 0) + line.toInt()
            }
        }

        return map.values.sortedDescending().take(3).sum()
    }

    val testInput = readInput("Day01")
    println(part2(testInput))
}
