package su.kamil.dev.su.kamil.dev.t2_secret_entrance

import org.junit.jupiter.api.Test
import su.kamil.dev.tasks.t2_secret_entrance.countClicks
import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import kotlin.test.assertEquals

// 1. "Due to newer security protocols, please use password method 0x434C49434B until further notice."
//      You remember from the training seminar that "method 0x434C49434B"
//      means you're actually supposed to count the number of times any click causes
//      the dial to point at 0, regardless of whether it happens during a rotation or at the end of one.

class SecondTaskSolutionTest {

    @Test
    fun testClicksCounter() {
        val lines = listOf(
            "R50",
            "L111",
            "R99"
        )


        val res = countClicks(lines)
        assertEquals(3,res)
    }


}