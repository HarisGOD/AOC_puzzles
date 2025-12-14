// no package

import java.io.File
import java.io.FileReader

fun solve(numbers:List<List<Long>>,operations:List<Char>):Long{
	var total = 0L
	for(i in 0..<operations.size){
		var sum = if (operations[i]=='+')0L else 1L
		//		println(numbers.size)
		for(j in 0..<numbers.size){
			if(operations[i]=='+'){
			sum+=numbers[j][i]
			}
			else{
			sum*=numbers[j][i]
			}							
		}
		total+=sum
	}
	
	return total
}

fun main(){
	val pathToFile = "input"
	val file = File(pathToFile)
	val fileReader = FileReader(file)
	val lines = fileReader.readLines()
	val operations = lines.last().split(" ")
        .filter {it.isNotEmpty()}
        .map { it[0] }

	val numbers = lines.subList(0,lines.size-1)
        .map{
		line ->
            line.split(" ")
                .filter{it.isNotEmpty()}
                .map { element ->
                    element.toLong()
                }
	}
	
	val res = solve(numbers,operations)
	println(res)
}


main()
