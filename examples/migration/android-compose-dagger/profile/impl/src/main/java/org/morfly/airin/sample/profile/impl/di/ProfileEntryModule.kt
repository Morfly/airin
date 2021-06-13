package org.morfly.airin.sample.profile.impl.di

import dagger.Binds
import dagger.Module
import org.morfly.airin.sample.profile.ProfileEntry
import org.morfly.airin.sample.profile.impl.ProfileEntryImpl


@Module
interface ProfileEntryModule {

    @Binds
    fun profileEntry(impl: ProfileEntryImpl): ProfileEntry
}