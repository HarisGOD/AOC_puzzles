package su.kamil.dev.tasks.t9_fresh_goods

import su.kamil.dev.tasks.t4_database_incident_advanced.Range
import java.io.File
import java.io.FileReader

suspend fun hello(){
    println("Hello")
}

class FoodSpoilingDetector(val ranges:List<Range>){
    var currentRangeIndex = 0

    fun isFoodFresh(number: Long): Boolean {
        for(i in 0..<ranges.size) {
            if (ranges[currentRangeIndex].numberIn(number)) {
                return true
            }
            else{
                incrementIndex()
            }
        }
        return false
    }

    fun incrementIndex(){
        currentRangeIndex = (currentRangeIndex + 1) % ranges.size
    }
}

fun solution(ranges: List<Range>,ids: List<Long>): Long{
    var freshies = 0L
    val detector = FoodSpoilingDetector(ranges)

    for (id in ids){
        if (detector.isFoodFresh(id)){
            freshies+=1
        }
    }

    return freshies
}

fun main(){
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t9_fresh_goods/input"

    val file = File(pathToFile)
    val fileReader = FileReader(file)
    val lines = fileReader.readLines()
    val ranges = mutableListOf<Range>()
    val ids = mutableListOf<Long>()
    for (line in lines){
        val splitRes = line.split('-')
        if(splitRes.size==2){
            ranges.add(Range(splitRes[0].toLong(),splitRes[1].toLong()))
        }
        else{
            if (line.isEmpty())
                continue
            else{
                ids.add(line.toLong())
            }
        }

    }

    val res = solution(ranges,ids)
    println(res)
}