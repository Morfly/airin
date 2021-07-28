package org.morfly.sample

import android.os.Bundle


interface Route {
    val args: Bundle?
}

interface Navigator {

    fun navigateTo(route: Route)
}