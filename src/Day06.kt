fun main() {
    fun part1(input: List<String>): Int {
        var result = -1

        for (line in input) {
            var right = 4 - 1
            var left = 0

            for(i in right until line.length) {
                val window = line.slice(left..right).toSet()
                if (window.size == 4) {
                    result = i + 1
                    break
                }
                right++
                left++
            }
        }

        return result
    }


    fun part2(input: List<String>): Int {
        var result = -1

        for (line in input) {
            var right = 14 - 1
            var left = 0

            for(i in right until line.length) {
                val window = line.slice(left..right).toSet()
                if (window.size == 14) {
                    result = i + 1
                    break
                }
                right++
                left++
            }
        }

        return result
    }

    val testInput = readInput("Day06")
    println(part1(testInput))
    println(part2(testInput))
}
