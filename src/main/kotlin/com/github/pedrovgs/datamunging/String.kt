package com.github.pedrovgs.datamunging

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

fun String.safeToInt(): Option<Int> =
        if (this.isNotEmpty() && this.toCharArray().all { it.isDigit() }) Some(this.toInt()) else None

fun String.safeToDouble(): Option<Double> =
        if (this.isNotEmpty() && this.toCharArray().all { it.isDigit() }) Some(this.toDouble()) else None