package com.example.demo

fun main(args: Array<String>) {
    var map = mapOf<String,String>("a" to "1","b" to "2")
    println(map.get("a"))
    var t:String? = null
    println(t?.length)
}