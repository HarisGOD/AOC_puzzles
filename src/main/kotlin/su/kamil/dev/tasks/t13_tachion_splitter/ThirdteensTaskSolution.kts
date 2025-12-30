
import java.io.File
import java.io.FileReader
import kotlin.math.cbrt

fun solve(lines:List<String>): Long {
	var ctbp = mutableListOf<Int>(lines[0].indexOf('S')) // current tachions beams positions
    var splitTotal = 0L
	for(li in 1..<lines.size){
        println(li)
		val line = lines[li]
        val ntbp = mutableSetOf<Int>()
        for (tb in ctbp){
            if(line[tb]=='^') {
                splitTotal+=1
                ntbp.add(tb+1)
                ntbp.add(tb-1)
            }
            else{
                ntbp.add(tb)
                //single
            }
        }
        ctbp=ntbp.toMutableList()

	}
	
	
	
	return splitTotal
}


fun main(){
    val pathToFile = "input"
    val file = File(pathToFile)
    val fileReader = FileReader(file)
    val lines = fileReader.readLines()

    val res = solve(lines)

    println(res)
}


main()
