package org.morfly.airin.sample.core.di.lib

import dagger.MapKey
import org.morfly.airin.sample.core.FeatureEntry
import kotlin.reflect.KClass


@MapKey
annotation class FeatureEntryKey(val value: KClass<out FeatureEntry>)