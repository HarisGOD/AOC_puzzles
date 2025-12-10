package su.kamil.dev.tasks.t5_max_joltage

import java.io.File
import java.io.FileReader

fun getMaxPair(line:String): Int{

    var max1 = '0'
    var max2 = '0'

    for(i in 0..<line.length-1 ) {
        if(line[i]>max1)
        {
            max1=line[i]
            max2=line[i+1]
        }
        else{
            if(line[i]>max2)
                max2=line[i]
        }
        if(line.last()>max2)
            max2=line.last()
    }
    return (max1.code-48)*10+(max2.code-48)
}

fun solve(lines:List<String>): Long{

    var sum = 0L
    for(line in lines){
        sum+=getMaxPair(line)
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