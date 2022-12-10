data class Tree(
    val height: Int,
    val top: Tree? = null,
    var bottom: Tree? = null,
    val left: Tree? = null,
    var right: Tree? = null,
    var isVisible: Boolean = false
)

data class Patch(
    val trees: Array<Array<Tree>>
)

fun main() {
    fun parsePatch(input: List<String>): Patch {
        val patchWidth = input[0].length
        val patch = Patch(
            trees = Array(input.size) { Array(patchWidth) { Tree(
                height = 0
            ) } }
        )

        for ((i, line) in input.withIndex()) {
            for ((j, char) in line.withIndex()) {
                val tree = Tree(
                    height = char.digitToInt(),
                    left = if (j == 0) null else patch.trees[i][j - 1],
                    right = if (j == patchWidth - 1) null else patch.trees[i][j + 1],
                    top = if (i == 0) null else patch.trees[i - 1][j],
                    bottom = if (i == patch.trees.size - 1) null else patch.trees[i + 1][j],
                    isVisible = j == 0 || j == patchWidth - 1 || i == 0 || i == patch.trees.size - 1
                )
                patch.trees[i][j] = tree
                tree.top?.bottom = tree
                tree.left?.right = tree
            }
        }
        return patch
    }

    fun updateVisibility(patch: Patch) {
        val trees = patch.trees

        for ((i, treeLine) in trees.withIndex()) {
            for ((j, tree) in treeLine.withIndex()) {
                if (tree.isVisible) continue

                val fromLeft = treeLine.sliceArray(0 until j).all { it.height < tree.height }
                val fromRight = treeLine.sliceArray(j + 1 until treeLine.size).all {
                    it.height < tree.height
                }
                var curr: Tree? = tree.top
                val topList = mutableListOf<Tree>()
                while (curr != null) {
                    topList += curr
                    curr = curr.top
                }

                curr = tree.bottom
                val bottomList = mutableListOf<Tree>()
                while (curr != null) {
                    bottomList += curr
                    curr = curr.bottom
                }
                val fromTop = topList.all { it.height < tree.height }
                val fromBottom = bottomList.all { it.height < tree.height }

                tree.isVisible = fromLeft || fromRight || fromTop || fromBottom
            }
        }
    }

    fun part1(input: List<String>): Int {
        val patch = parsePatch(
            input = input
        )

        updateVisibility(patch)

        return patch.trees.flatten().count { it.isVisible }

    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("Day08")
    println(part1(testInput))
    // println(part2(testInput))
}