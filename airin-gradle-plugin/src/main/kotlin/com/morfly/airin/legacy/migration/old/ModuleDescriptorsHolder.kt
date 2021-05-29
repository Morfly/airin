package com.morfly.airin.legacy.migration.old

//import org.gradle.api.Project
//import org.morfly.bazelgen.generator.file.BazelFile
//
//
//class ModuleDescriptorsHolder(descriptors: List<ModuleDescriptor> = emptyList()) {
//
//    private val descriptors = ArrayDeque(descriptors)
//
//    fun add(descriptor: ModuleDescriptor) {
//        descriptors.addFirst(descriptor)
//    }
//
//    fun describe(project: Project): BazelFile? =
//        descriptors
//            .firstOrNull { it.canDescribe(project) }
//            ?.describe(project)
//}