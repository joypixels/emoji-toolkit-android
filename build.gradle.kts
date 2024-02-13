buildscript {
    val agp_version by extra("8.0.2")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("maven-publish") apply true
}
