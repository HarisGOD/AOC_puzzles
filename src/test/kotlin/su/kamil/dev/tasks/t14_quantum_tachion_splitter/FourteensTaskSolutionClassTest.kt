package su.kamil.dev.tasks.t14_quantum_tachion_splitter

import java.io.File
import java.io.FileReader
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch
import kotlin.test.Test

class FourteensTaskSolutionClassTest {
    @Test
    fun test() {
        val pathToFile = "/home/thegod/JAVA/PROJECTS/AOC_puzzles/src/main/kotlin/su/kamil/dev/tasks/t14_quantum_tachion_splitter/input"
        val file = File(pathToFile)
        val fileReader = FileReader(file)
        val lines = fileReader.readLines()
//        val lines = (".......S.......\n" +
//                "...............\n" +
//                ".......^.......\n" +
//                "...............\n" +
//                "......^.^......\n" +
//                "...............\n" +
//                ".....^.^.^.....\n" +
//                "...............\n" +
//                "....^.^...^....\n" +
//                "...............\n" +
//                "...^.^...^.^...\n" +
//                "...............\n" +
//                "..^...^.....^..\n" +
//                "...............\n" +
//                ".^.^.^.^.^...^.\n" +
//                "...............").split("\n")

        val s = FourteensTaskSolutionClass()
        val res = s.solve(lines)

        println(res)
    }




}