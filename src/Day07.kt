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

fun inputParser(input: List<String>): Directory {
    val rootDirectory = Directory(
        name = "/",
        parent = null
    )

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
                    )
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
                recalcSize(currentDirectory)

            }
        }
    }

    return rootDirectory
}

fun findDirectory(name: String, currentDirectory: Directory): Directory? {
    return currentDirectory.directories?.find { it.name == name }
}

fun recalcSize(current: Directory?) {
    var curr = current?.parent

    while (curr != null ) {
        curr.size = curr.directories?.sumOf { it.size!! }?.let {
            curr!!.size?.plus(
                it
            )
        }
        curr = curr.parent
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        val rootDirectory = inputParser(input)



//        var dirsUnderLimit = rootDirectory.directories?.flatMap {
//            it.directories!!.flatMap {
//                it.directories!!.flatMap {
//                    it.directories!!
//                }
//            }
//        }?.toMutableList()

        if (rootDirectory.size!! <= 100_000) if (dirsUnderLimit != null) {
            dirsUnderLimit += rootDirectory
        }

        return dirsUnderLimit?.sumOf { it.size!! } ?: 0
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("Day07")
    println(part1(testInput))
    // println(part2(testInput))
}

