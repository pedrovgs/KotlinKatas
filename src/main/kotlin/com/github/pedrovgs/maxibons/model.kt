package com.github.pedrovgs.maxibons

data class Developer internal constructor(val name: String = "", private val numberOfMaxibons: Int = 0) {
    val maxibonsToGrab: Int = Math.max(0, numberOfMaxibons)
}

data class KarumiFridge(private val numberOfMaxibons: Int = 10) {
    val maxibonsLeft: Int = Math.min(12, Math.max(3, numberOfMaxibons))
}

data class World(val karumiFridge: KarumiFridge = KarumiFridge())

object Karumies {
    val pedro = Developer("Pedro", 3)
    val sergio = Developer("Sergio Gutierrez", 2)
    val tylos = Developer("Tylos", 1)
    val juanjo = Developer("Juanjo", 0)
    val sarroyo = Developer("Sergio Arroyo", 5)
    val manolo = Developer("Manolo", 1)
}
