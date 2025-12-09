package su.kamil.dev.tasks.t3_database_incident

import java.io.File
import java.io.FileReader
import kotlin.math.floor
import kotlin.math.log10

fun Long.pow(power: Int): Long{
    var newV = 1L
    for (i in 0..<power)
        newV*=this
    return newV
}

fun Int.pow(power: Int): Int{
    var newV = 1
    for (i in 0..<power)
        newV*=this
    return newV
}



fun findSumFromRange(left: Long,right: Long): Long{
    var sum = 0L

    var i = left
    while(i<=right) {
        val power = floor(log10(i.toDouble())).toInt()
        if (power % 2 == 1) {
            val mask = 10L.pow(power/2)*10+1
            if (i%mask == 0L) {
                sum += i
                println(i)
            }
        }
        i++
    }

    return sum
}

fun solve(pairs:List<List<String>>): Long {
    return pairs.sumOf { findSumFromRange(it[0].toLong(),it[1].toLong()) }
}

fun main() {
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t3_database_incident/input"
    val file = File(pathToFile)
    val reader = FileReader(file)

    val lines = reader.readLines()
    val borders = lines[0].split(",")
    val pairs = borders.map { it.split('-') }
    val res = solve(pairs)
    println(res)

}