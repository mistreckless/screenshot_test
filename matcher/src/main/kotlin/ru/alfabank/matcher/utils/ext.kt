package ru.alfabank.matcher.utils

import reactor.util.function.Tuple2


operator fun <T> Tuple2<T, *>.component1(): T = t1

operator fun <T> Tuple2<*, T>.component2(): T = t2
