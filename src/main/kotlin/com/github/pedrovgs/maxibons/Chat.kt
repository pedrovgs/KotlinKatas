package com.github.pedrovgs.maxibons

import arrow.Kind

interface Chat<F> {
    fun sendMessage(message: String): Kind<F, String>
}