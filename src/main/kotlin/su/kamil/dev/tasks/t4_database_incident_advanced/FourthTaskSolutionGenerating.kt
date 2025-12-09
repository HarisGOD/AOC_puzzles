package su.kamil.dev.tasks.t4_database_incident_advanced

import su.kamil.dev.tasks.t3_database_incident.pow
import java.io.File
import java.io.FileReader
import kotlin.math.floor
import kotlin.math.log10

// some number N = ({a...z}*p_times)*k_times - where a...z is 0...9
// for number N, which decimal length L = k*p;
// possible decimal patterns may have length of divisors for L
// except number itself - it cant be the pattern's L
// and 1 - it's edge case


// idea is to generate ID which is matches requirements for each range
// e.g. borders is 5000 - 6000, primes for length of number is 4, patterns maybe made from numbers: 2
// other case is 50-500, my way is to split it into two different cases, bc patterns are more applicable so
// 50-99 and 100-500
// all number we put in set
fun getDivisors(n: Int): List<Int> {
    if (n <= 2) return listOf(1)  // No divisors besides 1 and itself

    val divisors = mutableListOf<Int>()

    for (i in 1 until n) {
        if (n % i == 0) {
            divisors.add(i)
        }
    }

    return divisors
}

// Example: divisors of 12 -> [2, 3, 4, 6]
data class Range(val leftBorder: Long, val rightBorder: Long) {
    fun numberIn(number: Long): Boolean {
        return (leftBorder <= number) && (number <= rightBorder)
    }


}

fun solve(ranges: List<Range>): Long {
    // for each range:
    //  split range and add in set
    val limitedRangesSet = mutableSetOf<Range>().apply {
        splitSets(ranges, this)
    }

    var setOfPossibleWrongIDs = setOf<Long>()
    // .....
    // then:
    for (range in limitedRangesSet) {
        // for each range in set:
        //  get divisors for number's power + 1 (number 1234: power+1 are 4: divs are: 2)
        val rangePowerP1 = getPowerOf(range.leftBorder) + 1
        val divisors = getDivisors(rangePowerP1)
        for (divisor in divisors) {
            //  for each divisor:
            //      find all patterned values with its divisor
            setOfPossibleWrongIDs = setOfPossibleWrongIDs.union(generatePatternedValues(divisor, range, rangePowerP1))
        }
    }
    println(setOfPossibleWrongIDs.max())
    return setOfPossibleWrongIDs.sum()
}

//      generate numbers which matching patterns
//      leftBorder - left part of number
//      rightBorder - left part of number
// e.g. 180000 ... 999999 -> ( divisors for 6:
//      all possible patterns with length 2: from 18 to 99
//      all possible patterns with length 3: from
fun generatePatternedValues(divisor: Int, range: Range, rangePowerP1: Int): Set<Long> {
    val set = mutableSetOf<Long>()
    var leftBorderPattern = (range.leftBorder / 10L.pow(rangePowerP1 - divisor)).toInt()
    var rightBorderPattern = (range.rightBorder / 10L.pow(rangePowerP1 - divisor)).toInt()
    // some sort of normalization values

    val times = rangePowerP1/divisor
    if(generateRepeatedNumber(leftBorderPattern,times,divisor)<range.leftBorder)
        leftBorderPattern +=1
    if(generateRepeatedNumber(rightBorderPattern,times,divisor)>range.rightBorder)
        rightBorderPattern -=1

    for(i in leftBorderPattern..rightBorderPattern){
        set.add(generateRepeatedNumber(i,times,divisor))
    }

    return set
}

fun generateRepeatedNumber(pattern: Int, times: Int, powerPatternP1: Int): Long {
    if(times == 1 || times == 0) return 0
    var num = pattern.toLong()
    val mul10 = 10L.pow(powerPatternP1)
    for(i in 0..<times-1)
    {
        num*=mul10
        num+=pattern
    }
    return num
}

fun splitSets(ranges: List<Range>, limitedRangesSet: MutableSet<Range>) {
    for (range in ranges) {
        splitSetRecursively(range, limitedRangesSet)
    }
}

private fun splitSetRecursively(range: Range, limitedRangesSet: MutableSet<Range>) {
    val powerLeft = getPowerOf(range.leftBorder)
    val powerRight = getPowerOf(range.rightBorder)
    if (powerLeft < powerRight) {
        val delimiter = 10L.pow(powerLeft + 1)
        splitSetRecursively(Range(range.leftBorder, delimiter - 1), limitedRangesSet)
        splitSetRecursively(Range(delimiter, range.rightBorder), limitedRangesSet)
    } else {
        limitedRangesSet.add(range)
    }
}

// fixme maybe efficiency issue cause uses math functions
private fun getPowerOf(number: Long): Int = floor(log10(number.toDouble())).toInt()

fun main() {
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t3_database_incident/input"
    val file = File(pathToFile)
    val reader = FileReader(file)

    val lines = reader.readLines()
    val borders = lines[0].split(",")
    val pairs = borders.map {
        Range(
            it.split('-')[0].toLong(),
            it.split('-')[1].toLong()
        )
    }
    val res = solve(pairs)
    println(res)

}