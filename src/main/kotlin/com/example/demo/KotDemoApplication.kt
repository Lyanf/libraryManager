package com.example.demo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
@SpringBootApplication
open class KotDemoApplication
fun main(args: Array<String>) {
    runApplication<KotDemoApplication>(*args)
}