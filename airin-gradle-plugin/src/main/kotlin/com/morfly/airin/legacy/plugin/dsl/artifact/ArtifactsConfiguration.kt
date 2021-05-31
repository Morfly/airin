package com.morfly.airin.legacy.plugin.dsl.artifact

import com.morfly.airin.legacy.plugin.dsl.constraint.Constrainable
import com.morfly.airin.legacy.plugin.dsl.constraint.ConstraintsConfiguration
import org.gradle.api.Action


open class ArtifactsConfiguration : Constrainable {

    fun ignore(vararg artifacts: String) {

    }

    fun value(arg: Any): String {
        return ""
    }

    override var constraints = ConstraintsConfiguration()

    override fun constraints(action: Action<ConstraintsConfiguration>) {
    }


    infix fun CharSequence.replaceWith(bazel: CharSequence) {

    }

    infix fun CharSequence.replaceWith(bazel: List<CharSequence>) {

    }

    infix fun List<CharSequence>.replaceWith(bazel: CharSequence) {

    }

    infix fun List<CharSequence>.replaceWith(bazel: List<CharSequence>) {

    }


    infix fun CharSequence.replaceAnyWith(bazel: CharSequence) {

    }

    infix fun CharSequence.replaceAnyWith(bazel: List<CharSequence>) {

    }

    infix fun List<CharSequence>.replaceAnyWith(bazel: CharSequence) {

    }

    infix fun List<CharSequence>.replaceAnyWith(bazel: List<CharSequence>) {

    }

}

