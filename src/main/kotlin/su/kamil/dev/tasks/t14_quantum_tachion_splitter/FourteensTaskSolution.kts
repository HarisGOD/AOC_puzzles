import java.io.File
import java.io.FileReader
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.asJavaAtomic
import kotlin.concurrent.atomics.incrementAndFetch

@OptIn(ExperimentalAtomicApi::class)
var splitTotal = AtomicLong(0)

@OptIn(ExperimentalAtomicApi::class)
fun quadimensionalSplitTotal(tbp: Int, li: Int, lines: List<String>) {
    if(li==lines.size-1)
        return

    if (lines[li+1][tbp]=='^'){
        splitTotal.incrementAndFetch()
        Thread({ quadimensionalSplitTotal(tbp - 1, li + 1, lines) }).start()
        Thread({ quadimensionalSplitTotal(tbp + 1, li, lines) }).start()


    }
    else{
        return(quadimensionalSplitTotal(tbp,li+1,lines))
    }
}

@OptIn(ExperimentalAtomicApi::class)
fun solve(lines: List<String>): Long {
    val tbp = lines[0].indexOf('S')
    quadimensionalSplitTotal(tbp,0,lines)


    return splitTotal.load()
}
// WTF multiprocessing isnt for me
// I got heap error
// I find out that idk how to increase heap in .kts
// I find out then that I did recursive call for same function
// but test setup already appeared and its better for solve hard cases

fun main() {
    val pathToFile = "input"
    val file = File(pathToFile)
    val fileReader = FileReader(file)
    val lines = fileReader.readLines()

    val res = solve(lines)

    println(res)
}


main()
