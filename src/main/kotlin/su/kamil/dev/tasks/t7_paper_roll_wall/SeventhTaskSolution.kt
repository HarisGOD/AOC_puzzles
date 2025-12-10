package su.kamil.dev.tasks.t7_paper_roll_wall


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
    val vec2 = BooleanArray(3)
    val vec3 = BooleanArray(3)
    val vecNULL = BooleanArray(3, { false })
    val height = gridMatrix.size
    val width = gridMatrix.get(0).length
    var posX = 0
    var posY = 0

    // for all caret moves, we know, that they were not go deeper than limits of grid(maximum go inside border to 1 pos)
    fun caretRight() {
        if(posX==width-1) throw Exception("You trying move caret to far(right) form border(${width-1})")
        vec2.copyInto(vec1)
        vec3.copyInto(vec2)
        if (posX <= width) {
            posX++

            vec3[0] = (posY > 0) && isPaperAt(posY - 1, posX)
            vec3[1] = isPaperAt(posY, posX)
            vec3[2] = (posY < height) && isPaperAt(posY + 1, posX)
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
            vec1[0] = (posY>0)&&isPaperAt(posY - 1, posX)
            vec1[1] = isPaperAt(posY, posX)
            vec1[2] = (posY < height) &&isPaperAt(posY + 1, posX)
        } else {
            vecNULL.copyInto(vec1)
        }
    }

    fun caretDown() {
        TODO() // assert that it right

        vec1[0] = vec1[1]
        vec1[1] = vec1[2]

        vec2[0] = vec2[1]
        vec2[1] = vec2[2]

        vec3[0] = vec3[1]
        vec3[1] = vec3[2]



        if (posY > height - 1) {
            vec1[2] = false
            vec2[2] = false
            vec3[2] = false
        } else {
            posY++
            vec1[2] = isPaperAt(posY, posX - 1)
            vec2[2] = isPaperAt(posY, posX)
            vec3[2] = isPaperAt(posY, posX + 1)
        }

    }

    fun getCurrentValue(): Int {
        var sum = 0
        // go in circle
        if (posY > 0            &&  posX > 0            &&  (gridMatrix[posY - 1][posX - 1] == '@')) sum++
        if (posY > 0            &&                          (gridMatrix[posY - 1][posX    ] == '@')) sum++
        if (posY > 0            &&  posX < width - 1    &&  (gridMatrix[posY - 1][posX + 1] == '@')) sum++
        if (                        posX < width - 1    &&  (gridMatrix[posY    ][posX + 1] == '@')) sum++

        if (posY < height - 1   &&  posX < width - 1    &&  (gridMatrix[posY + 1][posX + 1] == '@')) sum++
        if (posY < height - 1   &&                          (gridMatrix[posY + 1][posX    ] == '@')) sum++
        if (posY < height       &&  posX > 0            &&  (gridMatrix[posY + 1][posX - 1] == '@')) sum++
        if (                        posX > 0            &&  (gridMatrix[posY    ][posX - 1] == '@')) sum++

        return sum
    }

    fun isPaperAt(y: Int, x: Int): Boolean {
        return gridMatrix[y][x] == '@'
    }
}


fun solve(lines: List<String>) {

}

fun main() {

    val b = BinMat(
        listOf(
            "..@@@",
            "..@..",
            "..@..",
            "..@..",
        )
    )

    println(b.getCurrentValue())
    b.caretRight()
    println(b.getCurrentValue())
    b.caretRight()
    println(b.getCurrentValue())
    b.caretRight()
    println(b.getCurrentValue())
    b.caretRight()
    println(b.getCurrentValue())
    println("in other direction:")
    println(b.getCurrentValue())
    b.caretLeft()
    println(b.getCurrentValue())
    b.caretLeft()
    println(b.getCurrentValue())
    b.caretLeft()
    println(b.getCurrentValue())
    b.caretLeft()
    println(b.getCurrentValue())
}
