buildscript {
    val agp_version by extra("7.3.1")
    val agp_version1 by extra("8.2.2")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("maven-publish") apply true
}
