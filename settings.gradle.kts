import java.util.Properties

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.neoforged.net/releases/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("gg.meza.stonecraft") version "1.10.+"
    id("dev.kikugie.stonecutter") version "0.9.+"
}

fun Properties.loaders() = getProperty("loaders").split(",").map(String::trim)

val versionGroups = rootDir.resolve("versions/dependencies")
    .listFiles { file -> file.extension == "properties" }
    .orEmpty()
    .map { file ->
        file.nameWithoutExtension to Properties().apply {
            file.inputStream().use(::load)
        }
    }
    .sortedBy { (range, _) ->
        range.substringBefore("-").split(".").joinToString("") { it.padStart(4, '0') }
    }

gradle.beforeProject {
    val loader = name.substringAfterLast("-")
    val (range, properties) = versionGroups
        .find { (range, _) -> name == "$range-$loader" }
        ?: return@beforeProject

    extensions.extraProperties.set("minecraft_version", range.substringBefore("-"))
    properties.forEach { key, value ->
        extensions.extraProperties.set(key.toString(), value.toString())
    }
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true

    shared {
        versionGroups.forEach { (range, properties) ->
            properties.loaders().forEach { loader ->
                version("$range-$loader", range.substringBefore("-"))
            }
        }

        val (latestRange, latestProperties) = versionGroups.last()
        val latestLoaders = latestProperties.loaders()
        val vcsLoader = latestLoaders.find { it == "fabric" } ?: latestLoaders.first()
        vcsVersion = "$latestRange-$vcsLoader"
    }

    create(rootProject)
}

rootProject.name = "miaow"
