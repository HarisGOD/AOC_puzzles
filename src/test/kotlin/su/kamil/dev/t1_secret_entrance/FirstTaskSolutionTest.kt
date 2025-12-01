package su.kamil.dev.su.kamil.dev.t1_secret_entrance

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import su.kamil.dev.tasks.t1_secret_entrance.countZeros

class FirstTaskSolutionTest {



    @Test
    fun testCounter() {

        val lines = listOf(
            "R50",
            "L50",
            "R50",
            "L50",
            "R50"
        )


        // dial is numbers 0..99
        val res = countZeros(lines)
        assertEquals(3,res)
    }
}