package su.kamil.dev.tasks.t10_fresh_goods_advanced

import su.kamil.dev.tasks.t4_database_incident_advanced.Range
import java.io.File
import java.io.FileReader
import kotlin.math.max
import kotlin.math.min

fun Range.powerOfRange(): Long{
    return this.rightBorder-this.leftBorder+1
}

fun Range.isCollides(other: Range): Boolean {
    return (other.numberIn(this.leftBorder))||(other.numberIn(this.rightBorder))
}

fun Range.merge(other: Range): Range{
    return Range(min(this.leftBorder,other.leftBorder),max(this.rightBorder,other.rightBorder))
}

class RangeMerger(val ranges: MutableList<Range>){

    fun processMerging(){
        var flag = true
        var index = 0
        do{
            var merged = null as Range?
            for (k in 0..<ranges.size)
                if (ranges[k].isCollides(ranges[index]) && k!=index)
                {
                    merged = ranges[k].merge(ranges[index])
                    ranges.removeAt(max(k,index))
                    ranges.removeAt(min(k,index))
                    ranges.add(merged)
                    index--
                    break
                }
            index++
            if(index>=ranges.size)
                flag=false
        }while (flag)

    }

    fun getFreshies(): Long {
        var freshies = 0L
        for (range in ranges)
            freshies += range.powerOfRange()
        return freshies
    }
}

fun solution(ranges: MutableList<Range>): Long{

    val merger = RangeMerger(ranges)
    merger.processMerging()


    return merger.getFreshies()
}

fun main(){
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t9_fresh_goods/input"

    val file = File(pathToFile)
    val fileReader = FileReader(file)
    val lines = fileReader.readLines()
    val ranges = mutableListOf<Range>()
    for (line in lines){
        val splitRes = line.split('-')
        if(splitRes.size==2){
            ranges.add(Range(splitRes[0].toLong(),splitRes[1].toLong()))
        }
        else{
            break
        }

    }

    val res = solution(ranges)
    println(res)
    println(416834422689187)
}