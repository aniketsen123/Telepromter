// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        maven { url =uri("https://maven.fabric.io/public") }
            mavenCentral()
            jcenter()
        }

    dependencies {
        classpath ("com.android.tools.build:gradle:3.2.1")
        // Check for v3.1.2 or higher
        classpath ("com.google.gms:google-services:4.4.2")


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    } }


plugins {
    id("com.android.application") version "8.1.2" apply false
    id ("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}