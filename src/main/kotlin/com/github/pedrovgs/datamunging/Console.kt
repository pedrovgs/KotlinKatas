package com.github.pedrovgs.datamunging

import arrow.fx.IO

interface Console {
    fun log(str: String): IO<String>
}