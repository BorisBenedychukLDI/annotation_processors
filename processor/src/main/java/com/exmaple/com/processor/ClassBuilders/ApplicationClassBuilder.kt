package com.exmaple.com.processor.ClassBuilders

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec

class ApplicationClassBuilder(private val uniqueStr: String, private val OS_KEY: String) {

    fun build(): TypeSpec = TypeSpec.classBuilder("Application$uniqueStr")
        .superclass(ClassName("android.app", "Application"))
        .addFunction(
            FunSpec.builder("onCreate")
                .addModifiers(KModifier.OVERRIDE)
                .addStatement(
                    """
    super.onCreate()
OneSignal.initWithContext(this)
OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
OneSignal.setAppId(decode$uniqueStr("$OS_KEY"))
                """.trimMargin()
                )
                .build()
        )
        .build()
}
