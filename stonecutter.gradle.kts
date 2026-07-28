import org.gradle.api.tasks.Sync

plugins {
    id("dev.kikugie.stonecutter")
    id("gg.meza.stonecraft")
}

stonecutter active "26.1-26.2-fabric" /* [SC] DO NOT EDIT */

val collectDist by tasks.registering(Sync::class) {
    group = "build"
    into(layout.projectDirectory.dir("dist"))

    val modVersion = providers.gradleProperty("mod.version").get()
    subprojects.forEach { versionProject ->
        val loader = versionProject.name.substringAfterLast("-")
        val minecraftRange = versionProject.name.removeSuffix("-$loader")
        dependsOn("${versionProject.path}:build")
        from(
            versionProject.layout.buildDirectory.file(
                "libs/miaow-$modVersion-$loader-$minecraftRange.jar",
            ),
        )
    }
}

tasks.register("build") {
    group = "build"
    dependsOn(collectDist)
}
