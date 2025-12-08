/* Projeto é Kotlin DSL */
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlinx.kover")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
    println("keystore.properties charged successfully")
} else {
    println("keystore.properties not found")
}

val secretsProperties = Properties().apply {
    val secretsFile = rootProject.file("secrets.properties")
    if (secretsFile.exists()) {
        load(secretsFile.inputStream())
        println("secrets.properties carregado com sucesso")
    } else {
        println("secrets.properties não encontrado")
    }
}

fun getSecretProperty(key: String): String? {
    return secretsProperties.getProperty(key)
}


android {
    namespace = "com.fabianospdev.volunteerscompose"
    compileSdk = 36
    testBuildType = "debug"

    defaultConfig {
        applicationId = "com.fabianospdev.volunteerscompose"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField("String", "FIREBASE_API_KEY", "\"${getSecretProperty("FIREBASE_API_KEY") ?: ""}\"")
    }

    signingConfigs {
        create("release") {
            when {
                System.getenv("ANDROID_KEYSTORE_PATH") != null -> {
                    storeFile = file(System.getenv("ANDROID_KEYSTORE_PATH"))
                    storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
                    keyAlias = System.getenv("ANDROID_KEYSTORE_ALIAS")
                    keyPassword = System.getenv("ANDROID_KEYSTORE_KEY_PASSWORD")
                    println("Setting the signature of the loaded version from environment variables.")
                }
                keystoreProperties.containsKey("storeFile") -> {
                    storeFile = file(keystoreProperties["storeFile"] as String)
                    storePassword = keystoreProperties["storePassword"] as String
                    keyAlias = keystoreProperties["keyAlias"] as String
                    keyPassword = keystoreProperties["keyPassword"] as String
                    println("Setting the signature of release loaded from keystore.properties")
                }
                else -> {
                    storeFile = file("debug.keystore")
                    storePassword = "android"
                    keyAlias = "androiddebugkey"
                    keyPassword = "android"
                    println("Setting the signature of release loaded with debug values")
                }
            }
        }

        getByName("debug") {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            enableUnitTestCoverage = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")

            buildConfigField("String", "FIREBASE_API_KEY", "\"${getSecretProperty("FIREBASE_API_KEY") ?: ""}\"")
        }
        debug {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"

            buildConfigField("String", "FIREBASE_API_KEY", "\"${getSecretProperty("FIREBASE_API_KEY") ?: ""}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.all {
            testBuildType = "debug"
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    ksp {
        arg("room.incremental", "true")
    }

    // Gera AAB (Android App Bundle)
    bundle {
        language {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.androidx.security.crypto.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.volley)

    ksp(libs.androidx.hilt.compiler)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))
}

tasks.withType<Test>().configureEach {
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}

afterEvaluate {
    tasks.withType<Test>().configureEach {
        if (name.contains("Release", ignoreCase = true)) {
            enabled = false
            logger.lifecycle("Task $name desabilitada")
        }
    }
}

kover {
    reports {
        total {
            html {
                onCheck.set(false)
            }
            xml {
                onCheck.set(false)
            }
        }

        verify {
            rule("line-coverage") {
                bound {
                    minValue = 90
                }
            }
            rule("branch-coverage") {
                bound {
                    minValue = 80
                }
            }
        }

        filters {
            excludes {
                androidGeneratedClasses()
                classes(
                    "*Hilt_*",
                    "*_Factory",
                    "*Companion",
                    "*\$InjectAdapter*",
                    "*\$ModuleAdapter*",
                    "*\$ViewInjector*",
                    "dagger.hilt.android.internal.*",
                    "dagger.hilt.internal.*",
                    "*_HiltComponents*",
                    "*HiltModules*",
                    "*_MembersInjector",
                    "*ViewModel_Factory*",
                    "com.fabianospdev.volunteerscompose.VolunteersComposeApp",
                    "com.fabianospdev.volunteerscompose.features.login.presentation.utils.*",
                    "com.fabianospdev.volunteerscompose.core.utils.*",
                    "com.fabianospdev.volunteerscompose.ui.theme.*",
                    "com.fabianospdev.volunteerscompose.di.*"
                )
            }
        }
    }
}

tasks.register("testDebugOnly") {
    group = "verification"
    description = "Run ONLY debug tests and generate coverage"

    dependsOn("testDebugUnitTest", "koverHtmlReport")

    doLast {
        println("Testes DEBUG executados com sucesso!")
        println("Relatório de cobertura: app/build/reports/kover/html/index.html")
    }
}
