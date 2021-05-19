//package com.morfly.airin.lib
//
//import org.morfly.bazelgen.generator.file.BazelFile
//
//
//internal class RootTemplateProvider<T>(providers: List<TemplateProvider<T>>) : TemplateProvider<T>() {
//
//    private var firstTemplateProvider = initialize(providers.asReversed().iterator())
//
////    private fun buildChain(providers: List<TemplateProvider<T>>): TemplateProvider<T>? {
////        val root = providers.lastOrNull()
////        for (provider in providers.asReversed()) {
////
////        }
////        return root
////    }
//
//    fun initialize(iterator: Iterator<TemplateProvider<T>>): TemplateProvider<T>? {
//        val root = if (iterator.hasNext()) iterator.next() else null
//
//        //        if(iterator.hasNext())
////        if(iterator.hasNext()) iterator.next()
////        else null
//        fun TemplateProvider<T>.initialize(): TemplateProvider<T>? {
//            nextProvider = if (iterator.hasNext()) iterator.next().initialize() else null
//            return nextProvider
//        }
//        return root?.initialize()
//    }
//
//
//    override fun nextProvider(root: T): TemplateProvider<T>? =
//        firstTemplateProvider?.nextProvider(root)
//
//    override fun canProvide(target: T): Boolean =
//        firstTemplateProvider?.canProvide(target) ?: false
//
//    override fun provideTemplates(target: T): List<BazelFile> =
//        firstTemplateProvider?.provideTemplates(target) ?: emptyList()
//}