package su.kamil.dev.tasks.t1_secret_entrance

import java.io.File
import java.io.FileInputStream
import java.io.FileReader

// Lore:
//  1. A document taped to the wall helpfully explains:
//      "Due to new security protocols, the password is locked in the safe below.
//      Please see the attached document for the new combination."
//
//  2. The safe has a dial with only an arrow on it;
//      around the dial are the numbers 0 through 99 in order.
//      As you turn the dial, it makes a small click noise as it reaches each number.
//
//  3. The attached document (your puzzle input) contains a sequence of rotations,
//      one per line, which tell you how to open the safe. A rotation starts with an L or R
//      which indicates whether the rotation should be to the left (toward lower numbers)
//      or to the right (toward higher numbers). Then, the rotation has a distance value
//      which indicates how many clicks the dial should be rotated in that direction.
//
//  4. The dial starts by pointing at 50.
//
//  5. You could follow the instructions, but your recent required official North Pole secret entrance security
//      training seminar taught you that the safe is actually a decoy.
//      The actual password is the number of times the dial is left pointing at 0
//      after any rotation in the sequence.
//

fun main() {
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t1_secret_entrance/input"
    val file = File(pathToFile)
    val fileInputStream = FileInputStream(file)
    val reader = FileReader(file)
    val lines = reader.readLines()


    // dial is numbers 0..99
    val res = countZeros(lines)
    println(res)
}

fun countZeros(lines: List<String>): Int {
    var dial = 50
    var zeros = 0


    lines.forEach { line ->
        val direction = if (line[0] == 'R') 1 else -1
        val distance = line.removeRange(0, 1).toInt()
        dial = (dial + direction * distance).mod(100)
        if (dial == 0) zeros++
        if (distance == 0) println("ATTENTION")
    }

    return(zeros)
}
