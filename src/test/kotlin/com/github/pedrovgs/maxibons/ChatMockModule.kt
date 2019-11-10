package com.github.pedrovgs.maxibons

import arrow.Kind
import arrow.core.Option
import arrow.core.None
import arrow.core.Some
import arrow.fx.ForIO
import arrow.fx.IO

class ChatMockModule : Chat<ForIO> {

    var messageSent: Option<String> = None
    override fun sendMessage(message: String): Kind<ForIO, String> {
        messageSent = Some(message)
        return IO.just(message)
    }
}