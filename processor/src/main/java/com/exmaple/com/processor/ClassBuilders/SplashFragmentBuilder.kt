package com.exmaple.com.processor.ClassBuilders

import com.squareup.kotlinpoet.*

class SplashFragmentBuilder(private val uniqueStr: String, private val bindingStr1: String) {

    fun build(): TypeSpec = TypeSpec.classBuilder("Fragment1$uniqueStr")
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
                ${bindingStr1}.inflate(inflater, container, false).run {
                requireContext().getSharedPreferences("SP$uniqueStr", MODE_PRIVATE).getString("LP$uniqueStr", null)?.let {
                     findNavController().navigate(R.id.action_fragment1${uniqueStr}_to_fragment2$uniqueStr)
                }
                root.forEach { if (it is Button) {
                it.setOnClickListener {
                    it.isClickable = false
                    lifecycleScope.launch {
                        delay (5000)
                        findNavController().navigate(R.id.action_fragment1${uniqueStr}_to_fragment2$uniqueStr)
                    }
                }
            }
            }
                
                
                return root
                }
                    
                """.trimIndent()
                )
                .returns(ClassName("android.view", "View").copy(nullable = true))
                .build()
        )
        .build()


}





