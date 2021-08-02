package org.morfly.airin

import org.morfly.airin.BazelLabelFormat.FullRepo
import org.morfly.airin.BazelLabelFormat.FullRepoOmitsTarget


/**
 *
 */
interface BazelLabeled {

    /**
     *
     */
    val defaultLabelFormat: BazelLabelFormat

    /**
     *
     */
    fun bazelLabel(format: BazelLabelFormat = defaultLabelFormat): String
}

/**
 *
 */
sealed interface BazelLabelFormat {

    /**
     * @myrepo//my/app/main:app_binary
     */
    @JvmInline
    value class FullRepo(val repoName: String) : BazelLabelFormat

    /**
     * @myrepo//my/app/main
     */
    @JvmInline
    value class FullRepoOmitsTarget(val repoName: String) : BazelLabelFormat

    /**
     * //my/app/main:app_binary
     */
    object Full : BazelLabelFormat

    /**
     * //my/app/main
     */
    object FullOmitsTarget : BazelLabelFormat

    /**
     * :app_binary
     */
    object ShortColon : BazelLabelFormat

    /**
     * app_binary
     */
    object Short : BazelLabelFormat
}


/**
 *
 */
val BazelLabelFormat.repoName: String?
    get() = when (this) {
        is FullRepo -> repoName
        is FullRepoOmitsTarget -> repoName
        else -> null
    }

/**
 *
 */
fun Collection<BazelLabeled>.bazelLabels(format: BazelLabelFormat? = null): List<String> =
    map { it.bazelLabel(format ?: it.defaultLabelFormat) }

/**
 *
 */
fun Sequence<BazelLabeled>.bazelLabels(format: BazelLabelFormat? = null): Sequence<String> =
    map { it.bazelLabel(format ?: it.defaultLabelFormat) }