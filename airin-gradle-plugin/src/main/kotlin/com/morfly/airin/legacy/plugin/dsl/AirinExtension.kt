//package com.morfly.airin.plugin.dsl
//
//import com.morfly.airin.legacy.ArtifactMappingConfiguration
//import com.morfly.airin.legacy.ArtifactsConfiguration
//import com.morfly.airin.legacy.BazeliskConfiguration
//import com.morfly.airin.legacy.Constrainable
//import com.morfly.airin.legacy.ConstraintsConfiguration
//import com.morfly.airin.plugin.dsl.template.TemplatesConfiguration
//import org.gradle.api.Action
//import org.gradle.api.model.ObjectFactory
//import javax.inject.Inject
//
//
//open class AirinExtension @Inject constructor(
//    objectFactory: ObjectFactory
//) : Constrainable {
//
//    override var constraints = objectFactory.newInstance(ConstraintsConfiguration::class.java)
//    var templates = objectFactory.newInstance(TemplatesConfiguration::class.java)
//    var artifacts = objectFactory.newInstance(ArtifactsConfiguration::class.java)
//    var bazelisk = objectFactory.newInstance(BazeliskConfiguration::class.java)
//
//    override fun constraints(action: Action<ConstraintsConfiguration>) {
//        action.execute(constraints)
//    }
//
//    fun templates(action: Action<TemplatesConfiguration>) {
//        action.execute(templates)
//    }
//
//    fun artifacts(action: Action<ArtifactsConfiguration>) {
//        action.execute(artifacts)
//    }
//
//    fun bazelisk(action: Action<BazeliskConfiguration>) {
//        action.execute(bazelisk)
//    }
//
//    companion object {
//        const val NAME = "airin"
//    }
//}