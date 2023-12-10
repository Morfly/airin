# Airin

## Overview

```kotlin
// root build.gradle.kts
plugins {
    id("io.morfly.airin.android") version "x.y.z"
}
```

```kotlin
// root build.gradle.kts
airin {
    targets += setOf(":app")

    register<AndroidLibraryModule> {
        include<JetpackComposeFeature>()
        include<HiltFeature>()
        include<ParcelizeFeature>()
    }
    register<JvmLibraryModule>()

    register<RootModule> {
        include<AndroidToolchainFeature>()
    }
}
```

```shell
./gradlew app:migrateToBazel --no-configure-on-demand
```