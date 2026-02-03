package su.kamil.dev.tasks.t16_jbox_playground_adv

import java.io.File
import java.io.FileReader
import kotlin.math.sqrt

// aka Junction Box - a box to connect inside it two or more wires and then hide it inside
data class JBox(val x: Int, val y: Int, val z: Int) {
    fun distance(other: JBox): Double {
        val dx = (x - other.x) + 0L
        val dy = (y - other.y) + 0L
        val dz = (z - other.z) + 0L
        return sqrt(dx * dx + dy * dy + dz * dz + 0.0)
    }

}

// single JBox creates single Circuit
// JBoxes, which connects between with wires creates a circuit
class Circuit(val jBoxes: List<JBox>) {
    constructor(singular: JBox) : this(listOf(singular))

    fun lowestDistance(other: Circuit): Double {
        return jBoxes.minOf { hostJBox ->
            other.jBoxes.minOf { it.distance(hostJBox) }
        }
    }
}

// connecting two circuits is done by creating new instance(and then leave prev two from accounting)
fun Circuit.mergeAndReturn(other: Circuit): Circuit {
    return Circuit(this.jBoxes.union(other.jBoxes).toList())


}

// goal is to make several connections with total shortest length of used wire
// hash to check result is true is: multiplication of sizes of the three largest circuits created
fun solve(lines: List<String>): Int {
    // first obvious solution:
    // 1. make singular circuits from input lines
    // 2. make a code block to find the shortest distance between each circuit
    // 3. do it one thousand times

    // actually, we need to make a list of shortest distance we ever found, bc we need a 1000 of it connected and
    //
    // second solution:
    // 1. count distance between each JBox and then put in single list
    // 2. this list, sorted will have solution in first thousand values
    // * note, its necessary to prevent self referring and cross-referring, e.g. do for(i in 0..N) for(j in i..N) is enough

    // in first solution for each new circuit we will do new computations
    // and some was performed before but not saved for future
    // second solution looks efficient, because we do only one forward iterating over given data
    // and then use sorted list to use all found data
    return solution(lines)

}
// don't work, bc heavily relies on circuit, which do not contain information about connections,
// and it's impossible to waste connection to connect JBs on same circuit,
// but seems like it must be possible

fun solution(lines: List<String>): Int {
    val jBoxes = mutableListOf<JBox>()
    // init
    for (line in lines) {
        val args = line.split(',').map { it.toInt() }
        val jBox = JBox(args[0], args[1], args[2])
        jBoxes.add((jBox))
    }
    // To save on string lights,
    // the Elves would like to focus on connecting
    // pairs of junction boxes that are
    // 1. as close together as possible according to straight-line distance.
    // 2. but aren't already directly connected
    val circuits = mutableListOf<Circuit>()
    // the idea is to create a matrix in which will be distances between each JB
    // distance between JBs i and j is where i<j
    // distArr[i][j]
    val distArr = Array<DoubleArray>(
        size = jBoxes.size - 1,
        init = { row ->
            DoubleArray(row + 1) { col ->
                //distance =
                jBoxes[row + 1].distance(jBoxes[col])
            }
        }
    )

    // Pair first - is row, second is column
    fun getNearestJBoxPairIndexes(): Pair<Int, Int> {
        var nearestDist = Double.MAX_VALUE
        var pair = Pair(1, 0)
        for (i in 0..<jBoxes.size - 1)
            for (j in 0..i) {
                if (nearestDist > distArr[i][j]) {
                    nearestDist = distArr[i][j]
                    pair = Pair(i + 1/*normalization*/, j)
                }
            }
        return pair
    }

    // do connection logic $maxConnections times
    while (true) {
        val pair = getNearestJBoxPairIndexes()
        val a = pair.first
        val b = pair.second

        println("$a\t$b\t${jBoxes[a].distance(jBoxes[b])}\t${jBoxes[a]}\t${jBoxes[b]}")
        distArr[a - 1/*denormalization*/][b] = Double.MAX_VALUE

        // null,    null    - create new
        // null,    exist   - merge
        // exist,   null    - merge
        // both exist and already in same circuit - then do nothing
        // exist,   exist   - merge and delete old
        var circuitA = null as Circuit?
        var circuitB = null as Circuit?
        for (circuit in circuits) {
            if (jBoxes[a] in circuit.jBoxes)
                circuitA = circuit
            if (jBoxes[b] in circuit.jBoxes)
                circuitB = circuit
        }

        if (circuitA == null && circuitB == null) {
            circuits.add(Circuit(listOf(jBoxes[a], jBoxes[b])))
        }
        else
        if (circuitA == null && circuitB != null) {
            circuits.remove(circuitB)
            circuits.add(circuitB.mergeAndReturn(Circuit(jBoxes[a])))

        }
        else
        if (circuitA != null && circuitB == null) {
            circuits.remove(circuitA)
            circuits.add(circuitA.mergeAndReturn(Circuit(jBoxes[b])))

        }
        else
        if (circuitA == circuitB)
        {
            if (circuits.size ==1)
            println("HADOOOKEN, ${circuits.size}")
        }
        else
        if (circuitA != null && circuitB != null) {
            circuits.remove(circuitA)
            circuits.remove(circuitB)
            circuits.add(circuitA.mergeAndReturn(circuitB))
        }
        else{
            println("WTF, ${circuits.size}")
        }


        if (circuits.isNotEmpty() && circuits[0].jBoxes.size == jBoxes.size) {
            if(circuits[0].jBoxes.toSet().size != jBoxes.size)
                println("SHIT")
            return jBoxes[a].x * jBoxes[b].x
        }
    }
}

fun main() {
//    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t15_jbox_playground/input"
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t15_jbox_playground/testInput"
    val file = File(pathToFile)
    val fileReader = FileReader(file)
    val lines = fileReader.readLines()
    val res = solve(lines)
    println(res)

}
