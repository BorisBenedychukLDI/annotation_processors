package com.exmaple.com.processor.ClassBuilders

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class WebFragmentBuilder(
    private val url: String,
    private val uniqueStr: String,
    private val bindingStr2: String
) {
    fun build(): TypeSpec = TypeSpec.classBuilder("Fragment2$uniqueStr")
        .addProperty(
            PropertySpec.builder(
                "fPCB$uniqueStr",
                ClassName("android.webkit", "ValueCallback")
                    .parameterizedBy(
                        ClassName("kotlin", "Array")
                            .parameterizedBy(ClassName("android.net", "Uri"))
                    )
                    .copy(nullable = true)
            ).addModifiers(KModifier.PRIVATE)
                .mutable(true)
                .initializer("null")
                .build()
        )
        .superclass(ClassName("androidx.fragment.app", "Fragment"))
        .addFunction(
            FunSpec.builder("onCreateView")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("inflater", ClassName("android.view", "LayoutInflater"))
                .addParameter(
                    "container",
                    ClassName("android.view", "ViewGroup").copy(nullable = true)
                )
                .addParameter(
                    "savedInstanceState",
                    ClassName("android.os", "Bundle").copy(nullable = true)
                )
                .addStatement(
                    """
        $bindingStr2.inflate(inflater, container, false).apply {
        wv$uniqueStr.run {
            CookieManager.getInstance().let {
                CookieManager.allowFileSchemeCookies()
                it.setAcceptThirdPartyCookies(this@run, true)
            }
            registerForActivityResult(
                        ActivityResultContracts.StartActivityForResult()
                    ) {
                        fPCB$uniqueStr?.onReceiveValue(
                            WebChromeClient.FileChooserParams.parseResult(
                                it.resultCode,
                                it.data
                            )
                        )
                    }.let {
            webChromeClient = ${
                        TypeSpec.anonymousClassBuilder()
                            .superclass(ClassName("android.webkit", "WebChromeClient"))
                            .addFunction(
                                FunSpec.builder("onShowFileChooser")
                                    .addModifiers(KModifier.OVERRIDE)
                                    .addParameter(
                                        ParameterSpec(
                                            "webView",
                                            ClassName(
                                                "android.webkit",
                                                "WebView"
                                            ).copy(nullable = true)
                                        )
                                    )
                                    .addParameter(
                                        ParameterSpec(
                                            "filePathCallback$uniqueStr",
                                            ClassName(
                                                "android.webkit",
                                                "ValueCallback"
                                            ).parameterizedBy(
                                                ClassName("kotlin", "Array")
                                                    .parameterizedBy(
                                                        ClassName(
                                                            "android.net",
                                                            "Uri"
                                                        )
                                                    )
                                            )
                                                .copy(nullable = true)
                                        )
                                    )
                                    .addParameter(
                                        ParameterSpec(
                                            "fileChooserParams$uniqueStr",
                                            ClassName(
                                                "android.webkit.WebChromeClient",
                                                "FileChooserParams"
                                            ).copy(
                                                nullable = true
                                            )
                                        )
                                    )
                                    .returns(BOOLEAN)
                                    .addStatement(
                                        """
                                fPCB$uniqueStr = filePathCallback$uniqueStr
        it.launch(fileChooserParams$uniqueStr
            ?.createIntent()?.apply { 
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*" })
        return true
                            """.trim()
                                    )
                                    .build()
                            ).build()
                    }
            webViewClient = ${
                        TypeSpec.anonymousClassBuilder()
                            .superclass(ClassName("android.webkit", "WebViewClient"))
                            .addFunction(
                                FunSpec.builder("shouldOverrideUrlLoading")
                                    .addModifiers(KModifier.OVERRIDE)
                                    .addParameter(
                                        ParameterSpec(
                                            "view$uniqueStr",
                                            ClassName(
                                                "android.webkit",
                                                "WebView"
                                            ).copy(nullable = true)
                                        )
                                    )
                                    .addParameter(
                                        ParameterSpec(
                                            "request$uniqueStr",
                                            ClassName("android.webkit", "WebResourceRequest").copy(
                                                nullable = true
                                            )
                                        )
                                    )
                                    .returns(BOOLEAN)
                                    .addStatement(
                                        """val modifiedLinks$uniqueStr = listOf("mailto:", "tel:", "facebook", "tg:", "instagram")
        return when {
            modifiedLinks$uniqueStr.any {
                request$uniqueStr?.url.toString().contains(it)
            } -> {
                view$uniqueStr?.context?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        request$uniqueStr?.url
                    )
                )
                true
            }
            else -> false
        }"""
                                    )
                                    .build()
                            )
                            .addFunction(
                                FunSpec.builder("onPageFinished")
                                    .addModifiers(KModifier.OVERRIDE)
                                    .addParameter(
                                        ParameterSpec(
                                            "view$uniqueStr",
                                            ClassName(
                                                "android.webkit",
                                                "WebView"
                                            ).copy(nullable = true)
                                        )
                                    )
                                    .addParameter(
                                        ParameterSpec(
                                            "url$uniqueStr",
                                            ClassName("kotlin", "String").copy(nullable = true)
                                        )
                                    )
                                    .addStatement(
                                        """
                                super.onPageFinished(view$uniqueStr, url$uniqueStr)
        requireContext().getSharedPreferences("SP$uniqueStr", MODE_PRIVATE).edit().putString("LP$uniqueStr", url$uniqueStr).apply()
        CookieManager.getInstance().flush()
                            """.trimIndent()
                                    )
                                    .build()
                            )
                            .build()
                    }
                            
                            
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            settings.run {
                useWideViewPort = true
                displayZoomControls = false
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                builtInZoomControls = true
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                domStorageEnabled = true
                mediaPlaybackRequiresUserGesture = false
                loadWithOverviewMode = true
                displayZoomControls = false
            }
            srl$uniqueStr.run {
                setOnRefreshListener {
                    loadUrl(url ?: return@setOnRefreshListener)
                    isRefreshing = false
                }
            }
            }
            loadUrl(requireContext().getSharedPreferences("SP$uniqueStr", MODE_PRIVATE).getString("LP$uniqueStr", null)
             ?: decode$uniqueStr("$url"))
        
        ${
                        TypeSpec.anonymousClassBuilder()
                            .superclass(ClassName("androidx.activity", "OnBackPressedCallback"))
                            .addSuperclassConstructorParameter("true")
                            .addFunction(
                                FunSpec.builder("handleOnBackPressed")
                                    .addModifiers(KModifier.OVERRIDE)
                                    .addStatement("wv$uniqueStr.run { if (canGoBack()) goBack() else requireActivity().finish() }")
                                    .build()
                            )
                            .build()
                    }.let {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, it)
        }
    }
    return root

}
         """
                )
                .returns(ClassName("android.view", "View").copy(nullable = true))
                .build()
        )
        .build()

}
