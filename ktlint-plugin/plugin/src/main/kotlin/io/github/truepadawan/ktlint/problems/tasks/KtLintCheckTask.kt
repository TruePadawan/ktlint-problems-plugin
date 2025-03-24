@file:Suppress("UnstableApiUsage")

package io.github.truepadawan.ktlint.problems.tasks

import io.github.truepadawan.ktlint.problems.KtlintProblemsPlugin
import io.github.truepadawan.ktlint.problems.invoker.KtLintInvoker
import io.github.truepadawan.ktlint.problems.invoker.LintErrorResult
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.problems.ProblemId
import org.gradle.api.problems.Problems
import org.gradle.api.problems.Severity
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class KtLintCheckTask : DefaultTask {
    private var projectLayout: ProjectLayout

    @Inject
    constructor(projectLayout: ProjectLayout) : super() {
        this.projectLayout = projectLayout
    }

    @get:Inject
    abstract val problems: Problems

    /*
     * Run the linter on all kotlin files in the project and report lint errors via Problems API
     * */
    @TaskAction
    fun action() {
        val ktLintInvoker = KtLintInvoker.initialize()
        val kotlinFiles = projectLayout.projectDirectory.asFileTree.filter { it.extension == "kt" }
        kotlinFiles.forEach {
            println("Checking ${it.name}")
            val lintErrorResult = ktLintInvoker.invokeLinter(it)
            if (lintErrorResult.errors.isNotEmpty()) {
                reportProblems(lintErrorResult)
                println("- Lint error(s) found")
            } else {
                println("- No lint error(s) found")
            }
            println("----------------------")
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
