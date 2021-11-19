package com.exmaple.com.aswell

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class NiceAnnot(
    val OS_KEY: String,
    val uniqueStr: String,
    val binding1String: String,
    val binding2String: String,
    val url: String
)