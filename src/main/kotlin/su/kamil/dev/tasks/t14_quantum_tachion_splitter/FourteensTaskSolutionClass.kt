package su.kamil.dev.tasks.t14_quantum_tachion_splitter

import java.io.File
import java.io.FileReader
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch

class FourteensTaskSolutionClass {

    val cache = List<MutableList<Long?>>(143,{MutableList<Long?>(143,{null})})

    fun quadimensionalSplitTotal(tbp: Int, li: Int, lines: List<String>): Long {
        if(li==lines.size-1)
            return 0L

        if (cache[li][tbp]!=null)
            return cache[li][tbp]!!


        if (lines[li+1][tbp]=='^'){
            val res = quadimensionalSplitTotal(tbp - 1, li + 1, lines) + quadimensionalSplitTotal(tbp + 1, li + 1, lines)+1
            cache[li][tbp]=res
            return res
        }
        else{
            val res = quadimensionalSplitTotal(tbp,li+1,lines)
            cache[li][tbp]=res
            return res
        }
    }

    fun solve(lines: List<String>): Long {
        val tbp = lines[0].indexOf('S')


//        Thread.sleep(10000)

        return quadimensionalSplitTotal(tbp,0,lines)
    }







}

fun main() {
    val pathToFile = "input"
    val file = File(pathToFile)
    val fileReader = FileReader(file)
    val lines = fileReader.readLines()
    val s = FourteensTaskSolutionClass()
    val res = s.solve(lines)

    println("res:${res}, but actual answer is res+1:${res+1} is strange, " +
            "i think long and have no idea why my code isnt work, then i did some test and got magic +1")
}