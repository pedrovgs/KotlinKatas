package com.github.pedrovgs.maxibons

import arrow.core.Id
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.instances.io.monad.monad
import arrow.instances.id.monad.monad
import com.github.pedrovgs.maxibons.KarumiHQs.KarumiHQs.maxibonsToRefill
import com.github.pedrovgs.maxibons.KarumiHQs.KarumiHQs.minNumberOfMaxibons
import com.github.pedrovgs.maxibons.Generators.DeveloperGenerator
import com.github.pedrovgs.maxibons.Generators.HungryDeveloperGenerator
import com.github.pedrovgs.maxibons.Generators.NotSoHungryDeveloperGenerator
import com.github.pedrovgs.maxibons.Generators.WorldGenerator
import com.github.pedrovgs.maxibons.interpreters.SlackModule
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
        forAll(WorldGenerator(), Gen.list(DeveloperGenerator())) { world, developers: List<Developer> ->
            val maxibonsLeft = openFridge(world, developers).karumiFridge.maxibonsLeft
            maxibonsLeft > 2
        }
    }

    "ask for more maxibons using the chat client if we need to refill" {
        forAll(HungryDeveloperGenerator()) { developer: Developer ->
            val messageSent = openFridgeAndGetChatMessageSent(World(), developer)
            messageSent == Some("Hi there! I'm ${developer.name}. We need more maxibons")
        }
    }

    "not ask for more maxibons using the chat client if we don't need to refill" {
        forAll(NotSoHungryDeveloperGenerator()) { developer: Developer ->
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
    val karumiHQs = KarumiHQs(IO.monad(), SlackModule())
    return karumiHQs.openFridge(world, developers)
            .fix().unsafeRunSync()
}

private fun openFridgeAndGetChatMessageSent(world: World, developer: Developer): Option<String> {
    val chat = ChatMockModule()
    val karumiHQs = KarumiHQs(Id.monad(), chat)
    karumiHQs.openFridge(world, developer)
    return chat.messageSent
}

private fun openFridge(world: World, developer: Developer): World =
        openFridge(world, listOf(developer))