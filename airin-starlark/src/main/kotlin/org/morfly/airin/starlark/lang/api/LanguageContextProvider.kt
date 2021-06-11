package org.morfly.airin.starlark.lang.api


/**
 * Defines that a class implementing this interface must be able to provide new instances of a specified language context.
 */
internal interface LanguageContextProvider<out C : LanguageContext> {

    /**
     * Provides new instance of a language context.
     */
    fun newContext(): C
}