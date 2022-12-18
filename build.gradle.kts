plugins {
    id("java")
}

group = "moe.xmcn.catsero"
version = "2.0-beta5"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
    maven("https://lss233.littleservice.cn/repositories/minecraft")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.0")
    implementation("me.clip:placeholderapi:2.11.2")
    implementation("io.github.dreamvoid:MiraiMC-Bukkit:1.7.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}