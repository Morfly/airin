package org.morfly.airin.sample.profile

import androidx.compose.runtime.compositionLocalOf


interface ProfileProvider


val LocalProfileProvider = compositionLocalOf<ProfileProvider> {
    error("No profile provider found!")
}