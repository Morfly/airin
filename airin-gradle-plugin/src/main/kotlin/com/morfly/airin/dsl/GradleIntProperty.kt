package com.morfly.airin.dsl

import org.gradle.api.Project
import kotlin.reflect.KProperty


//internal class GradleIntProperty<T>(
//    project: Project,
//    default: Int? = null
//) {
//    val property = project.objects.property(Integer::class.java).apply {
//        set(default as? Integer)
//    }
//
//    operator fun getValue(thisRef: T, property: KProperty<*>): Int =
//        this.property.get().toInt()
//
//    operator fun setValue(thisRef: T, property: KProperty<*>, value: Int) =
//        this.property.set(value as? Integer)
//}