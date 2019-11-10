package com.github.pedrovgs.datamunging

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.fix
import arrow.mtl.Kleisli

private val optionStringToIntKleisli = Kleisli { str: String ->
    if (str.toCharArray().all { it.isDigit() }) Some(str.toInt()) else None
}
private val optionStringToDoubleKleisli = Kleisli { str: String ->
    if (str.toCharArray().all { it.isDigit() }) Some(str.toDouble()) else None
}

fun String.safeToInt(): Option<Int> {
    return optionStringToIntKleisli.run(this).fix()
}

fun String.safeToDouble(): Option<Double> {
    return optionStringToDoubleKleisli.run(this).fix()
}