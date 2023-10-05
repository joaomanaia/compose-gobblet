package core.utils.kotlin

operator fun <T> Iterable<T>.times(count: Int): List<T> = List(count) { this }.flatten()
