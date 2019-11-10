package com.github.pedrovgs.datamunging

import arrow.core.some
import com.github.pedrovgs.datamunging.interpreter.ResourcesFileReader
import com.github.pedrovgs.datamunging.interpreter.StdConsole
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class WeatherExtractorSpec : StringSpec({
    "returns the day 14 as the coldest day" {
        val day = WeatherExtractor(CsvFileReader(ResourcesFileReader()), StdConsole())
                .findDayWithSmallestTemperature("src/resources/weather.dat")
                .unsafeRunSync()

        day shouldBe WeatherInfoForDay(dayOfMonth = 30, minTemp = 45.0, maxTemp = 90.0).some()
    }
})