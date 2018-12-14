package com.github.pedrovgs.maxibons

import arrow.Kind
import arrow.instances.list.foldable.foldM
import arrow.typeclasses.Monad
import arrow.typeclasses.binding

class KarumiHQs<F>(private val M: Monad<F>, private val chat: Chat<F>) {

    companion object KarumiHQs {
        const val maxibonsToRefill: Int = 10
        const val minNumberOfMaxibons: Int = 2
    }

    fun openFridge(world: World, developers: List<Developer>): Kind<F, World> =
            developers.foldM(M, world, this::openFridge)

    fun openFridge(world: World, developer: Developer): Kind<F, World> {
        return M.binding {
            val finalMaxibonsAmount = refillMaxibonsIfNeeded(developer, computeMaxibonsLeft(world, developer)).bind()
            World(KarumiFridge(finalMaxibonsAmount))
        }
    }

    private fun computeMaxibonsLeft(world: World, developer: Developer): Int =
            Math.max(0, world.karumiFridge.maxibonsLeft - developer.maxibonsToGrab)

    private fun refillMaxibonsIfNeeded(developer: Developer, maxibonsLeft: Int): Kind<F, Int> {
        return if (shouldRefillMaxibons(maxibonsLeft)) {
            M.binding {
                askTheTeamForMoreMaxibons(developer).bind()
                maxibonsLeft + maxibonsToRefill
            }
        } else {
            M.just(maxibonsLeft)
        }
    }

    private fun shouldRefillMaxibons(maxibonsLeft: Int) = maxibonsLeft <= minNumberOfMaxibons

    private fun askTheTeamForMoreMaxibons(developer: Developer): Kind<F, String> {
        return chat.sendMessage("Hi there! I'm ${developer.name}. We need more maxibons")
    }
}