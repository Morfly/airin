//package com.morfly.airin.plugin.dsl.template
//
//import com.morfly.airin.GradleTemplateProvider
//import kotlin.reflect.KClass
//
//
//open class TemplatesConfiguration {
//
//    var binaryTargetName: String = "application"
//
//    inline fun <reified T : GradleTemplateProvider> register() {
//        register(T::class.java)
//    }
//
//    fun <T : GradleTemplateProvider> register(vararg templates: KClass<T>) {
//        register(templates.map { it.java })
//    }
//
//    fun <T: GradleTemplateProvider> register(vararg templates: Class<T>) {
//        register(templates.toList())
//    }
//
//    private fun <T: GradleTemplateProvider> register(templates: List<Class<T>>) {
//
//    }
//}