import java.util.*

/**
 * Created by Matt on 6/15/2018.
 */

val riverLength = 225.0
val stops = Array<Int>(2* riverLength.toInt()) {0}
val tripLengths = 6..18
val dayLengths = tripLengths.map { riverLength /(it+1)}

var totalTrips = 0

fun main(args:Array<String>) {
    while(true) {
        val currentOptions = dayLengths.toMutableList()
        while (!currentOptions.isEmpty()) {
//            val tripDayLength = currentOptions.first()
//            val tripDayLength = currentOptions.last()
            val tripDayLength = currentOptions[(currentOptions.size * Math.random()).toInt()]
            val tripStops = tryTripStep(tripDayLength, 4.0, 0)
            if (tripStops.all { it >= 0 } && tripStops.size <= tripLengths.last) {
                tripStops.reverse()
                tripStops.remove(0)
                println("found ${tripStops.size} night trip: $tripStops")
                tripStops.forEach { stops[it]++ }
                totalTrips++
                break
            } else {
                currentOptions.remove(tripDayLength)
            }
        }
        if (currentOptions.isEmpty()) break
    }
    println("found $totalTrips trips using ${stops.sum()} stops")
}

fun tryTripStep(targetLength: Double, eps: Double, start: Int): MutableList<Int> {
    if (targetLength + eps + start/2 >= riverLength) return arrayListOf(start)
    var i = (start + 2*targetLength).toInt()
    val newStop = offsetList(eps)
            .map { it+i }
            .filter {it < (2*riverLength.toInt())}
            .filter { stops[it] < 2 }
            .firstOrNull { tryTripStep(targetLength, eps, it).all { it > 0 } }
            ?: return arrayListOf(-1)
    val newList = tryTripStep(targetLength, eps, newStop)
    newList.add(start)
    return newList
}

fun offsetList(eps: Double): List<Int> {
    val ret = arrayListOf(0)
    for(i in 1..(2*eps).toInt()) {
        ret.add(-i)
        ret.add(i)
    }
    return ret
}