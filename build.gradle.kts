// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()  // Add the Google repository
        mavenCentral()  // Optional, add this if needed
    }
    dependencies {
        // Correct AGP version classpath
        classpath("com.android.tools.build:gradle:8.7.2")  // Use parentheses instead of single quotes
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

}