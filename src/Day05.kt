import java.util.Scanner

fun main() {

    fun stackParser(input: List<String>): Array<ArrayDeque<String>> {
        val stackString = input.filter {
            it.contains("\\[.?\\]".toRegex())
        }
        val numOfStacks = stackString.maxOf {
            it.count { c: Char -> c.isLetter() }
        }
        val result = Array<ArrayDeque<String>>(numOfStacks) { ArrayDeque() }

        for (line in stackString) {

            val crates = line.chunked(
                size = 4,
                transform = {
                    it.trim().replace("\\[|\\]".toRegex(), "")
                }
            ).toMutableList()

            for ((j, crate) in crates.withIndex()) {
                if (crate.isNotEmpty()) {
                    result[j].addFirst(crate)
                }
            }

        }

        return result
    }

    fun craneParser(
        stacks: Array<ArrayDeque<String>>,
        input: List<String>,
        craneVersion: String = "9000"
    ): Array<ArrayDeque<String>> {
        val craneStrings = input.filter {
            it.startsWith("move")
        }

        var scanner: Scanner?

        for (instruction in craneStrings) {
            scanner = Scanner(instruction)
            scanner.next("move")
            val cratesToMove = scanner.nextInt()
            scanner.next("from")
            val sourceStack = scanner.nextInt()
            scanner.next("to")
            val destStack = scanner.nextInt()

            if (craneVersion == "9000") {
                repeat(cratesToMove) {
                    stacks[destStack - 1].addLast(
                        stacks[sourceStack - 1].removeLast()
                    )
                }
            } else {
                val toMove = ArrayDeque<String>()
                repeat(cratesToMove) {
                    toMove.addFirst(
                        stacks[sourceStack - 1].removeLast()
                    )
                }
                stacks[destStack - 1].addAll(
                    toMove
                )
            }

        }

        return stacks
    }

    fun part1(input: List<String>): String {
        val stacks = stackParser(input)
        val finalStacks = craneParser(
            stacks = stacks,
            input = input
        )

        var tops = ""

        for (stack in finalStacks) {
            tops += stack.removeLast()
        }

        return tops
    }


    fun part2(input: List<String>): String {
        val stacks = stackParser(input)
        val finalStacks = craneParser(
            stacks = stacks,
            input = input,
            craneVersion = "9001"
        )

        var tops = ""

        for (stack in finalStacks) {
            tops += stack.removeLast()
        }

        return tops
    }

    val testInput = readInput("Day05")
    println(part1(testInput))
    println(part2(testInput))
}
