import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21";
    id("org.openjfx.javafxplugin") version("0.0.10");
}


group = "org.pattern"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "18"
    modules("javafx.controls", "javafx.graphics", "javafx.fxml", "javafx.media")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.32")
    implementation("com.github.almasb:fxgl:17.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}