/*
 * This source file was generated by the Gradle 'init' task
 */
package io.github.truepadawan.ktlint.problems.sample

class Person(
    var name: String,
) {
    fun greeting(): String = "Hello, My name is $name. Nice to meet you"
}

fun main() {
    val hermes = Person(name = "Hermes")
    println(hermes.greeting())
}
