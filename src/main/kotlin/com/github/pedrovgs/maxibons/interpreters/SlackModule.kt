package com.github.pedrovgs.maxibons.interpreters

import arrow.Kind
import arrow.core.right
import arrow.fx.typeclasses.Async
import com.github.pedrovgs.maxibons.Chat

class SlackModule<F>(A: Async<F>) : Chat<F>, Async<F> by A {
    override fun sendMessage(message: String): Kind<F, String> = async { callback ->
        println(message)
        callback(message.right())
    }
}