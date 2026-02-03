package su.kamil.dev.tasks.t15_jbox_playground

import java.io.File
import java.io.FileReader
import kotlin.math.min
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
fun solve(lines: List<String>, maxConnections: Int = 1000): Int {
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
    return solution2(lines, maxConnections)

}
// don't work, bc heavily relies on circuit, which do not contain information about connections,
// and it's impossible to waste connection to connect JBs on same circuit,
// but seems like it must be possible
fun solution1(lines: List<String>, maxConnections: Int): Int {
    val circuits = mutableListOf<Circuit>()
    // init
    for (line in lines) {
        val args = line.split(',').map { it.toInt() }
        val jBox = JBox(args[0], args[1], args[2])
        circuits.add(Circuit(jBox))
    }

    // do connection work
    for (i in 0..<maxConnections) {
        // check, if all jboxes already connected with some other, then result is done
        if(circuits.size<=maxConnections/2+1) {
            var isAllConnected = true
            for (circuit in circuits) {
                if(circuit.jBoxes.size==1){
                    isAllConnected=false
                    break
                }
            }
            if(isAllConnected){
                return circuits[0].jBoxes.size * circuits[1].jBoxes.size * circuits[2].jBoxes.size
            }
        }

        var merge1 = null as Circuit?
        var merge2 = null as Circuit?
        var minDist = Double.MAX_VALUE
        for (i in 0..<circuits.size)
//            if(circuits[i].jBoxes.size>1)
//                continue
            for (j in 0..<circuits.size) {
                if(j==i) continue


                val current1 = circuits[i]
                val current2 = circuits[j]
                val curMinDist = current1.lowestDistance(current2)
                if (curMinDist < minDist) {
                    minDist = curMinDist
                    merge1 = circuits[i]
                    merge2 = circuits[j]
                }
            }

        if (merge1==null || merge2==null) break
        val merged = merge1.mergeAndReturn(merge2)
        circuits.remove(merge1)
        circuits.remove(merge2)
        circuits.add(merged)
    }

    circuits.sortWith(Comparator { c1, c2 -> c2.jBoxes.size - c1.jBoxes.size })
    println(circuits)
    return circuits[0].jBoxes.size * circuits[1].jBoxes.size * circuits[2].jBoxes.size
}

fun solution2(lines: List<String>, maxConnections: Int):Int{
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
    val distArr = Array<DoubleArray> (
        size = jBoxes.size-1,
        init = { row->
            DoubleArray(row+1){ col->
                //distance =
                jBoxes[row+1].distance(jBoxes[col])
            }
        }
    )
    // Pair first - is row, second is column
    fun getNearestJBoxPairIndexes(): Pair<Int,Int>{
        var nearestDist = Double.MAX_VALUE
        var pair = Pair(0,0)
        for(i in 0..<jBoxes.size-1)
            for (j in 0..i){
                if(nearestDist>distArr[i][j]) {
                    nearestDist =  distArr[i][j]
                    pair = Pair(i+1,j)
                }
            }
        return pair
    }

    // do connection logic $maxConnections times
    for(i in 0..<maxConnections){
        val pair = getNearestJBoxPairIndexes()
        val a = pair.first
        val b = pair.second
//        println("$a,$b")
        distArr[a-1/*normalization*/][b]= Double.MAX_VALUE

        // null,    null    - create new
        // null,    exist   - merge
        // exist,   null    - merge
        // both exist and already in same circuit - then do nothing
        // exist,   exist   - merge and delete old
        var circuitA = null as Circuit?
        var circuitB = null as Circuit?
        for(circuit in circuits){
            if (jBoxes[a] in circuit.jBoxes)
                circuitA = circuit
            if (jBoxes[b] in circuit.jBoxes)
                circuitB = circuit
        }

        if (circuitA==null && circuitB==null){
            circuits.add(Circuit(listOf(jBoxes[a],jBoxes[b])))
            continue
        }
        if (circuitA==null && circuitB !=null){
                circuits.remove(circuitB)
                circuits.add(circuitB.mergeAndReturn(Circuit(jBoxes[a])))
            continue
            }
        if (circuitA!=null && circuitB==null){
            circuits.remove(circuitA)
            circuits.add(circuitA.mergeAndReturn(Circuit(jBoxes[b])))
            continue
        }
        if (circuitA==circuitB && circuitA!=null) continue
        if (circuitA!=null && circuitB!=null){
            circuits.remove(circuitA)
            circuits.remove(circuitB)
            circuits.add(circuitA.mergeAndReturn(circuitB))
            continue
        }

    }

    circuits.sortWith(Comparator { c1, c2 -> c2.jBoxes.size - c1.jBoxes.size })
    println(circuits)
    return circuits[0].jBoxes.size * circuits[1].jBoxes.size * circuits[2].jBoxes.size
}

fun main() {
    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t15_jbox_playground/input"
//    val pathToFile = "src/main/kotlin/su/kamil/dev/tasks/t15_jbox_playground/testInput"
    val file = File(pathToFile)
    val fileReader = FileReader(file)
    val lines = fileReader.readLines()
    val res = solve(lines,1000)
    println(res)

}
