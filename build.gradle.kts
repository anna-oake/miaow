import gg.meza.stonecraft.mod
import org.gradle.api.tasks.bundling.AbstractArchiveTask

plugins {
    id("gg.meza.stonecraft")
}

val compatibilityTest = providers.gradleProperty("compatibilityTest").isPresent
val minecraftVersion = property("minecraft_version").toString()
val loaderName = name.substringAfterLast("-")
val minecraftRangeName = name.removeSuffix("-$loaderName")
val releaseJarName = "miaow-${property("mod.version")}-$loaderName-$minecraftRangeName.jar"
val loaderSourceRoot = rootProject.file("src/$loaderName/java")
val processedLoaderSourceRoot = layout.buildDirectory.dir("generated/stonecutter/$loaderName")
loaderSourceRoot.walkTopDown()
    .filter { it.isFile && it.extension == "java" }
    .forEach { source ->
        val relativePath = source.relativeTo(loaderSourceRoot).invariantSeparatorsPath
        stonecutter.process(source, "build/generated/stonecutter/$loaderName/$relativePath")
    }

sourceSets.main {
    java.srcDir(processedLoaderSourceRoot)
    resources.srcDir(rootProject.file("src/$loaderName/resources"))
}

modSettings {
    variableReplacements = mapOf(
        "authors" to "anna-oake",
        "homepage" to "https://modrinth.com/mod/miaow",
        "sources" to "https://github.com/anna-oake/miaow",
        "license" to "MIT",
        "loaderVersion" to property("loader_version").toString(),
        "javaVersion" to if (minecraftVersion.startsWith("26.")) "25" else "21",
        "fabricMinecraftRange" to if (loaderName != "fabric") {
            ""
        } else if (compatibilityTest) {
            ">=$minecraftVersion"
        } else {
            property("fabric_minecraft_range").toString()
        },
        "neoForgeMinecraftRange" to if (loaderName != "neoforge") {
            ""
        } else if (compatibilityTest) {
            "[$minecraftVersion,)"
        } else {
            property("neoforge_minecraft_range").toString()
        }
    )
}

tasks.withType<Jar>().configureEach {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${rootProject.name}" }
    }
}

if (minecraftVersion.startsWith("26.")) {
    tasks.named<Jar>("jar") {
        archiveFileName.set(releaseJarName)
    }
} else {
    tasks.named<AbstractArchiveTask>("remapJar") {
        archiveFileName.set(releaseJarName)
    }
}
