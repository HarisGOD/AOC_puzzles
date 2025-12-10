package su.kamil.dev.tasks.t6_max_joltage_advanced

import java.io.File
import java.io.FileReader


fun getMaxStringSequenceFrom(line: String, sequenceSize: Int): String {
    if (sequenceSize==0) return ""
    var maxChar = '0'
    var maxCharPos = -1

    for(i in 0..line.length-sequenceSize){
        if(line[i]>maxChar){
            maxChar = line[i]
            maxCharPos = i
        }
    }



    return maxChar+getMaxStringSequenceFrom(line.substring(maxCharPos+1),sequenceSize-1)
}

fun getMaxSeqNumber(line: String): Long{
    return getMaxStringSequenceFrom(line,12).toLong()
}

fun solve(lines: List<String>): Long {

    var sum = 0L
    for(line in lines){
        sum+=getMaxSeqNumber(line)
    }
    return sum


}

fun main() {
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t5_max_joltage/input"
    val file = File(pathToFile)
    val reader = FileReader(file)

    val testI = "987654321111111\n" +
            "811111111111119\n" +
            "234234234234278\n" +
            "818181911112111"
    val lines = reader.readLines()

    val res = solve(lines)
    println(res)

}
