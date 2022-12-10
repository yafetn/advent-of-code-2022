data class File(
    val size: Int,
    val name: String
)

data class Directory(
    val parent: Directory?,
    val name: String,
    var files: List<File>? = mutableListOf(),
    var directories: List<Directory>? = mutableListOf(),
    var size: Int? = 0
)

fun inputParser(input: List<String>): Pair<Directory, MutableMap<String, Int>> {
    val rootDirectory = Directory(
        name = "/",
        parent = null
    )

    val dirMap = mutableMapOf<String, Int>()

    var currentDirectory = rootDirectory

    for (line in input) {
        when {
            line.startsWith("$")  -> when (line) {
                "$ cd .." -> {
                    currentDirectory = currentDirectory.parent!!
                    continue
                }
                "$ ls" -> continue
                else -> {
                    if (line == "$ cd /") continue
                    val targetDirectory = line.split(" ")[2]
                    currentDirectory = findDirectory(
                        name = targetDirectory,
                        currentDirectory = currentDirectory
                    )!!
                    continue
                }
            }

            line.startsWith("dir") -> {
                currentDirectory.directories = currentDirectory.directories?.plus(
                    Directory(
                        parent = currentDirectory,
                        name = line.split(" ")[1]
                    ).also {
                        dirMap[it.name + it.parent!!.name] = it.size!!
                    }
                )
            }

            line.contains("^\\d+ ".toRegex()) -> {
                val (size, name) = line.split(" ")

                currentDirectory.files = currentDirectory.files?.plus(
                    File(
                        name = name,
                        size = size.toInt()
                    )
                )
                currentDirectory.size = currentDirectory.size?.plus(size.toInt())
                dirMap["${currentDirectory.name}${currentDirectory.parent?.name ?: ""}"] = currentDirectory.size!!
                recalcSize(currentDirectory, dirMap)
            }
        }
    }
    dirMap[rootDirectory.name] = rootDirectory.size!!
    return rootDirectory to dirMap
}

fun findDirectory(name: String, currentDirectory: Directory): Directory? {
    return currentDirectory.directories?.find { it.name == name }
}

fun recalcSize(current: Directory?, dirMap: MutableMap<String, Int>) {
    var curr = current?.parent

    while (curr != null ) {
        curr.size = curr.directories?.sumOf { it.size!! }?.plus(curr.files?.sumOf { it.size }!!)
        dirMap["${curr.name}${curr.parent?.name ?: ""}"] = curr.size!!
        curr = curr.parent
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        val (_, dirMap) = inputParser(input)

        val dirsUnderLimit = dirMap.filter { it.value <= 100_000 }

        return dirsUnderLimit.values.sum()
    }

    fun part2(input: List<String>): Int {
        val totalAvail = 70_000_000
        val updateNeeds = 30_000_000

        val (root, dirMap) = inputParser(input)

        val totalUsed = root.size!!
        val toFreeNeeded = updateNeeds - (totalAvail - totalUsed)

        val smallestPossibleDeletion = dirMap.filterValues { it >= toFreeNeeded }.minBy { it.value }

        return smallestPossibleDeletion.value
    }

    val testInput = readInput("Day07")
    println(part1(testInput))
    println(part2(testInput))
}
