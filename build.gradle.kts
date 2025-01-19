// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false // This is fine for project-level declaration
    alias(libs.plugins.kotlin.android) apply false // This is fine for project-level declaration
    id("com.google.dagger.hilt.android") version "2.48.1" apply false // Apply false for Hilt here
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false // Apply false for KSP here
    alias(libs.plugins.google.gms.google.services) apply false // Apply false for google services in project-level
}