import org.apache.tools.ant.filters.FixCrLfFilter
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    kotlin("jvm") version "1.8.0"
}

group = "moe.xmcn.catsero"
version = "2.3.1"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
    maven("https://crystal.app.lss233.com/repositories/minecraft")
    maven("https://jitpack.io")
}

dependencies {
    // 本地
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // 远程
    implementation("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.1-20220531.173326-1")
    implementation("me.clip:placeholderapi:2.11.2")
    implementation("io.github.dreamvoid:MiraiMC-Bukkit:1.7.1")
    implementation("com.alibaba:fastjson:2.0.23")
    implementation("com.github.CroaBeast:AdvancementInfo:2.0.2")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("me.zhenxin:qqbot-sdk:1.2.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation(kotlin("test"))
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.compileJava {
    options.encoding = "UTF-8"
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}
tasks.compileTestJava {
    options.encoding = "UTF-8"
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}
tasks.compileKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}
tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

// 替换资源文件的Tokens`config.groovy`
fun loadEnv(): Any {
    val configFile = file("config.groovy")
    val env = "prod"
    val confProp = groovy.util.ConfigSlurper(env).parse(configFile.toURL())
    //config.groovy配置文件,配置占位符 (@key@)
    val tokens = confProp.toProperties()
    logger.lifecycle("Tokens: $tokens")
    return tokens;
}

fun delFiles(dir: String) {
    val configs = fileTree(dir)
    for (f in configs) {
        f.delete()
    }
}

tasks.processResources {
    delFiles("$buildDir/resources")
    filteringCharset = "UTF-8"
    filter(ReplaceTokens::class, "tokens" to loadEnv())
    filter(
        FixCrLfFilter::class,
        "eol" to FixCrLfFilter.CrLf.newInstance("lf"),
        "tab" to FixCrLfFilter.AddAsisRemove.newInstance("asis"),
        "tablength" to 4,
        "eof" to FixCrLfFilter.AddAsisRemove.newInstance("remove"),
        "fixlast" to true
    )

}

tasks.create<Jar>("fatJar") {
    setDuplicatesStrategy(DuplicatesStrategy.FAIL)
    val sourceMain = java.sourceSets["main"]
    from(sourceMain.output)

    configurations.runtimeClasspath.filter {
        it.name.startsWith("fastjson") or
                it.name.startsWith("AdvancementInfo") or
                it.name.startsWith("HikariCP")
    }.forEach { jar ->
        from(zipTree(jar))
    }
}

//build命令依赖的其他命令
tasks.build {
    dependsOn(
        tasks.processResources,
        "fatJar"
    )
}
