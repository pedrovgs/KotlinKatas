package com.github.pedrovgs.maxibons

import arrow.Kind
import arrow.core.extensions.list.foldable.foldM
import arrow.typeclasses.ApplicativeError
import arrow.typeclasses.Monad
import kotlin.math.max

class KarumiHQs<F>(
    private val chat: Chat<F>,
    private val M: Monad<F>,
    AE: ApplicativeError<F, Throwable>
) : ApplicativeError<F, Throwable> by AE {

    companion object KarumiHQs {
        const val maxibonsToRefill: Int = 10
        const val minNumberOfMaxibons: Int = 2
    }

    fun openFridge(world: World, developers: List<Developer>): Kind<F, World> =
            developers.foldM(M, world, this::openFridge)

    fun openFridge(world: World, developer: Developer): Kind<F, World> =
            refillMaxibonsIfNeeded(developer, computeMaxibonsLeft(world, developer)).map {
                World.worldMaxibonsLeft.set(world, it)
            }

    private fun computeMaxibonsLeft(world: World, developer: Developer): Int =
            max(0, world.karumiFridge.maxibonsLeft - developer.maxibonsToGrab)

    private fun refillMaxibonsIfNeeded(developer: Developer, maxibonsLeft: Int): Kind<F, Int> =
            if (shouldRefillMaxibons(maxibonsLeft)) {
                askTheTeamForMoreMaxibons(developer).map {
                    maxibonsLeft + maxibonsToRefill
                }
            } else {
                just(maxibonsLeft)
            }

    private fun shouldRefillMaxibons(maxibonsLeft: Int) = maxibonsLeft <= minNumberOfMaxibons

    private fun askTheTeamForMoreMaxibons(developer: Developer): Kind<F, String> =
            chat.sendMessage("Hi there! I'm ${developer.name}. We need more maxibons")
}