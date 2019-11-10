package com.github.pedrovgs.maxibons

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.fx.IO
import arrow.fx.extensions.io.async.async
import arrow.fx.extensions.io.monad.monad
import arrow.fx.fix
import com.github.pedrovgs.maxibons.KarumiHQs.KarumiHQs.maxibonsToRefill
import com.github.pedrovgs.maxibons.KarumiHQs.KarumiHQs.minNumberOfMaxibons
import com.github.pedrovgs.maxibons.Generators.DeveloperGenerator
import com.github.pedrovgs.maxibons.Generators.HungryDeveloperGenerator
import com.github.pedrovgs.maxibons.Generators.NotSoHungryDeveloperGenerator
import com.github.pedrovgs.maxibons.Generators.WorldGenerator
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class MaxibonsSpec : StringSpec({

    "always have more than 2 maxibons" {
        forAll(WorldGenerator(), DeveloperGenerator()) { world, developer ->
            val maxibonsLeft = openFridge(world, developer).karumiFridge.maxibonsLeft
            maxibonsLeft > 2
        }
    }

    "always have more than 2 maxibons  even if they go in groups" {
        forAll(WorldGenerator(), Gen.list(DeveloperGenerator())) { world, developers ->
            val maxibonsLeft = openFridge(world, developers).karumiFridge.maxibonsLeft
            maxibonsLeft > 2
        }
    }

    "ask for more maxibons using the chat client if we need to refill" {
        forAll(HungryDeveloperGenerator()) { developer ->
            val messageSent = openFridgeAndGetChatMessageSent(World(), developer)
            messageSent == Some("Hi there! I'm ${developer.name}. We need more maxibons")
        }
    }

    "not ask for more maxibons using the chat client if we don't need to refill" {
        forAll(NotSoHungryDeveloperGenerator()) { developer ->
            val messageSent = openFridgeAndGetChatMessageSent(World(), developer)
            messageSent == None
        }
    }

    "left the office with 6 maxibons left if Pedro and Manolo go to the fridge" {
        val world = World()
        val fridge = openFridge(world, listOf(Karumies.pedro, Karumies.manolo)).karumiFridge
        fridge.maxibonsLeft shouldBe 6
    }

    "use 2 as the min number of maxibons" {
        minNumberOfMaxibons shouldBe 2
    }
    "use 10 as the number of maxibons to refill when needed" {
        maxibonsToRefill shouldBe 10
    }
})

private fun openFridge(world: World, developers: List<Developer>): World {
    val chat = ChatMockModule()
    val karumiHQs = KarumiHQs(chat, IO.monad(), IO.async())
    return karumiHQs.openFridge(world, developers)
            .fix().unsafeRunSync()
}

private fun openFridgeAndGetChatMessageSent(world: World, developer: Developer): Option<String> {
    val chat = ChatMockModule()
    val karumiHQs = KarumiHQs(chat, IO.monad(), IO.async())
    karumiHQs.openFridge(world, developer)
    return chat.messageSent
}

private fun openFridge(world: World, developer: Developer): World =
        openFridge(world, listOf(developer))