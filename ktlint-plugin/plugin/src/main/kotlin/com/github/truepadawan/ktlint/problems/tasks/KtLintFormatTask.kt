package com.github.truepadawan.ktlint.problems.tasks

import com.github.truepadawan.ktlint.problems.KtlintProblemsPlugin
import com.github.truepadawan.ktlint.problems.invoker.KtLintInvoker
import com.github.truepadawan.ktlint.problems.invoker.LintErrorResult
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.problems.ProblemId
import org.gradle.api.problems.Problems
import org.gradle.api.problems.Severity
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class KtLintFormatTask : DefaultTask {
    private val projectLayout: ProjectLayout

    @Inject
    constructor(projectLayout: ProjectLayout) : super() {
        this.projectLayout = projectLayout
    }

    @get:Inject
    abstract val problems: Problems

    /*
     * Run the formatter on all kotlin files in the project and report lint errors via Problems API
     * */
    @TaskAction
    fun action() {
        val ktLintInvoker = KtLintInvoker.initialize()
        val kotlinFiles = projectLayout.settingsDirectory.asFileTree.filter { it.extension == "kt" }
        kotlinFiles.forEach {
            val (formattedCode, lintErrorResult) = ktLintInvoker.invokeFormatter(it)
            reportProblems(lintErrorResult)
            val currentFileContent = it.readText()
            if (currentFileContent != formattedCode) {
                it.writeText(formattedCode)
            }
        }
    }

    private fun reportProblems(lintErrorResult: LintErrorResult) {
        val (file, errors) = lintErrorResult
        errors.forEach {
            val problemId = ProblemId.create(it.ruleId.value, it.detail, KtlintProblemsPlugin.PROBLEM_GROUP)
            problems.reporter.report(problemId, { problemSpec ->
                problemSpec.lineInFileLocation(file.path, it.line, it.col).severity(Severity.WARNING)
            })
        }
    }
}
