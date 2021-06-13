package org.morfly.airin.sample.profile.impl.di

import dagger.Component
import org.morfly.airin.sample.profile.ProfileEntryProvider


@Component(modules = [ProfileEntryModule::class])
interface ProfileEntryComponent : ProfileEntryProvider