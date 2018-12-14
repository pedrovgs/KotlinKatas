package com.github.pedrovgs.maxibons

import arrow.Kind
import arrow.higherkind

@higherkind
interface Chat<F> {
    fun sendMessage(message: String): Kind<F, String>
}