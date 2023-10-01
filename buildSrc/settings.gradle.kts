import de.fayard.refreshVersions.bootstrapRefreshVersionsForBuildSrc

buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.60.3")
}

bootstrapRefreshVersionsForBuildSrc()