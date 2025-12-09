package su.kamil.dev.su.kamil.dev.t3_database_incident

import org.junit.jupiter.api.Test
import su.kamil.dev.tasks.t3_database_incident.pow
import su.kamil.dev.tasks.t3_database_incident.solve
import kotlin.math.floor
import kotlin.math.log10
import kotlin.test.assertEquals

class ThirdTaskSolutionTest {

    @Test
    fun testSolution3(){

        val line = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
        val borders = line.split(",")
        val pairs = borders.map { it.split('-') }
        val res = solve(pairs)
        val exp = 1227775554L

        assertEquals(exp,res)

    }
    @Test
    fun testPow(){
        assertEquals(1L,10L.pow(0))
        assertEquals(10L,10L.pow(1))
        assertEquals(100L,10L.pow(2))
        assertEquals(1000L,10L.pow(3))
        assertEquals(10000L,10L.pow(4))


    }
}