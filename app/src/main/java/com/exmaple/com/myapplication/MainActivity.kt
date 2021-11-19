package com.exmaple.com.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.exmaple.com.processor.NiceAnnot

@NiceAnnot("askjdhfals",
"999ll", "FragmentFirst999llBinding", "FragmentSecond999llBinding", "sadhfsdkfa"
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}