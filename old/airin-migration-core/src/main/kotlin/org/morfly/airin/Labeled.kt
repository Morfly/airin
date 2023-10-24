package org.morfly.airin


/**
 *
 */
interface Labeled {

    /**
     *
     */
    val label: String

    /**
     *
     */
    val shortLabel: String
}


/**
 *
 */
fun Collection<Labeled>.labels(): List<String> =
    map { it.label }

/**
 *
 */
fun Sequence<Labeled>.labels(): Sequence<String> =
    map { it.label }

/**
 *
 */
fun Collection<Labeled>.shortLabels(): List<String> =
    map { it.shortLabel }

/**
 *
 */
fun Sequence<Labeled>.shortLabels(): Sequence<String> =
    map { it.shortLabel }