package su.kamil.dev.tasks.t8_paper_roll_wall_dynamic

import java.io.File
import java.io.FileReader
import kotlin.io.println


// we do bin matrix 3x3:
// our input    our bin matrix positioning
//              ###
// |@@@@.   |   #x#@@.  |
// |....@   |   ###..@  |
// |.@.@.   |==>|.@.@.  |==> moves left till border and then at new start and do it again
// |@..@.   |   |@..@.  |
// |@..@@   |   |@..@@  |
//
// we make
// I.  fun to move bin mat state to right
// and
// II. fun to move bin mat to bottom

// I    col1 = col2; col2 = col3; col3 = newCol - maybe easier than just manually scan grid again
// II   row1 = row2; row2 = row3; row3 = newRow - same shit
data class BinMat(val gridMatrix: List<String>) {
    val vec1 = BooleanArray(3)
    val vec2 = BooleanArray(3).apply {
        this[1] = gridMatrix[0][0] =='@'
        this[2] = gridMatrix[1][0] =='@'
    }
    val vec3 = BooleanArray(3).apply {
        this[1] = gridMatrix[0][1] =='@'
        this[2] = gridMatrix[1][1] =='@'
    }

    val vecNULL = BooleanArray(3, { false })
    val height = gridMatrix.size
    val width = gridMatrix.get(0).length
    var posX = 0
    var posY = 0
    val dynamicRepresentation:List<MutableList<Char>> = gridMatrix.map { it.toMutableList() }
    // for all caret moves, we know, that they were not go deeper than limits of grid(maximum go inside border to 1 pos)

    fun caretRight() {
        if(posX==width-1) throw Exception("You trying move caret to far(right) form border(${width-1})")
        vec2.copyInto(vec1)
        vec3.copyInto(vec2)
        if (posX <= width) {
            posX++

            vec3[0] = (posY > 0) && isPaperAt(posY - 1, posX)
            vec3[1] = isPaperAt(posY, posX)
            vec3[2] = (posY < height-1) && isPaperAt(posY + 1, posX)
        } else {
            vecNULL.copyInto(vec3)
        }
    }

    fun caretLeft() {
        if(posX==0) throw Exception("You trying move caret to far(left) form border(0)")

        vec2.copyInto(vec3)
        vec1.copyInto(vec2)
        if (posX > 0) {
            posX--
            vec1[0] = (posY>0) && isPaperAt(posY - 1, posX)
            vec1[1] = isPaperAt(posY, posX)
            vec1[2] = (posY < height-1) && isPaperAt(posY + 1, posX)
        } else {
            vecNULL.copyInto(vec1)
        }
    }

    fun caretDown() {
        vec1[0] = vec1[1]
        vec1[1] = vec1[2]

        vec2[0] = vec2[1]
        vec2[1] = vec2[2]

        vec3[0] = vec3[1]
        vec3[1] = vec3[2]



        if (posY >= height - 1) {
            vec1[2] = false
            vec2[2] = false
            vec3[2] = false
        } else {
            posY++
            vec1[2] = (posX > 0) && isPaperAt(posY, posX - 1)
            vec2[2] = isPaperAt(posY, posX)
            vec3[2] = (posX < width-1) && isPaperAt(posY, posX + 1)
        }

    }

    fun getAmountOfSurroundingPapers(): Int {
        var sum = 0
        // go in circle
        // rt mt lt lm | rb bm lb mr
        if (posY > 0            &&  posX > 0            &&  (dynamicRepresentation[posY - 1][posX - 1] == '@')) sum++
        if (posY > 0            &&                          (dynamicRepresentation[posY - 1][posX    ] == '@')) sum++
        if (posY > 0            &&  posX < width - 1    &&  (dynamicRepresentation[posY - 1][posX + 1] == '@')) sum++
        if (                        posX < width - 1    &&  (dynamicRepresentation[posY    ][posX + 1] == '@')) sum++

        if (posY < height - 1   &&  posX < width - 1    &&  (dynamicRepresentation[posY + 1][posX + 1] == '@')) sum++
        if (posY < height - 1   &&                          (dynamicRepresentation[posY + 1][posX    ] == '@')) sum++
        if (posY < height - 1   &&  posX > 0            &&  (dynamicRepresentation[posY + 1][posX - 1] == '@')) sum++
        if (                        posX > 0            &&  (dynamicRepresentation[posY    ][posX - 1] == '@')) sum++

        return sum
    }

    fun isPaperAt(y: Int, x: Int): Boolean {
        return dynamicRepresentation[y][x] == '@'
    }

    fun removeFrom(y:Int,x:Int){
        dynamicRepresentation[y][x] = 'x'
    }

    fun resetPosition(){
        posX = 0
        posY = 0
        vecNULL.copyInto(vec1)
        vecNULL.copyInto(vec2)
        vecNULL.copyInto(vec3)
        vec2[1] = gridMatrix[0][0] =='@'
        vec2[2] = gridMatrix[1][0] =='@'
        vec3[1] = gridMatrix[0][1] =='@'
        vec3[2] = gridMatrix[1][1] =='@'
    }
}


fun solve(lines: List<String>): Int {
    var sum = 0
    val b = BinMat(lines)

    var isGoingRight = true
    var removedPapers = 999
    while(removedPapers>0) {
        removedPapers=0
        for (row in 0..<b.height) {
            for (col in 0..<b.width - 1) {
                if (b.isPaperAt(b.posY, b.posX) && (b.getAmountOfSurroundingPapers() < 4)) {
                    sum += 1
                    removedPapers+=1
                    b.removeFrom(b.posY,b.posX)
                }

                if (isGoingRight) {
                    b.caretRight()
                    if (col == b.width - 2)
                        if (b.isPaperAt(b.posY, b.posX) && (b.getAmountOfSurroundingPapers() < 4)) {
                            sum += 1
                            removedPapers+=1
                            b.removeFrom(b.posY,b.posX)
                        }
                } else {
                    b.caretLeft()
                    if (col == b.width - 2)
                        if (b.isPaperAt(b.posY, b.posX) && (b.getAmountOfSurroundingPapers() < 4)) {
                            sum += 1
                            removedPapers+=1
                            b.removeFrom(b.posY,b.posX)
                        }

                }
            }
            isGoingRight = (!isGoingRight)
            b.caretDown()
        }
        println()
        for (line in b.dynamicRepresentation){
            for(char in line)
                print(char)
            println()
        }
        b.resetPosition()
    }

    return sum
}

fun main() {
    val exampleGrid =
            ("..@@.@@@@.\n" +
            "@@@.@.@.@@\n" +
            "@@@@@.@.@@\n" +
            "@.@@@@..@.\n" +
            "@@.@@@@.@@\n" +
            ".@@@@@@@.@\n" +
            ".@.@.@.@@@\n" +
            "@.@@@.@@@@\n" +
            ".@@@@@@@@.\n" +
            "@.@.@@@.@.")
                .split("\n")

    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t7_paper_roll_wall/input"
    val file = File(pathToFile)
    val fileReader = FileReader(file)
    val lines = fileReader.readLines()
    val res = solve(lines)
    println(res)

}
