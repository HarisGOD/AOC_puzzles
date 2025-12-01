package su.kamil.dev.su.kamil.dev.t2_secret_entrance

import java.io.File
import java.io.FileInputStream
import java.io.FileReader

// 1. "Due to newer security protocols, please use password method 0x434C49434B until further notice."
//      You remember from the training seminar that "method 0x434C49434B"
//      means you're actually supposed to count the number of times any click causes
//      the dial to point at 0, regardless of whether it happens during a rotation or at the end of one.


fun main() {
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t1_secret_entrance/input"
    val file = File(pathToFile)
    val fileInputStream = FileInputStream(file)
    val reader = FileReader(file)
    val lines = reader.readLines()


    // dial is numbers 0..99
    val res = countClicks(lines)
    println(res)
}

fun countClicks(lines: List<String>): Int {
    var dial = 50
    var zeros = 0


    lines.forEach { line ->
        val direction = if (line[0] == 'R') 1 else -1
        val distance = line.removeRange(0, 1).toInt()
        val curDial = dial
        for(i in 0..<distance){
            dial = (dial+direction).mod(100)
            if (dial==0) zeros++
        }
    }

    return(zeros)
}


