package su.kamil.dev.su.kamil.dev.t4_database_incident_advanced

import org.junit.jupiter.api.Test
import su.kamil.dev.tasks.t3_database_incident.pow
import su.kamil.dev.tasks.t4_database_incident_advanced.Range
import su.kamil.dev.tasks.t4_database_incident_advanced.generatePatternedValues
import su.kamil.dev.tasks.t4_database_incident_advanced.generateRepeatedNumber
import su.kamil.dev.tasks.t4_database_incident_advanced.solve
import su.kamil.dev.tasks.t4_database_incident_advanced.splitSets
import kotlin.math.floor
import kotlin.math.log10
import kotlin.test.assertEquals


class FourthTaskSolutionGeneratingTest {

    @Test
    fun testSolution4(){

        val line = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
        val borders = line.split(",")
        val ranges = borders.map { Range(
            it.split('-')[0].toLong(),
            it.split('-')[1].toLong())
        }
        val res = solve(ranges)
        val exp = 4174379265L

        assertEquals(exp,res)


    }
    @Test
    fun testRanges(){
        val ranges = listOf<Range>(
            Range(10,100),Range(109,1000009))
        val set = mutableSetOf<Range>().apply {
            splitSets(ranges,this)
        }
        // fixme: proper assertion
        println(set)

    }

    @Test
    fun generatePatternsTest(){
        val range = Range(1003,5006)
        val divisor = 2
        val rangePowerP1 = 4
        val res = generatePatternedValues(divisor,range,rangePowerP1)
        println(res)

    }

    @Test
    fun generateRepeatedNumberTest(){

        val pattern = 1
        val times = 3
        val powerPattern = 0 // powerOf(pattern)

        val res = generateRepeatedNumber(pattern,times,powerPattern+1)
        println(res)
    }

    @Test
    fun temp(){
        val i = 619654619654
        val t = 100000000000

        val power = floor(log10(i.toDouble())).toInt()
        if (power % 2 == 1) {
            val mask = 10L.pow(power/2)*10+1
            println(i%mask)
        }


    }
}