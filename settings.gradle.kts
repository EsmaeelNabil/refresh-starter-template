import de.fayard.refreshVersions.bootstrapRefreshVersions

buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.60.3")
}

bootstrapRefreshVersions()

include(":app")
rootProject.name = "new-culture"
