package com.exmaple.com.processor

import com.exmaple.com.processor.ClassBuilders.ApplicationClassBuilder
import com.exmaple.com.processor.ClassBuilders.SplashFragmentBuilder
import com.exmaple.com.processor.ClassBuilders.UtilFileBuilder
import com.exmaple.com.processor.ClassBuilders.WebFragmentBuilder
import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement


@SupportedSourceVersion(SourceVersion.RELEASE_8)
class MyClass : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> =
        mutableSetOf(NiceAnnot::class.java.canonicalName)

    override fun process(
        set: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {
        val kaptDir = processingEnv.options[KAPT_KOTLIN_GENERATED] ?: return false
        roundEnvironment?.run {
            getElementsAnnotatedWith(NiceAnnot::class.java).forEach {
                val modelData = getModelData(it)
                val fileName = "Application${modelData.uniqueStr}"
                val fileName2 = "Fragment2${modelData.uniqueStr}"
                val fileName3 = "Fragment1${modelData.uniqueStr}"
                modelData.run {
                    FileSpec.builder(packageName, fileName)
                        .addType(ApplicationClassBuilder(uniqueStr, OS_KEY).build())
                        .addImport("android.app", "Application")
                        .addImport("com.onesignal", "OneSignal")
                        .addImport(packageName, "decode$uniqueStr")
                        .build()
                        .writeTo(File(kaptDir))
                    FileSpec.builder(modelData.packageName, fileName2)
                        .addType(WebFragmentBuilder(url, uniqueStr, binding2).build())
                        .addImport("android.webkit", "WebChromeClient", "CookieManager", "WebSettings" , "WebViewClient", "WebView")
                        .addImport("android.webkit.WebChromeClient", "FileChooserParams")
                        .addImport("androidx.activity", "OnBackPressedCallback")
                        .addImport("androidx.core.view", "forEach")
                        .addImport("$packageName.databinding", binding2)
                        .addImport("android.util", "Log")
                        .addImport("androidx.swiperefreshlayout.widget", "SwipeRefreshLayout")
                        .addImport("android.content", "Intent")
                        .addImport("androidx.activity.result.contract", "ActivityResultContracts")
                        .addImport("android.content.Context", "MODE_PRIVATE")
                        .addImport(packageName, "decode$uniqueStr")
                        .build()
                        .writeTo(File(kaptDir))
                    FileSpec.builder(packageName, fileName3)
                        .addType(SplashFragmentBuilder(uniqueStr, binding1).build())
                        .addImport("$packageName.databinding", binding1)
                        .addImport(packageName, "R")
                        .addImport("android.content.Context", "MODE_PRIVATE")
                        .addImport("androidx.navigation.fragment", "findNavController")
                        .addImport("kotlinx.coroutines", "delay", "launch")
                        .addImport("android.widget", "Button")
                        .addImport("androidx.core.view", "forEach")
                        .addImport("androidx.lifecycle", "lifecycleScope")
                        .addImport(packageName, "decode$uniqueStr")
                        .build()
                        .writeTo(File(kaptDir))
                    FileSpec.builder(packageName, "UtilsFunctions$uniqueStr")
                        .addFunction(UtilFileBuilder(uniqueStr).buildFunctionDecoder())
                        .addFunction(UtilFileBuilder(uniqueStr).checkInternetFunction())
                        .addImport("android.content", "Context")
                        .addImport("android.util", "Base64")
                        .addImport("android.net", "ConnectivityManager", "NetworkCapabilities")
                        .addImport("android.os", "Build")
                        .build()
                        .writeTo(File(kaptDir))
                }

            }
        }
        return true
    }

    private fun getModelData(element: Element): ModelData {
        val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
        val modelName = element.simpleName.toString()
        element.getAnnotation(NiceAnnot::class.java).run {
            return ModelData(packageName, modelName, uniqueStr, OS_KEY, binding1String, binding2String, url)
        }


    }

    companion object {
        const val KAPT_KOTLIN_GENERATED = "kapt.kotlin.generated"
    }
}

data class ModelData(
    val packageName: String,
    val modelName: String,
    val uniqueStr: String,
    val OS_KEY: String,
    val binding1: String,
    val binding2: String,
    val url: String
)



