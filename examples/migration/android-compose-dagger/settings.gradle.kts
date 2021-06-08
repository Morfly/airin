dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "android-compose-dagger"
include(":app")
include(":core")
include(":data:api")
include(":data:impl")
include(":image-list:api")
include(":image-list:impl")
