package com.github.truepadawan.ktlint.problems

import com.github.truepadawan.ktlint.problems.tasks.KtLintCheckTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.problems.ProblemGroup
import org.gradle.api.problems.ProblemReporter
import org.gradle.api.problems.Problems
import javax.inject.Inject

class KtlintProblemsPlugin : Plugin<Project> {
    companion object {
        val PROBLEM_GROUP = ProblemGroup.create("ktlintProblems", "KtLint Problems Plugin")
    }

    private val problemReporter: ProblemReporter

    @Inject
    constructor(problems: Problems) {
        problemReporter = problems.reporter
    }

    override fun apply(project: Project) {
        project.tasks.register("ktlintCheck", KtLintCheckTask::class.java) { task ->
            task.description = "Run KtLint on all kotlin files in the project and report issues via the Problems API"
            task.group = "ktlint problems plugin"
        }
    }
}