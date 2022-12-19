import org.apache.tools.ant.filters.FixCrLfFilter
import org.apache.tools.ant.filters.ReplaceTokens

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
    implementation("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.0")
    implementation("me.clip:placeholderapi:2.11.2")
    implementation("io.github.dreamvoid:MiraiMC-Bukkit:1.7.1")
    implementation("com.alibaba:fastjson:2.0.21")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.compileJava {
    options.encoding = "UTF-8"
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

task<Copy>("processShell") {
    //必须先删除原sh目录下文件后重新生成,不然使用gradle build -Penv参数切换环境时,无法替换占位符变量
    delFiles("$buildDir/sh")
    from("src/main/sh") {
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
    into("$buildDir/sh")
}

tasks.create<Jar>("fatJar") {
    appendix = ""
    setDuplicatesStrategy(DuplicatesStrategy.FAIL)
    val sourceMain = java.sourceSets["main"]
    from(sourceMain.output)

    configurations.runtimeClasspath.filter {
        it.name.startsWith("fastjson")
    }.forEach { jar ->
        from(zipTree(jar))
    }
}

//build命令依赖的其他命令
tasks.build {
    dependsOn("processShell", tasks.processResources, "fatJar")
}
