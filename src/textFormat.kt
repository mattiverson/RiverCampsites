import java.io.BufferedReader
import java.io.FileReader
import java.util.*

/**
 * Created by Matt on 6/15/2018.
 */

fun main(args: Array<String>) {
    val reader = BufferedReader(FileReader("src/result.txt"))
    val strings = ArrayList<String>()
    while(reader.ready()) strings.add(reader.readLine())
    val stops = strings.dropLast(1)
            .map { it.substring(16) }
            .map { it.substring(0, it.length - 1) }
            .map { it.split(", ") }
            .flatMap { it.map { Integer.parseInt(it) }}
//            .reduce { l1, l2 ->  listOf(*l1.toTypedArray(), *l2.toTypedArray())}
    val sortedStops = stops.toMutableList().sorted()
    val uniqueStops = sortedStops.distinct()
    val doubleStops = sortedStops.filterIndexed { idx, value -> idx > 0 && sortedStops[idx-1] == value }
    println(sortedStops)
    println(uniqueStops)
    println(doubleStops)
    println("unique: 22-443 except ${(22..443).minus(uniqueStops)}")
    println("double: 22-443 except ${(22..443).minus(doubleStops)}")
}