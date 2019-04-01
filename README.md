# <img alt="Kotlin" src="https://kotlinlang.org/assets/images/open-graph/kotlin_250x250.png" height="60"/> <img alt="Arrow" src="https://avatars0.githubusercontent.com/u/29458023?s=400&v=4" height="50"/> KotlinKatas [![Build Status](https://travis-ci.com/pedrovgs/KotlinKatas.svg?branch=master)](https://travis-ci.com/pedrovgs/KotlinKatas)

Kotlin training repository used to learn Kotlin and Functional Programming by solving some common katas using just purely functional programming with [Arrow](https://github.com/arrow-kt/arrow).

### List of katas:


| # | Kata Statement | PR | Topic |
|---|----------------|----|-------|
| 1 | [Maxibons](https://github.com/Karumi/MaxibonKataJava#-kata-maxibon-for-java-) | [https://github.com/pedrovgs/KotlinKatas/pull/1](https://github.com/pedrovgs/KotlinKatas/pull/1) | Polymorphic programming |
| 2 | [Form validation](https://gist.github.com/pedrovgs/d83fe1f096928715a6f31946e557995a) | [https://github.com/pedrovgs/KotlinKatas/pull/4](https://github.com/pedrovgs/KotlinKatas/pull/4) | Validated data type|

### Executing tests:

This project contains some tests written using [KotlinTest](https://github.com/kotlintest/kotlintest). You can easily run the tests by executing any of the following commands:

```
./gradlew test // Runs every test in your project
./gradlew test --tests *SomeSpecificTest // Runs specs matching with the filter passed as param.
```

### Checkstyle:

For the project checkstyle we are using [Ktlint](https://github.com/shyiko/ktlint). The code format will be evaluated after accepting any contribution to this repository using this tool. You can easily format your code changes automatically by executing ``./gradlew ktlinTformat``.

Developed By
------------

* Pedro Vicente Gómez Sánchez - <pedrovicente.gomez@gmail.com>

<a href="https://twitter.com/pedro_g_s">
  <img alt="Follow me on Twitter" src="https://image.freepik.com/iconos-gratis/twitter-logo_318-40209.jpg" height="60" width="60"/>
</a>
<a href="https://es.linkedin.com/in/pedrovgs">
  <img alt="Add me to Linkedin" src="https://image.freepik.com/iconos-gratis/boton-del-logotipo-linkedin_318-84979.png" height="60" width="60"/>
</a>

License
-------

    Copyright 2019 Pedro Vicente Gómez Sánchez

    Licensed under the GNU General Public License, Version 3 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.gnu.org/licenses/gpl-3.0.en.html

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
