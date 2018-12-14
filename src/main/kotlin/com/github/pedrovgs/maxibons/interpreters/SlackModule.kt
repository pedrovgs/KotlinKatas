package com.github.pedrovgs.maxibons.interpreters

import arrow.Kind
import arrow.effects.ForIO
import arrow.effects.IO
import com.github.pedrovgs.maxibons.Chat

class SlackModule : Chat<ForIO> {
    override fun sendMessage(message: String): Kind<ForIO, String> = IO {
        println(message)
        message
    }
}