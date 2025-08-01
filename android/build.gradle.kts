buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
//        classpath("com.google.gms:google-services:4.4.2") // ✅ Firebase
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0") // ✅ Kotlin
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Optional: Customize build directory location
val newBuildDir = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.set(newBuildDir)

subprojects {
    val newSubprojectBuildDir = newBuildDir.dir(project.name)
    project.layout.buildDirectory.set(newSubprojectBuildDir)
}

subprojects {
    project.evaluationDependsOn(":app")
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}






