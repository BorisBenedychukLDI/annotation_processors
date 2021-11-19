package com.exmaple.com.processor.ClassBuilders

import com.squareup.kotlinpoet.*

class UtilFileBuilder(
    private val uniqueStr: String,
) {

    fun buildFunctionDecoder() = FunSpec.builder("decode$uniqueStr")
        .addParameter(ParameterSpec("str$uniqueStr", ClassName("kotlin", "String")))
        .returns(STRING)
        .addStatement("return String(Base64.decode(str$uniqueStr, Base64.DEFAULT))")
        .build()

    fun checkInternetFunction() = FunSpec.builder("checkInternet$uniqueStr")
        .addParameter(ParameterSpec("context$uniqueStr", ClassName("android.content", "Context")))
        .returns(BOOLEAN)
        .addStatement(
            """
            val connectivityManager$uniqueStr =
            context$uniqueStr.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities$uniqueStr =
                connectivityManager$uniqueStr.getNetworkCapabilities(connectivityManager$uniqueStr.activeNetwork)
                    ?: return false
            return networkCapabilities$uniqueStr.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            for (network$uniqueStr in connectivityManager$uniqueStr.allNetworks) {
                connectivityManager$uniqueStr.getNetworkInfo(network$uniqueStr)?.let {
                    if (it.isConnected) return true
                }
            }
            return false
        }
           
        """.trimIndent()
        )
        .build()

}
