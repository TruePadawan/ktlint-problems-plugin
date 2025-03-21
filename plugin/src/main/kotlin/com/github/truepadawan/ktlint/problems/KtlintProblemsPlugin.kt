package com.github.truepadawan.ktlint.problems

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.OutputFile

/**
 * A simple 'hello world' plugin.
 */
class KtlintProblemsPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        // Register a task
        project.tasks.register("greeting") { task ->
            task.doLast {
                println("Hello from plugin 'org.example.greeting'")
            }
        }
    }
}