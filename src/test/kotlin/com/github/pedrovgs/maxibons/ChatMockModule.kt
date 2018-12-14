package com.github.pedrovgs.maxibons

import arrow.Kind
import arrow.core.ForId
import arrow.core.Option
import arrow.core.None
import arrow.core.Some
import arrow.core.Id

class ChatMockModule : Chat<ForId> {

    var messageSent: Option<String> = None
    override fun sendMessage(message: String): Kind<ForId, String> {
        messageSent = Some(message)
        return Id.just(message)
    }
}