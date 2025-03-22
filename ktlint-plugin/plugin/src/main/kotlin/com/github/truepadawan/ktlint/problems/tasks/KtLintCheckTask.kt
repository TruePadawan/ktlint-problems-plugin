package com.github.truepadawan.ktlint.problems.tasks

import com.github.truepadawan.ktlint.problems.invoker.KtLintInvoker
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class KtLintCheckTask @Inject constructor(private var projectLayout: ProjectLayout) : DefaultTask() {
    /*
    * Run the linter on all kotlin files in the project and report errors via Problems API
    * */
    @TaskAction
    fun action() {
        val ktLintInvoker = KtLintInvoker.initialize()
        val kotlinFiles = projectLayout.settingsDirectory.asFileTree.filter { it.extension == "kt" }
        kotlinFiles.forEach {
            val lintErrorResult = ktLintInvoker.invokeLinter(it)

        }
    }
}